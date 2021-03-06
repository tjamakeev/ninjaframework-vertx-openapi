package service;


import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.openapitools.server.api.model.Order;
import org.openapitools.server.api.verticle.StoreApi;

import com.google.inject.Inject;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;


public class StoreApiImpl implements StoreApi
{
    @Inject
    private HelloService helloService;


    @Override
    public void deleteOrder( final String orderId, final Handler<AsyncResult<Void>> handler )
    {

    }


    @Override
    public void getInventory( final Handler<AsyncResult<Map<String, Integer>>> handler )
    {
        helloService.hello( "getInventory" );

        handler.handle( new AsyncResult<Map<String, Integer>>()
        {
            @Override
            public Map<String, Integer> result()
            {
                HashMap<String, Integer> result = new LinkedHashMap<>();
                result.put( "1", 1 );
                result.put( "2", 2 );
                result.put( "3", 3 );
                return result;
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
    public void getOrderById( final Long orderId, final Handler<AsyncResult<Order>> handler )
    {

    }


    @Override
    public void placeOrder( final Order order, final Handler<AsyncResult<Order>> handler )
    {

    }
}
