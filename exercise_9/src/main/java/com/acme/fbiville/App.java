package com.acme.fbiville;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.util.concurrent.Callable;

@Command( name = "hello", version = "hello 1.0", description = "prints a greeting message" )
public class App implements Callable<Integer>
{

    @Parameters( index = "0", description = "who am i talking to?" )
    private String name;

    @Option( names = {"-g", "--greet-with"}, description = "what word to use as a greeter, i.e. Hello, Bonjour, Merhaba, ..." )
    private String greeting = "Hello";

    @Override
    public Integer call() throws Exception
    {
        System.out.printf( "%s %s!\n", greeting, name );
        return 0;
    }

    public static void main( String... args )
    {
        int exitCode = new CommandLine( new App() ).execute( args );
        System.exit( exitCode );
    }
}
