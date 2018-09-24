package utils;


import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import ninja.Context;


public class AuthenticationUtil
{
    public static final String tokenHeader = "Authentication-Token";


    public static boolean hasAuthenticationToken( Context context )
    {
        System.out.println( context.getHeader( tokenHeader ) );
        if ( context.getHeader( tokenHeader ) == null )
        {
            return false;
        }
        else if ( context.getHeader( tokenHeader ).equals( "" ) )
        {
            return false;
        }
        return true;
    }


    public static Claims parseToken( Context context, String applicationSecret )
    {
        Claims claims =
                Jwts.parser().setSigningKey( applicationSecret ).parseClaimsJws( context.getHeader( tokenHeader ) )
                    .getBody();
        return claims;
    }


    public static boolean isExpired( Claims claims )
    {
        Date expiresOn = claims.getExpiration();
        return new Date().getTime() > expiresOn.getTime();
    }


    public static String generateToken( String emailAddress, String applicationSecret )
    {
        long ONE_MINUTE_IN_MILLIS = 60000;
        long now = new Date().getTime();
        Date expires = new Date( now + ( 1 * ONE_MINUTE_IN_MILLIS ) );
        return Jwts.builder().setExpiration( expires ).setSubject( emailAddress )
                   .signWith( SignatureAlgorithm.HS256, applicationSecret ).compact();
    }
}
