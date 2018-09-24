package conf;


import com.google.inject.Inject;

import io.jsonwebtoken.Claims;
import ninja.Context;
import ninja.Filter;
import ninja.FilterChain;
import ninja.Result;
import ninja.Results;
import ninja.utils.NinjaProperties;
import utils.AuthenticationUtil;


public class JWTAuthFilter implements Filter
{
    @Inject
    private NinjaProperties ninjaProperties;


    @Override
    public Result filter( final FilterChain filterChain, final Context context )
    {
        if ( !AuthenticationUtil.hasAuthenticationToken( context ) )
        {
            return Results.unauthorized();
        }

        String applicationSecret = ninjaProperties.get( "application.secret" );
        Claims claims = AuthenticationUtil.parseToken( context, applicationSecret );

        //check the timestamp
        if ( AuthenticationUtil.isExpired( claims ) )
        {
            return Results.unauthorized();
        }

        String email = claims.getSubject();
        //grab the user from the DB using the email

        return filterChain.next( context );
    }
}
