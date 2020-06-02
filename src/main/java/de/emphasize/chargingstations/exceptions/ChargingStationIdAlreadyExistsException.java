package de.emphasize.chargingstations.exceptions;

import java.util.UUID;

import lombok.EqualsAndHashCode;
import lombok.Value;

@SuppressWarnings("serial")
@Value()
@EqualsAndHashCode(callSuper=false)
public class ChargingStationIdAlreadyExistsException extends RuntimeException {
	UUID id;

	public ChargingStationIdAlreadyExistsException(UUID id) {
		super(String.format("Charging Station with ID '%s' already exists", id));
		this.id = id;
	}
}
