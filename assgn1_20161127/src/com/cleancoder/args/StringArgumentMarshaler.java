package com.cleancoder.args;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static com.cleancoder.args.ArgsException.ErrorCode.MISSING_STRING;

public class StringArgumentMarshaler implements ArgumentMarshaler {
private String stringValue = "";

public void set(final Iterator<String> currentArgument) throws ArgsException {
        try {
                stringValue = currentArgument.next();
        }
        catch (final NoSuchElementException e) {
                throw new ArgsException(MISSING_STRING);
        }
}

public static String getValue(final ArgumentMarshaler arg_marshaller) {
        if (arg_marshaller != null && arg_marshaller instanceof StringArgumentMarshaler)
                return ((StringArgumentMarshaler) arg_marshaller).stringValue;
        else
                return "";
}
}
