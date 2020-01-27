package com.cleancoder.args;

import junit.framework.TestCase;

import static com.cleancoder.args.ArgsException.ErrorCode.*;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * <h1>Test Args Exceptions</h1>
 * <p>
 * The following tests are used to test the
 * ArgsException class and make sure each error is
 * raised correctly
 * </p>
 * 
 * @author      Shovan Swain
 * @version     1.0
 * {@link ArgsException}
 */
public class ArgsExceptionTest extends TestCase {
public static void main(String[] args) {
        Result result = JUnitCore.runClasses(ArgsExceptionTest.class);

        for (Failure failure : result.getFailures()) {
                System.out.println(failure.toString());
        }

        System.out.printf("Ran %d Tests in %d time\n", result.getRunCount(), result.getRunTime());
        System.out.printf("Passed %d tests\n", result.getRunCount() - result.getIgnoreCount()- result.getFailureCount());
        System.out.printf("Ignored %d tests\n", result.getFailureCount());
}

public void testUnexpectedMessage() throws Exception {
        ArgsException error = new ArgsException(UNEXPECTED_ARGUMENT, 'x', null);

        assertEquals("Argument -x unexpected.", error.errorMessage());
}

public void testMissingStringMessage() throws Exception {
        ArgsException error = new ArgsException(MISSING_STRING, 'x', null);

        assertEquals("Could not find string parameter for -x.", error.errorMessage());
}

public void testInvalidIntegerMessage() throws Exception {
        ArgsException error = new ArgsException(INVALID_INTEGER, 'x', "Forty two");

        assertEquals("Argument -x expects an integer but was 'Forty two'.", error.errorMessage());
}

public void testMissingIntegerMessage() throws Exception {
        ArgsException error = new ArgsException(MISSING_INTEGER, 'x', null);

        assertEquals("Could not find integer parameter for -x.", error.errorMessage());
}

public void testInvalidDoubleMessage() throws Exception {
        ArgsException error = new ArgsException(INVALID_DOUBLE, 'x', "Forty two");

        assertEquals("Argument -x expects a double but was 'Forty two'.", error.errorMessage());
}

public void testMissingDoubleMessage() throws Exception {
        ArgsException error = new ArgsException(MISSING_DOUBLE, 'x', null);

        assertEquals("Could not find double parameter for -x.", error.errorMessage());
}

public void testMissingMapMessage() throws Exception {
        ArgsException error = new ArgsException(MISSING_MAP, 'x', null);

        assertEquals("Could not find map string for -x.", error.errorMessage());
}

public void testMalformedMapMessage() throws Exception {
        ArgsException error = new ArgsException(MALFORMED_MAP, 'x', null);

        assertEquals("Map string for -x is not of form k1:v1,k2:v2...", error.errorMessage());
}

public void testInvalidArgumentName() throws Exception {
        ArgsException error = new ArgsException(INVALID_ARGUMENT_NAME, '#', null);

        assertEquals("'#' is not a valid argument name.", error.errorMessage());
}

public void testInvalidFormat() throws Exception {
        ArgsException error = new ArgsException(INVALID_ARGUMENT_FORMAT, 'x', "$");

        assertEquals("'$' is not a valid argument format.", error.errorMessage());
}

public void testArgWithoutFlag() throws Exception {
        ArgsException error = new ArgsException(ARG_WITHOUT_FLAG);

        assertEquals("Random Argument was provided without flag specification", error.errorMessage());
}
}
