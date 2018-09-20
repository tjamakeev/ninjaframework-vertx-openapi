package org.openapitools.server.api.verticle;


import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.openapitools.server.api.model.User;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import service.HelloService;


public class UserApiImpl implements UserApi
{
    private final HelloService helloService;


    public UserApiImpl( final HelloService helloService )
    {
        this.helloService = helloService;
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
        helloService.hello( username );
        handler.handle( new AsyncResult<String>()
        {
            @Override
            public String result()
            {
                Map<String, String> result = new LinkedHashMap<>();
                result.put( "token", UUID.randomUUID().toString() );
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
