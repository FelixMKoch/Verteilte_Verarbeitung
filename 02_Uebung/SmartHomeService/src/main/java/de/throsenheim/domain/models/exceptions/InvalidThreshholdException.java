package de.throsenheim.domain.models.exceptions;

/**
 * Exception, falls der Theshhold im Rules-Model falsch gewählt wurde
 */
public class InvalidThreshholdException extends RuntimeException{
    @Override
    public String getMessage() {
        return "Eingegebener Threshhold mus szwischen 0 und 29 liegen!";
    }
}
