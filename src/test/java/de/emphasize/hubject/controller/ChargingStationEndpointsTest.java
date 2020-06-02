package de.emphasize.chargingstations.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import de.emphasize.chargingstations.exceptions.ChargingStationByIdNotFoundException;
import de.emphasize.chargingstations.exceptions.ChargingStationIdAlreadyExistsException;
import de.emphasize.chargingstations.model.ChargingStation;
import de.emphasize.chargingstations.services.ChargingStationService;

@RunWith(MockitoJUnitRunner.class)
public class ChargingStationEndpointsTest {

	@Mock
	private ChargingStationService chargingStationService;

	@InjectMocks
	private ChargingStationEndpoints unit;

	@Test
	public void whenAddingWithNewId() {
		UUID id = UUID.randomUUID();
		ChargingStation expected = ChargingStation.builder().id(id).postalCode("freifeld").build();
		unit.postChargingStation(expected);
	}

	@Test(expected = ChargingStationIdAlreadyExistsException.class)
	public void whenAddingWithAlreadyExistingId() {
		UUID id = UUID.randomUUID();
		ChargingStation expected = ChargingStation.builder().id(id).postalCode("freifeld").build();
		doAnswer(invocation -> {
			throw new ChargingStationIdAlreadyExistsException(id);
		}).when(chargingStationService).addChargingStation(eq(expected));

		unit.postChargingStation(expected);
	}

	@Test
	public void whenExistingId() {
		UUID id = UUID.randomUUID();
		ChargingStation expected = ChargingStation.builder().id(id).postalCode("freifeld").build();
		when(chargingStationService.getChargingStation(id)).thenReturn(expected);
		assertThat(unit.getChargingStation(id)).isEqualTo(ResponseEntity.ok(expected));
	}

	@Test
	public void whenNotExistingId() {
		UUID id = UUID.randomUUID();
		when(chargingStationService.getChargingStation(id)).thenThrow(new ChargingStationByIdNotFoundException(id));
		try {
			unit.getChargingStation(id);
			fail("Exception expected");
		} catch (ChargingStationByIdNotFoundException e) {
			assertThat(e.getId()).isEqualTo(id);
		}
	}
}
