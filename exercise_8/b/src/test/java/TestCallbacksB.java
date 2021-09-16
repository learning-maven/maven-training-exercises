import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class TestCallbacksB
        implements BeforeTestExecutionCallback, AfterTestExecutionCallback
{
    public void beforeTestExecution( ExtensionContext extensionContext ) throws Exception
    {
        System.out.printf( "starting test: %s\n", extensionContext.getTestMethod().orElseThrow() );
    }

    public void afterTestExecution( ExtensionContext extensionContext ) throws Exception
    {
        System.out.printf( "finished test: %s\n", extensionContext.getTestMethod().orElseThrow() );
    }
}
