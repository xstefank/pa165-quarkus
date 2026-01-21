package cz.muni.fi.pa165.socialnetwork.exceptions;

public class EmailValidationFailedException extends RuntimeException {
    public EmailValidationFailedException(String string) {
        super(string);
    }
}
