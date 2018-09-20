package conf;


import org.openapitools.server.api.verticle.PetApi;
import org.openapitools.server.api.verticle.PetApiImpl;
import org.openapitools.server.api.verticle.PetApiVerticle;
import org.openapitools.server.api.verticle.StoreApi;
import org.openapitools.server.api.verticle.StoreApiImpl;
import org.openapitools.server.api.verticle.StoreApiVerticle;
import org.openapitools.server.api.verticle.UserApi;
import org.openapitools.server.api.verticle.UserApiImpl;
import org.openapitools.server.api.verticle.UserApiVerticle;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import io.vertx.core.Vertx;
import lombok.extern.slf4j.Slf4j;
import service.ApplicationApiVerticle;
import service.HelloService;


@Slf4j
public class ServiceBinder extends AbstractModule
{

    @Provides
    @Singleton
    public ApplicationApiVerticle provideApplicationApi( Vertx vertx, PetApiVerticle petApiVerticle,
                                                         UserApiVerticle userApiVerticle,
                                                         StoreApiVerticle storeApiVerticle )
    {
        ApplicationApiVerticle apiVerticle =
                new ApplicationApiVerticle( vertx, petApiVerticle, userApiVerticle, storeApiVerticle );
        apiVerticle.setServerPort( 9092 );
        return apiVerticle;
    }


    @Provides
    @Singleton
    public PetApiVerticle providePetApiVerticle( PetApi petApi )
    {
        return new PetApiVerticle( petApi );
    }


    @Provides
    @Singleton
    public UserApiVerticle provideUserApiVerticle( UserApi userApi )
    {
        return new UserApiVerticle( userApi );
    }


    @Provides
    @Singleton
    public StoreApiVerticle provideStoreApiVerticle( StoreApi storeApi )
    {
        return new StoreApiVerticle( storeApi );
    }


    @Provides
    @Singleton
    public PetApi providePetApi( HelloService helloService )
    {
        return new PetApiImpl( helloService );
    }


    @Provides
    @Singleton
    public UserApi provideUserApi( HelloService helloService )
    {
        return new UserApiImpl( helloService );
    }


    @Provides
    @Singleton
    public StoreApi provideStoreApi( HelloService helloService )
    {
        return new StoreApiImpl( helloService );
    }


    @Provides
    @Singleton
    public HelloService provideService()
    {
        return new HelloService();
    }


    @Provides
    @Singleton
    public AppConfig provideAppConfig()
    {
        return new AppConfig( 30, 30 );
    }


    @Override
    protected void configure()
    {

    }
}