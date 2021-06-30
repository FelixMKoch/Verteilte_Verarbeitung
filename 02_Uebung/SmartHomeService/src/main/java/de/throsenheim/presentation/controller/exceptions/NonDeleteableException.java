package de.throsenheim.presentation.controller.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The entry that is to be deleted cannot be deleted
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class NonDeleteableException extends RuntimeException{
}
