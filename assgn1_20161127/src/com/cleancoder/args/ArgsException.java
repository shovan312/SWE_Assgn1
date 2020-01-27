package com.cleancoder.args;

import static com.cleancoder.args.ArgsException.ErrorCode.*;

/**
 * <h1>ArgsException</h1>
 * <p>
 * The ArgsException class is an extension of
 * the Exception class pre-built in JAVA to handle
 * the exceptions that could come up while using
 * instances of Args class.
 * </p>
 * 
 * @author                  Shovan Swain
 * @since                   2019-01-27
 * @version                 1.0
 * @param   Message         Message to be given while raising exception
 * @param   errorCode       error code for different kinds of exceptions
 * @param   errorArgumentID
 * @param   errorParameter
 */
public class ArgsException extends Exception {
private static final long serialVersionUID = 4211725500805170404L;
private char errorArgumentId = '\0';
private String errorParameter  = null;
private ErrorCode errorCode       = OK;

public ArgsException() {
}

public ArgsException(final String message) {
        super(message);
}

public ArgsException(final ErrorCode errorCode) {
        this.errorCode = errorCode;
}

public ArgsException(final ErrorCode errorCode, final String errorParameter) {
        this.errorCode = errorCode;
        this.errorParameter = errorParameter;
}

public ArgsException(final ErrorCode errorCode, final char errorArgumentId, final String errorParameter) {
        this.errorCode = errorCode;
        this.errorParameter = errorParameter;
        this.errorArgumentId = errorArgumentId;
}

public char getErrorArgumentId() {
        return errorArgumentId;
}

public void setErrorArgumentId(final char errorArgumentId) {
        this.errorArgumentId = errorArgumentId;
}

public String getErrorParameter() {
        return errorParameter;
}

public void setErrorParameter(final String errorParameter) {
        this.errorParameter = errorParameter;
}

public ErrorCode getErrorCode() {
        return errorCode;
}

public void setErrorCode(final ErrorCode errorCode) {
        this.errorCode = errorCode;
}

public String errorMessage() {
        switch (errorCode) {
        case OK:
                return "TILT: Should not get here.";

        case UNEXPECTED_ARGUMENT:
                return String.format(
                        "Argument -%c unexpected.",
                        errorArgumentId
                        );

        case MISSING_STRING:
                return String.format(
                        "Could not find string parameter for -%c.",
                        errorArgumentId
                        );

        case INVALID_INTEGER:
                return String.format(
                        "Argument -%c expects an integer but was '%s'.",
                        errorArgumentId, errorParameter
                        );

        case MISSING_INTEGER:
                return String.format(
                        "Could not find integer parameter for -%c.",
                        errorArgumentId
                        );

        case INVALID_DOUBLE:
                return String.format(
                        "Argument -%c expects a double but was '%s'.",
                        errorArgumentId,
                        errorParameter
                        );

        case MISSING_DOUBLE:
                return String.format(
                        "Could not find double parameter for -%c.",
                        errorArgumentId
                        );

        case INVALID_ARGUMENT_NAME:
                return String.format(
                        "'%c' is not a valid argument name.",
                        errorArgumentId
                        );

        case INVALID_ARGUMENT_FORMAT:
                return String.format(
                        "'%s' is not a valid argument format.",
                        errorParameter
                        );

        case MISSING_MAP:
                return String.format(
                        "Could not find map string for -%c.",
                        errorArgumentId
                        );

        case MALFORMED_MAP:
                return String.format(
                        "Map string for -%c is not of form k1:v1,k2:v2...",
                        errorArgumentId
                        );

        case ARG_WITHOUT_FLAG:
                return String.format(
                        "Random Argument was provided without flag specification"
                        );

        }
        return "";
}

public enum ErrorCode {
        OK,
        INVALID_ARGUMENT_FORMAT,
        UNEXPECTED_ARGUMENT,
        INVALID_ARGUMENT_NAME,
        MISSING_STRING,
        MISSING_INTEGER,
        INVALID_INTEGER,
        MISSING_DOUBLE,
        MALFORMED_MAP,
        MISSING_MAP,
        INVALID_DOUBLE,
        ARG_WITHOUT_FLAG,
}
}
