package pl.polsl.model;

import pl.polsl.model.Coder;
import pl.polsl.model.DictionaryException;
import pl.polsl.model.annotation.TestType;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test cases of coder method
 *
 * @author Piotr Musioł
 * @version 1.0
 */
public class CoderTest
{
    Coder coder;

    @Before
    public void setup()
    {
        coder = new Coder();
    }

    @TestType(method = TestType.Method.FAIL_MESSAGE)
    @Test
    public void lowerCaseLettersInputTest()
    {
        try
        {
            coder.code("proper input");
        } catch (DictionaryException e)
        {
            fail("Exception shouldn't be thrown");
        }
    }

    @TestType(method = TestType.Method.FAIL_MESSAGE)
    @Test
    public void upperCaseLettersInputTest()
    {
        try
        {
            coder.code("PROPER INPUT");
        } catch (DictionaryException e)
        {
            fail("Exception shouldn't be thrown");
        }
    }

    @TestType(method = TestType.Method.FAIL_MESSAGE)
    @Test
    public void blankInputTest()
    {
        try
        {
            coder.code("");
        } catch (DictionaryException e)
        {
            fail("Exception shouldn't be thrown");
        }
    }

    @TestType(method = TestType.Method.EXCEPTION)
    @Test(expected = DictionaryException.class)
    public void numbersInputTest() throws DictionaryException
    {
        coder.code("5 25 0");
    }

    @TestType(method = TestType.Method.EXCEPTION)
    @Test(expected = DictionaryException.class)
    public void punctuationMarksInput() throws DictionaryException
    {
        coder.code("-,!/?.");
    }

    @TestType(method = TestType.Method.FAIL_MESSAGE)
    @Test
    public void noInputTest()
    {
        try
        {
            coder.code(null);
        } catch (Exception e)
        {
            fail("Exception shouldn't be thrown\n" + e.getMessage());
        }
    }
}
