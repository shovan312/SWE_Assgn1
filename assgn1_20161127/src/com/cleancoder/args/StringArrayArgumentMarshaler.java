package com.cleancoder.args;

import static com.cleancoder.args.ArgsException.ErrorCode.*;

import java.util.*;

public class StringArrayArgumentMarshaler implements ArgumentMarshaler {
private final List<String> strings = new ArrayList<String>();

public void set(final Iterator<String> currentArgument) throws ArgsException {
        try {
                strings.add(currentArgument.next());
        }
        catch (final NoSuchElementException e) {
                throw new ArgsException(MISSING_STRING);
        }
}

public static String[] getValue(final ArgumentMarshaler arg_marshaler) {
        if (arg_marshaler != null && arg_marshaler instanceof StringArrayArgumentMarshaler)
                return ((StringArrayArgumentMarshaler) arg_marshaler).strings.toArray(new String[0]);
        else
                return new String[0];
}
}
