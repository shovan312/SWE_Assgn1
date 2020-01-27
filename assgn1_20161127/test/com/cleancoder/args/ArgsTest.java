package com.cleancoder.args;

import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.util.Map;

import static com.cleancoder.args.ArgsException.ErrorCode.*;
import static org.junit.Assert.*;

/**
 * <h1>Args Test</h1>
 * This Class is used to test multiple scenarios
 * of arguments and schema being given to the 
 * Args class
 * 
 * @author Shovan Swain
 * {@link Args}
 */
public class ArgsTest {
public static void main(String[] args) {
        Result result = JUnitCore.runClasses(ArgsTest.class);

        for (Failure failure : result.getFailures()) {
                System.out.println(failure.toString());
        }

        System.out.printf("Ran %d Tests in %d time\n", result.getRunCount(), result.getRunTime());
        System.out.printf("Passed %d tests\n", result.getRunCount() - result.getIgnoreCount()- result.getFailureCount());
        System.out.printf("Ignored %d tests\n", result.getFailureCount());
}

@Test
public void testCreateWithNoSchemaOrArguments() throws Exception {

        Args args = new Args("", new String[0]);
        assertEquals(0, args.nextArgument());
}


@Test
public void testWithNoSchemaButWithOneArgument() throws Exception {
        try {

                new Args("", new String[] {"-x"});
                fail();

        }
        catch (ArgsException error) {

                assertEquals(UNEXPECTED_ARGUMENT, error.getErrorCode());
                assertEquals('x', error.getErrorArgumentId());

        }
}

@Test
public void testWithNoSchemaButWithMultipleArguments() throws Exception {
        try {
                new Args("", new String[] {"-x", "-y"});
                fail();
        }
        catch (ArgsException error) {

                assertEquals(UNEXPECTED_ARGUMENT, error.getErrorCode());
                assertEquals('x', error.getErrorArgumentId());

        }

}

@Test
public void testNonLetterSchema() throws Exception {
        try {
                new Args("*", new String[] {});
                fail("Args constructor should have thrown exception");
        }
        catch (ArgsException error) {

                assertEquals(INVALID_ARGUMENT_NAME, error.getErrorCode());
                assertEquals('*', error.getErrorArgumentId());

        }
}

@Test
public void testInvalidArgumentFormat() throws Exception {
        try {
                new Args("f~", new String[] {});
                fail("Args constructor should have throws exception");
        }
        catch (ArgsException error) {

                assertEquals(INVALID_ARGUMENT_FORMAT, error.getErrorCode());
                assertEquals('f', error.getErrorArgumentId());

        }
}

@Test
public void testSimpleBooleanPresent() throws Exception {
        Args args = new Args("x", new String[] {"-x"});

        assertEquals(true, args.getBoolean('x'));
        assertEquals(1, args.nextArgument());

}

@Test
public void testSimpleStringPresent() throws Exception {
        Args args = new Args("x*", new String[] {"-x", "param"});

        assertTrue(args.has('x'));

        assertEquals("param", args.getString('x'));
        assertEquals(2, args.nextArgument());
}

@Test
public void testMissingStringArgument() throws Exception {
        try {
                new Args("x*", new String[] {"-x"});
                fail();
        }
        catch (ArgsException error) {

                assertEquals(MISSING_STRING, error.getErrorCode());
                assertEquals('x', error.getErrorArgumentId());

        }
}

@Test
public void testSpacesInFormat() throws Exception {

        Args args = new Args("x, y", new String[] {"-xy"});

        assertTrue(args.has('x'));
        assertTrue(args.has('y'));

        assertEquals(1, args.nextArgument());

}

@Test
public void testSimpleIntPresent() throws Exception {

        Args args = new Args("x#", new String[] {"-x", "42"});

        assertTrue(args.has('x'));

        assertEquals(42, args.getInt('x'));
        assertEquals(2, args.nextArgument());

}

@Test
public void testInvalidInteger() throws Exception {
        try {
                new Args("x#", new String[] {"-x", "Forty two"});
                fail();
        }
        catch (ArgsException error) {

                assertEquals(INVALID_INTEGER, error.getErrorCode());
                assertEquals('x', error.getErrorArgumentId());
                assertEquals("Forty two", error.getErrorParameter());

        }

}

@Test
public void testMissingInteger() throws Exception {
        try {
                new Args("x#", new String[] {"-x"});
                fail();
        }
        catch (ArgsException error) {

                assertEquals(MISSING_INTEGER, error.getErrorCode());
                assertEquals('x', error.getErrorArgumentId());

        }
}

@Test
public void testSimpleDoublePresent() throws Exception {

        Args args = new Args("x##", new String[] {"-x", "42.3"});

        assertTrue(args.has('x'));

        assertEquals(42.3, args.getDouble('x'), .001);

}

@Test
public void testInvalidDouble() throws Exception {
        try {
                new Args("x##", new String[] {"-x", "Forty two"});
                fail();
        }
        catch (ArgsException error) {

                assertEquals(INVALID_DOUBLE, error.getErrorCode());
                assertEquals('x', error.getErrorArgumentId());
                assertEquals("Forty two", error.getErrorParameter());

        }
}

@Test
public void testMissingDouble() throws Exception {
        try {
                new Args("x##", new String[] {"-x"});
                fail();
        }
        catch (ArgsException error) {

                assertEquals(MISSING_DOUBLE, error.getErrorCode());
                assertEquals('x', error.getErrorArgumentId());

        }
}

@Test
public void testStringArray() throws Exception {
        Args args = new Args("x[*]", new String[] {"-x", "alpha"});

        assertTrue(args.has('x'));

        String[] result = args.getStringArray('x');

        assertEquals(1, result.length);
        assertEquals("alpha", result[0]);

}

@Test
public void testMissingStringArrayElement() throws Exception {
        try {
                new Args("x[*]", new String[] {"-x"});
                fail();
        }
        catch (ArgsException error) {

                assertEquals(MISSING_STRING, error.getErrorCode());
                assertEquals('x', error.getErrorArgumentId());

        }
}

@Test
public void manyStringArrayElements() throws Exception {

        Args args = new Args("x[*]", new String[] {"-x", "alpha", "-x", "beta", "-x", "gamma"});

        assertTrue(args.has('x'));

        String[] result = args.getStringArray('x');

        assertEquals(3, result.length);
        assertEquals("alpha", result[0]);
        assertEquals("beta", result[1]);
        assertEquals("gamma", result[2]);

}

@Test
public void MapArgument() throws Exception {

        Args args = new Args("f&", new String[] {"-f", "key1:val1,key2:val2"});

        assertTrue(args.has('f'));

        Map<String, String> map = args.getMap('f');

        assertEquals("val1", map.get("key1"));
        assertEquals("val2", map.get("key2"));

}

@Test(expected=ArgsException.class)
public void malFormedMapArgument() throws Exception {

        Args args = new Args("f&", new String[] {"-f", "key1:val1,key2"});

}

@Test
public void oneMapArgument() throws Exception {

        Args args = new Args("f&", new String[] {"-f", "key1:val1"});

        assertTrue(args.has('f'));

        Map<String, String> map = args.getMap('f');

        assertEquals("val1", map.get("key1"));

}

}
