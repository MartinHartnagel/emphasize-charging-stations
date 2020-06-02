package de.emphasize.chargingstations.exceptions;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.UUID;

import org.junit.Test;

import de.emphasize.chargingstations.model.ChargingStation;
import de.emphasize.chargingstations.services.ChargingStationService;

public class ChargingStationServiceTest {
	private ChargingStationService unit = new ChargingStationService();

	@Test
	public void whenEmptyArray() throws IOException {
		assertThat(unit.getChargingStations()).isEmpty();
	}

	@Test
	public void getOne() throws IOException {
		ChargingStation expected = ChargingStation.builder() //
		    .id(UUID.fromString("12345678-5555-4444-4444-1234567890ab")) //
		    .longitude(13.3535702) //
		    .latitude(52.4812529) //
		    .postalCode("10829").build();
		unit.addChargingStation(expected);
		assertThat(unit.getChargingStation(UUID.fromString("12345678-5555-4444-4444-1234567890ab"))).isEqualTo(expected);
	}

	@Test(expected = ChargingStationByIdNotFoundException.class)
	public void getNotExisting() throws IOException {
		unit.getChargingStation(UUID.fromString("12345678-5555-4444-4444-1234567890cc"));
	}
}
