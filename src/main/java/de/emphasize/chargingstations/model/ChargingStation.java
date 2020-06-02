package de.emphasize.chargingstations.model;

import java.util.UUID;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import lombok.Builder;
import lombok.Value;

/**
 * Contains a charging station.
 */
@Value
@Builder
@JsonDeserialize(builder = ChargingStation.ChargingStationBuilder.class)
public class ChargingStation {

	UUID id;

	Double longitude;

	Double latitude;

	String postalCode;

	@JsonPOJOBuilder(withPrefix = "")
	public static final class ChargingStationBuilder {

	}
}
