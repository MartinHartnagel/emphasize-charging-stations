package de.emphasize.chargingstations.exceptions;

import java.util.UUID;

import lombok.EqualsAndHashCode;
import lombok.Value;

@SuppressWarnings("serial")
@Value()
@EqualsAndHashCode(callSuper=false)
public class ChargingStationByIdNotFoundException extends RuntimeException {
	UUID id;

	public ChargingStationByIdNotFoundException(UUID id) {
		super(String.format("Charging Station with ID '%s' not found", id));
		this.id = id;
	}
}
