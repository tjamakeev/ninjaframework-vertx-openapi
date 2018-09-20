package service;


import lombok.extern.slf4j.Slf4j;


@Slf4j
public class HelloService
{
    public void hello( final String message )
    {
        log.info( "Hello " + message );
    }
}
