package service;


import java.io.File;
import java.util.List;

import org.openapitools.server.api.MainApiException;
import org.openapitools.server.api.model.ModelApiResponse;
import org.openapitools.server.api.model.Pet;
import org.openapitools.server.api.verticle.PetApi;

import com.google.inject.Inject;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class PetApiImpl implements PetApi
{
    private HelloService helloService;


    @Inject
    public PetApiImpl( final HelloService helloService )
    {
        this.helloService = helloService;
    }


    @Override
    public void addPet( final Pet pet, final Handler<AsyncResult<Void>> handler )
    {

    }


    @Override
    public void deletePet( final Long petId, final String apiKey, final Handler<AsyncResult<Void>> handler )
    {

    }


    @Override
    public void findPetsByStatus( final List<String> status, final Handler<AsyncResult<List<Pet>>> handler )
    {

    }


    @Override
    public void findPetsByTags( final List<String> tags, final Handler<AsyncResult<List<Pet>>> handler )
    {

    }


    @Override
    public void getPetById( final Long petId, final Handler<AsyncResult<Pet>> resultHandler )
    {
        helloService.hello( String.valueOf( petId ) );

        if ( petId == 1 )
        {
            Pet pet = new Pet();
            pet.setId( petId );
            pet.setName( "Cat" );

            resultHandler.handle( Future.succeededFuture( pet ) );
        }
        else
        {
            resultHandler.handle( Future.failedFuture( new MainApiException( 404, "Pet not found." ) ) );
        }
    }


    @Override
    public void updatePet( final Pet pet, final Handler<AsyncResult<Void>> handler )
    {

    }


    @Override
    public void updatePetWithForm( final Long petId, final String name, final String status,
                                   final Handler<AsyncResult<Void>> handler )
    {

    }


    @Override
    public void uploadFile( final Long petId, final String additionalMetadata, final File file,
                            final Handler<AsyncResult<ModelApiResponse>> handler )
    {

    }
}
