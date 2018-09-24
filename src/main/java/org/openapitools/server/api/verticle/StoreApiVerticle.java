package org.openapitools.server.api.verticle;


import org.openapitools.server.api.MainApiException;
import org.openapitools.server.api.model.Order;

import com.google.inject.Inject;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import service.StoreApiImpl;


public class StoreApiVerticle extends AbstractVerticle
{
    final static Logger LOGGER = LoggerFactory.getLogger( StoreApiVerticle.class );

    final static String DELETEORDER_SERVICE_ID = "deleteOrder";
    final static String GETINVENTORY_SERVICE_ID = "getInventory";
    final static String GETORDERBYID_SERVICE_ID = "getOrderById";
    final static String PLACEORDER_SERVICE_ID = "placeOrder";

    final StoreApi service;


    public StoreApiVerticle()
    {
        try
        {
            Class serviceImplClass =
                    getClass().getClassLoader().loadClass( "service.StoreApiImpl" );
            service = ( StoreApi ) serviceImplClass.newInstance();
        }
        catch ( Exception e )
        {
            logUnexpectedError( "StoreApiVerticle constructor", e );
            throw new RuntimeException( e );
        }
    }


    @Inject
    public StoreApiVerticle( final StoreApiImpl storeApi )
    {
        service = storeApi;
    }


    @Override
    public void start() throws Exception
    {

        //Consumer for deleteOrder
        vertx.eventBus().<JsonObject>consumer( DELETEORDER_SERVICE_ID ).handler( message -> {
            try
            {
                // Workaround for #allParams section clearing the vendorExtensions map
                String serviceId = "deleteOrder";
                String orderIdParam = message.body().getString( "orderId" );
                if ( orderIdParam == null )
                {
                    manageError( message, new MainApiException( 400, "orderId is required" ), serviceId );
                    return;
                }
                String orderId = orderIdParam;
                service.deleteOrder( orderId, result -> {
                    if ( result.succeeded() )
                    {
                        message.reply( null );
                    }
                    else
                    {
                        Throwable cause = result.cause();
                        manageError( message, cause, "deleteOrder" );
                    }
                } );
            }
            catch ( Exception e )
            {
                logUnexpectedError( "deleteOrder", e );
                message.fail( MainApiException.INTERNAL_SERVER_ERROR.getStatusCode(),
                        MainApiException.INTERNAL_SERVER_ERROR.getStatusMessage() );
            }
        } );

        //Consumer for getInventory
        vertx.eventBus().<JsonObject>consumer( GETINVENTORY_SERVICE_ID ).handler( message -> {
            try
            {
                // Workaround for #allParams section clearing the vendorExtensions map
                String serviceId = "getInventory";
                service.getInventory( result -> {
                    if ( result.succeeded() )
                    {
                        message.reply( new JsonObject( Json.encode( result.result() ) ).encodePrettily() );
                    }
                    else
                    {
                        Throwable cause = result.cause();
                        manageError( message, cause, "getInventory" );
                    }
                } );
            }
            catch ( Exception e )
            {
                logUnexpectedError( "getInventory", e );
                message.fail( MainApiException.INTERNAL_SERVER_ERROR.getStatusCode(),
                        MainApiException.INTERNAL_SERVER_ERROR.getStatusMessage() );
            }
        } );

        //Consumer for getOrderById
        vertx.eventBus().<JsonObject>consumer( GETORDERBYID_SERVICE_ID ).handler( message -> {
            try
            {
                // Workaround for #allParams section clearing the vendorExtensions map
                String serviceId = "getOrderById";
                String orderIdParam = message.body().getString( "orderId" );
                if ( orderIdParam == null )
                {
                    manageError( message, new MainApiException( 400, "orderId is required" ), serviceId );
                    return;
                }
                Long orderId = Json.mapper.readValue( orderIdParam, Long.class );
                service.getOrderById( orderId, result -> {
                    if ( result.succeeded() )
                    {
                        message.reply( new JsonObject( Json.encode( result.result() ) ).encodePrettily() );
                    }
                    else
                    {
                        Throwable cause = result.cause();
                        manageError( message, cause, "getOrderById" );
                    }
                } );
            }
            catch ( Exception e )
            {
                logUnexpectedError( "getOrderById", e );
                message.fail( MainApiException.INTERNAL_SERVER_ERROR.getStatusCode(),
                        MainApiException.INTERNAL_SERVER_ERROR.getStatusMessage() );
            }
        } );

        //Consumer for placeOrder
        vertx.eventBus().<JsonObject>consumer( PLACEORDER_SERVICE_ID ).handler( message -> {
            try
            {
                // Workaround for #allParams section clearing the vendorExtensions map
                String serviceId = "placeOrder";
                JsonObject orderParam = message.body().getJsonObject( "Order" );
                if ( orderParam == null )
                {
                    manageError( message, new MainApiException( 400, "Order is required" ), serviceId );
                    return;
                }
                Order order = Json.mapper.readValue( orderParam.encode(), Order.class );
                service.placeOrder( order, result -> {
                    if ( result.succeeded() )
                    {
                        message.reply( new JsonObject( Json.encode( result.result() ) ).encodePrettily() );
                    }
                    else
                    {
                        Throwable cause = result.cause();
                        manageError( message, cause, "placeOrder" );
                    }
                } );
            }
            catch ( Exception e )
            {
                logUnexpectedError( "placeOrder", e );
                message.fail( MainApiException.INTERNAL_SERVER_ERROR.getStatusCode(),
                        MainApiException.INTERNAL_SERVER_ERROR.getStatusMessage() );
            }
        } );
    }


    private void manageError( Message<JsonObject> message, Throwable cause, String serviceName )
    {
        int code = MainApiException.INTERNAL_SERVER_ERROR.getStatusCode();
        String statusMessage = MainApiException.INTERNAL_SERVER_ERROR.getStatusMessage();
        if ( cause instanceof MainApiException )
        {
            code = ( ( MainApiException ) cause ).getStatusCode();
            statusMessage = ( ( MainApiException ) cause ).getStatusMessage();
        }
        else
        {
            logUnexpectedError( serviceName, cause );
        }

        message.fail( code, statusMessage );
    }


    private void logUnexpectedError( String serviceName, Throwable cause )
    {
        LOGGER.error( "Unexpected error in " + serviceName, cause );
    }
}
