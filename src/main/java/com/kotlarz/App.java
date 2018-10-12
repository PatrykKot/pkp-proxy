package com.kotlarz;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import com.kotlarz.config.container.GuiceModule;
import com.kotlarz.config.handler.RequestHandler;
import com.kotlarz.config.server.ServerServiceInitializer;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;
import spark.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class App
{
    public static void main( String[] args )
    {
        parseArgs( args, App::startApplication );
    }

    private static void parseArgs( String[] args, Runnable handler )
    {
        ArgumentParser parser = ArgumentParsers.newFor( "Peka proxy" ).build()
                        .defaultHelp( true )
                        .description( "Proxies requests to PEKA VM web service" );
        parser.addArgument( "-k", "--keystore" )
                        .help( "Keystore path" );
        parser.addArgument( "-p", "--password" )
                        .help( "Keystore password" );

        try
        {
            Namespace namespace = parser.parseArgs( args );
            AppArguments.KEYSTORE_PATH = namespace.get( "keystore" );
            AppArguments.KEYSTORE_PASSWORD = namespace.get( "password" );

            handler.run();
        }
        catch ( ArgumentParserException e )
        {
            parser.handleError( e );
        }
    }

    private static void startApplication()
    {
        log.info( "Creating dependency container" );
        Injector injector = Guice.createInjector( new GuiceModule() );

        log.info( "Looking for request handlers" );
        List<RequestHandler> handlers = getHandlers( injector );

        log.info( "Creating service" );
        Service service = ServerServiceInitializer.start();

        log.info( "Registering request handlers" );
        handlers.forEach( requestHandler -> {
            log.info( "Registering handler {}", requestHandler.getClass().getSimpleName() );
            requestHandler.register( service );
        } );

        log.info( "Application started on port " + service.port() );
    }

    private static List<RequestHandler> getHandlers( Injector injector )
    {
        return injector.getAllBindings()
                        .keySet()
                        .stream()
                        .map( Key::getTypeLiteral )
                        .map( TypeLiteral::getRawType )
                        .filter( RequestHandler.class::isAssignableFrom )
                        .map( injector::getInstance )
                        .map( RequestHandler.class::cast )
                        .collect( Collectors.toList() );
    }
}
