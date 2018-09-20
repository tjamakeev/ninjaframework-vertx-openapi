package service;


import org.openapitools.server.api.MainApiVerticle;
import org.openapitools.server.api.verticle.PetApiVerticle;
import org.openapitools.server.api.verticle.StoreApiVerticle;
import org.openapitools.server.api.verticle.UserApiVerticle;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class ApplicationApiVerticle extends MainApiVerticle
{
    private final UserApiVerticle userApiVerticle;
    private final PetApiVerticle petApiVerticle;
    private final StoreApiVerticle storeApiVerticle;


    public ApplicationApiVerticle( final Vertx vertx, final PetApiVerticle petApiVerticle,
                                   final UserApiVerticle userApiVerticle, final StoreApiVerticle storeApiVerticle )
    {
        this.vertx = vertx;
        this.petApiVerticle = petApiVerticle;
        this.userApiVerticle = userApiVerticle;
        this.storeApiVerticle = storeApiVerticle;
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
