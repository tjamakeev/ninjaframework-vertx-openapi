package conf;


import com.google.inject.AbstractModule;
import com.google.inject.Inject;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import lombok.extern.slf4j.Slf4j;
import service.ApplicationApiVerticle;


@Slf4j
public class VertxModule extends AbstractModule
{
    //    private static HttpServer server;


    @Override
    protected void configure()
    {
        log.info( "Configuring Vert.x" );
        Vertx vertx = Vertx.vertx();
        deploy( vertx, ServiceLauncher.class, new DeploymentOptions() );
    }


    private static Future<Void> deploy( Vertx vertx, Class verticle, DeploymentOptions opts )
    {
        Future<Void> done = Future.future();
        String deploymentName = "java-guice:" + verticle.getName();
        JsonObject config = new JsonObject().put( "guice_binder", ServiceBinder.class.getName() );

        opts.setConfig( config );

        vertx.deployVerticle( deploymentName, opts, r -> {
            // handle success/failure
        } );

        return done;
    }


    private static class ServiceLauncher extends AbstractVerticle
    {
        private AppConfig appConfig;


        @Inject
        public ServiceLauncher( AppConfig appConfig )
        {
            this.appConfig = appConfig;
        }


        /**
         * Start method uses CompositeFuture to deploy all required verticles
         */
        @Override
        public void start( Future<Void> done )
        {
            // start your verticle here
            log.info( "Starting verticles..." );
            deploy( vertx, ApplicationApiVerticle.class, new DeploymentOptions() );
        }
    }
}
