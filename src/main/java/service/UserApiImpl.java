package service;


import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openapitools.server.api.model.User;
import org.openapitools.server.api.verticle.UserApi;

import com.google.inject.Inject;

import exception.AuthenticationException;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;


public class UserApiImpl implements UserApi
{
    private final AuthenticationService authenticationService;


    @Inject
    public UserApiImpl( final AuthenticationService authenticationService )
    {
        this.authenticationService = authenticationService;
    }


    @Override
    public void createUser( final User user, final Handler<AsyncResult<Void>> handler )
    {

    }


    @Override
    public void createUsersWithArrayInput( final List<User> user, final Handler<AsyncResult<Void>> handler )
    {

    }


    @Override
    public void createUsersWithListInput( final List<User> user, final Handler<AsyncResult<Void>> handler )
    {

    }


    @Override
    public void deleteUser( final String username, final Handler<AsyncResult<Void>> handler )
    {

    }


    @Override
    public void getUserByName( final String username, final Handler<AsyncResult<User>> handler )
    {

    }


    @Override
    public void loginUser( final String username, final String password, final Handler<AsyncResult<String>> handler )
    {
        handler.handle( new AsyncResult<String>()
        {
            @Override
            public String result()
            {
                Map<String, String> result = new LinkedHashMap<>();
                try
                {
                    result.put( "token", authenticationService.authenticate( username, password ) );
                }
                catch ( AuthenticationException e )
                {
                    return null;
                }

                return JsonObject.mapFrom( result ).encode();
            }


            @Override
            public Throwable cause()
            {
                return null;
            }


            @Override
            public boolean succeeded()
            {
                return true;
            }


            @Override
            public boolean failed()
            {
                return false;
            }
        } );
    }


    @Override
    public void logoutUser( final Handler<AsyncResult<Void>> handler )
    {

    }


    @Override
    public void updateUser( final String username, final User user, final Handler<AsyncResult<Void>> handler )
    {

    }
}
