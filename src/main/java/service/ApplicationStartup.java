package service;


import com.google.inject.Inject;
import com.google.inject.Singleton;

import io.vertx.core.Vertx;
import lombok.extern.slf4j.Slf4j;
import ninja.lifecycle.Dispose;
import ninja.lifecycle.Start;


@Slf4j
@Singleton
public class ApplicationStartup
{
    @Inject
    ApplicationApiVerticle applicationApiVerticle;

    private Vertx vertx;


    @Start( order = 5 )
    public void vertx()
    {
        log.info( "Configuring Vert.x..." );
        vertx = Vertx.vertx();

        vertx.deployVerticle( applicationApiVerticle );
    }


    @Dispose( order = 5 )
    public void stop()
    {
        vertx.close();
    }
}
