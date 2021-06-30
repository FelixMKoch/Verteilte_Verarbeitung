package de.throsenheim.presentation.controller.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The ID that is being posted already exists in this certain database.
 */
@ResponseStatus (value = HttpStatus.BAD_REQUEST)
public class IdAlreadyExistsException extends RuntimeException{

}
