package com.cleancoder.args;

import static com.cleancoder.args.ArgsException.ErrorCode.*;

import java.util.*;

public class IntegerArgumentMarshaler implements ArgumentMarshaler {
private int intValue = 0;

public void set(final Iterator<String> currentArgument) throws ArgsException {
        String parameter = null;
        try {
                parameter = currentArgument.next();
                intValue  = Integer.parseInt(parameter);
        }
        catch (final NoSuchElementException e) {
                throw new ArgsException(MISSING_INTEGER);
        }
        catch (final NumberFormatException e) {
                throw new ArgsException(INVALID_INTEGER, parameter);
        }
}

public static int getValue(final ArgumentMarshaler arg_marshaller) {
        if (arg_marshaller != null && arg_marshaller instanceof IntegerArgumentMarshaler)
                return ((IntegerArgumentMarshaler) arg_marshaller).intValue;
        else
                return 0;
}
}
