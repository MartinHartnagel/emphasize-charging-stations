package de.emphasize.chargingstations;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import de.emphasize.chargingstations.model.ChargingStation;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("itest")
@Category(IntegrationTestCategory.class)
public class GetChargingStatusSuccess {

	private static final UUID CHARGING_STATION_ID = UUID.fromString("22345678-4444-4444-4444-1234567890ab");

	private static final String POSTAL_CODE = "10829";

	@Autowired
	ChargingStationUtils utils;

	@Before
	public void setUp() {
		ChargingStation chargingStation = ChargingStation.builder() //
		    .id(CHARGING_STATION_ID) //
		    .longitude(13.3535702) //
		    .latitude(52.4812529) //
		    .postalCode("10829").build();
		utils.postChargingStation(chargingStation);
	}

	@Test
	public void testGetChargingStation() throws IOException {
		utils.getChargingStation(CHARGING_STATION_ID.toString());
		assertThat(utils.getStatusCode()).isEqualTo(HttpStatus.OK);
		ChargingStation result = utils.readResult();
		assertThat(result.getId()).isEqualTo(CHARGING_STATION_ID);
	}

	@Test
	public void testGetChargingStationsByPostalCode() throws IOException {
		utils.getChargingStations(POSTAL_CODE);
		assertThat(utils.getStatusCode()).isEqualTo(HttpStatus.OK);
		ChargingStation[] result = utils.readResults();
		assertThat(result.length).isEqualTo(1);
		assertThat(result[0].getId()).isEqualTo(CHARGING_STATION_ID);
	}
}
