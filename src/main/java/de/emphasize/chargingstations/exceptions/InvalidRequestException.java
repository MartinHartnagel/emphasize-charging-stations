package de.emphasize.chargingstations.exceptions;

import lombok.EqualsAndHashCode;
import lombok.Value;

@SuppressWarnings("serial")
@Value()
@EqualsAndHashCode(callSuper=false)
public class InvalidRequestException extends RuntimeException {
	public InvalidRequestException(String message) {
		super(message);
	}
}
