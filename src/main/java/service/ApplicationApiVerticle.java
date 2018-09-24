package service;


import org.openapitools.server.api.MainApiVerticle;
import org.openapitools.server.api.verticle.PetApiVerticle;
import org.openapitools.server.api.verticle.StoreApiVerticle;
import org.openapitools.server.api.verticle.UserApiVerticle;

import com.google.inject.Inject;

import io.vertx.core.Future;
import lombok.extern.slf4j.Slf4j;
import ninja.utils.NinjaProperties;


@Slf4j
public class ApplicationApiVerticle extends MainApiVerticle
{
    @Inject
    private UserApiVerticle userApiVerticle;


    @Inject
    private NinjaProperties ninjaProperties;

    @Inject
    private PetApiVerticle petApiVerticle;

    @Inject
    private StoreApiVerticle storeApiVerticle;


    @Override
    public int getServerPort()
    {
        return ninjaProperties.getIntegerOrDie( "vertx.port" );
    }


    @Override
    public void deployVerticles( final Future<Void> startFuture )
    {
        vertx.deployVerticle( petApiVerticle, res -> {
            if ( res.succeeded() )
            {
                log.info( "PetApiVerticle : Deployed" );
            }
            else
            {
                startFuture.fail( res.cause() );
                log.error( "PetApiVerticle : Deployment failed" );
            }
        } );

        vertx.deployVerticle( storeApiVerticle, res -> {
            if ( res.succeeded() )
            {
                log.info( "StoreApiVerticle : Deployed" );
            }
            else
            {
                startFuture.fail( res.cause() );
                log.error( "StoreApiVerticle : Deployment failed" );
            }
        } );

        vertx.deployVerticle( userApiVerticle, res -> {
            if ( res.succeeded() )
            {
                log.info( "UserApiVerticle : Deployed" );
            }
            else
            {
                startFuture.fail( res.cause() );
                log.error( "UserApiVerticle : Deployment failed" );
            }
        } );
    }
}
