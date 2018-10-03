package com.kotlarz;

import com.kotlarz.server.Server;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

public class App
{
    public static void main( String[] args )
    {
        parseArgs( args, () -> Server.start() );
    }

    private static void parseArgs( String[] args, Runnable handler )
    {
        ArgumentParser parser = ArgumentParsers.newFor( "Peka proxy" ).build()
                        .defaultHelp( true )
                        .description( "Proxies requests to PEKA VM web service" );
        parser.addArgument( "-k", "--keystore" )
                        .required( true )
                        .help( "Keystore path" );
        parser.addArgument( "-p", "--password" )
                        .required( true )
                        .help( "Keystore password" );

        try
        {
            Namespace namespace = parser.parseArgs( args );
            AppArguments.KEYSTORE_PATH = namespace.get( "keystore" ).toString();
            AppArguments.KEYSTORE_PASSWORD = namespace.get( "password" ).toString();

            handler.run();
        }
        catch ( ArgumentParserException e )
        {
            parser.handleError( e );
        }
    }
}
