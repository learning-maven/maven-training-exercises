import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class TestCallbacksA extends TestCallbacksB implements BeforeEachCallback, AfterEachCallback
{
    @Override
    public void beforeEach( ExtensionContext extensionContext ) throws Exception
    {
        System.out.printf( "setting up test: %s\n", extensionContext.getTestMethod().orElseThrow() );
    }

    @Override
    public void afterEach( ExtensionContext extensionContext ) throws Exception
    {
        System.out.printf( "cleaning up test: %s\n", extensionContext.getTestMethod().orElseThrow() );
    }
}
