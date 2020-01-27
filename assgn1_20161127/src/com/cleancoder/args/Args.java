package com.cleancoder.args;

import java.util.*;

import static com.cleancoder.args.ArgsException.ErrorCode.*;

/**
 * <h1>Args</h1>
 * <p>
 * The Args class implements an interface for users 
 * to provide a schema for command line arguments
 * with flags and create a map between each identifier
 * and the corresponding argument provided.
 * </p>
 * 
 * @author            Shovan Swain
 * @version           1.0
 * @since             2019-01-27
 * @param  schema     Command Line Argument schema
 * @param  args       String Array of command line arguments
 */
public class Args {

private Map<Character, ArgumentMarshaler> marshalers;
private Set<Character>                    argsFound;
private ListIterator<String>              currentArgument;

public Args(String schema, String[] args) throws ArgsException {
        marshalers  =  new HashMap<Character, ArgumentMarshaler>();
        argsFound   =  new HashSet<Character>();

        parseSchema(schema);
        parseArgumentStrings(Arrays.asList(args));
}

private void parseSchema(String schema) throws ArgsException {
        // schema gives the argument identifiers
        for (String element : schema.split(","))
                if (element.length() > 0) {
                        parseSchemaElement(element.trim());
                }

}

private void parseSchemaElement(String element) throws ArgsException {
        char elementId    = element.charAt(0);
        String elementTail  = element.substring(1);

        validateSchemaElementId(elementId);
        // Add elements to Marshaler map
        if (elementTail.length() == 0) {
                marshalers.put(elementId, new BooleanArgumentMarshaler());
        }
        else if (elementTail.equals("*")) {
                marshalers.put(elementId, new StringArgumentMarshaler());
        }
        else if (elementTail.equals("#")) {
                marshalers.put(elementId, new IntegerArgumentMarshaler());
        }
        else if (elementTail.equals("##")) {
                marshalers.put(elementId, new DoubleArgumentMarshaler());
        }
        else if (elementTail.equals("[*]")) {
                marshalers.put(elementId, new StringArrayArgumentMarshaler());
        }
        else if (elementTail.equals("&")) {
                marshalers.put(elementId, new MapArgumentMarshaler());
        }
        else {
                throw new ArgsException(
                              INVALID_ARGUMENT_FORMAT,
                              elementId,
                              elementTail
                              );
        }
}

private void validateSchemaElementId(char elementId) throws ArgsException {
        // Validates each element of the schema
        if (!Character.isLetter(elementId)) {
                throw new ArgsException(
                              INVALID_ARGUMENT_NAME,
                              elementId,
                              null
                              );
        }
}

private void parseArgumentStrings(List<String> argsList) throws ArgsException {
        // Iterate over list of arguments and parse
        for (currentArgument = argsList.listIterator(); currentArgument.hasNext();) {
                String argString = currentArgument.next();

                if (argString.startsWith("-")) {
                        parseArgumentCharacters(argString.substring(1));
                }
                else {
                        throw new ArgsException(ARG_WITHOUT_FLAG);
                }
        }
}

private void parseArgumentCharacters(String argChars) throws ArgsException {
        for (int i = 0; i < argChars.length(); i++) {
                parseArgumentCharacter(argChars.charAt(i));
        }
}

private void parseArgumentCharacter(char argChar) throws ArgsException {
        // Fetch marshaller corresponding to each identifier
        ArgumentMarshaler m = marshalers.get(argChar);
        if (m == null) {
                throw new ArgsException(UNEXPECTED_ARGUMENT, argChar, null);
        }
        else {
                argsFound.add(argChar);
                try {
                        m.set(currentArgument);
                }
                catch (ArgsException e) {
                        e.setErrorArgumentId(argChar);
                        throw e;
                }
        }
}

public boolean has(char arg) {
        return argsFound.contains(arg);
}

public int nextArgument() {
        return currentArgument.nextIndex();
}

public boolean getBoolean(char arg) {
        return BooleanArgumentMarshaler.getValue(marshalers.get(arg));
}

public String getString(char arg) {
        return StringArgumentMarshaler.getValue(marshalers.get(arg));
}

public int getInt(char arg) {
        return IntegerArgumentMarshaler.getValue(marshalers.get(arg));
}

public double getDouble(char arg) {
        return DoubleArgumentMarshaler.getValue(marshalers.get(arg));
}

public String[] getStringArray(char arg) {
        return StringArrayArgumentMarshaler.getValue(marshalers.get(arg));
}

public Map<String, String> getMap(char arg) {
        return MapArgumentMarshaler.getValue(marshalers.get(arg));
}
}
