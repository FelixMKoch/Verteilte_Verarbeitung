package de.throsenheim.application.connections.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This certain dependency does not exist
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class DependenyDoesNotExistException extends RuntimeException{
}
