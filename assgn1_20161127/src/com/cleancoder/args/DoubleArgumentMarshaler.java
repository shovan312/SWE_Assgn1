package com.cleancoder.args;

import static com.cleancoder.args.ArgsException.ErrorCode.*;

import java.util.*;

public class DoubleArgumentMarshaler implements ArgumentMarshaler {
private double doubleValue = 0;

public void set(final Iterator<String> currentArgument) throws ArgsException {
        String parameter = null;
        try {
                parameter = currentArgument.next();
                doubleValue = Double.parseDouble(parameter);
        }
        catch (final NoSuchElementException e) {
                throw new ArgsException(MISSING_DOUBLE);
        }
        catch (final NumberFormatException e) {
                throw new ArgsException(INVALID_DOUBLE, parameter);
        }
}

public static double getValue(final ArgumentMarshaler arg_marshaller) {
        if (arg_marshaller != null && arg_marshaller instanceof DoubleArgumentMarshaler) {
                return ((DoubleArgumentMarshaler) arg_marshaller).doubleValue;
        }
        else {
                return 0.0;
        }
}
}
