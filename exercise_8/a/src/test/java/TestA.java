import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith( TestCallbacksA.class )
public class TestA
{

    @BeforeEach
    void setup()
    {

    }

    @AfterEach
    void tearDown()
    {

    }

    @Test
    void shouldReturnTrue()
    {
        assertTrue( true );
    }
}
