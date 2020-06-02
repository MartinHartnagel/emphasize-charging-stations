package de.emphasize.chargingstations;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.UUID;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import de.emphasize.chargingstations.model.ErrorDto;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("itest")
@Category(IntegrationTestCategory.class)
public class ChargingStatusErrorResponseCauseByNotFoundId {

	private static final UUID CHARGING_STATION_ID = UUID.randomUUID();

	@Autowired
	ChargingStationUtils utils;

	@Test
	public void testChargingStationNotFoundById() throws IOException {
		utils.getChargingStation(CHARGING_STATION_ID.toString());
		assertThat(utils.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		ErrorDto errorResult = utils.readErrorResult();
		assertThat(errorResult.getData().get("id")).isEqualTo(CHARGING_STATION_ID.toString());
	}
}
