package service;


import com.google.inject.Inject;

import exception.AuthenticationException;
import ninja.utils.NinjaProperties;
import utils.AuthenticationUtil;


public class AuthenticationService
{
    @Inject
    NinjaProperties ninjaProperties;

    @Inject
    HelloService helloService;


    public String authenticate( final String email, final String password ) throws AuthenticationException
    {

        helloService.hello( email );
        if ( "admin@gmail.com".equals( email ) && "secret".equals( password ) )
        {
            return AuthenticationUtil.generateToken( email, ninjaProperties.get( "application.secret" ) );
        }

        throw new AuthenticationException();
    }
}
