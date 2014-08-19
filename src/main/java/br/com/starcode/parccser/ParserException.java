package br.com.starcode.parccser;

/**
 * Exceptions in parsing.
 */
public class ParserException extends Exception {

    private static final long serialVersionUID = 1L;

    public ParserException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParserException(String message) {
        super(message);
    }

}
