package de.emphasize.chargingstations.config;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.Test;
import org.springframework.http.converter.HttpMessageNotReadableException;

import de.emphasize.chargingstations.exceptions.ChargingStationByIdNotFoundException;
import de.emphasize.chargingstations.exceptions.ChargingStationIdAlreadyExistsException;
import de.emphasize.chargingstations.exceptions.InvalidRequestException;
import de.emphasize.chargingstations.model.ErrorDto;

public class GlobalExceptionHandlerTest {

	private GlobalExceptionHandler unit = new GlobalExceptionHandler();

	@Test
	public void whenProcessAnyException() {
		Exception ex = new Exception("TestException");
		ErrorDto actual = unit.handleException(ex);
		assertThat(actual.getCode()).isEqualTo("0001_UnexpectedError");
		assertThat(actual.getMessage()).isEqualTo("TestException");
	}

	@Test
	public void whenProcessChargingStationByIdNotFoundException() {
		UUID random = UUID.randomUUID();
		ChargingStationByIdNotFoundException ex = new ChargingStationByIdNotFoundException(random);
		ErrorDto actual = unit.handleException(ex);
		assertThat(actual.getCode()).isEqualTo("0002_ChargingStationByIdNotFoundException");
		Map<String, String> expected = new HashMap<>();
		expected.put("id", random.toString());
		assertThat(actual.getData()).isEqualTo(expected);
	}

	@Test
	public void whenProcessChargingStationIdAlreadyExistsException() {
		UUID id = UUID.randomUUID();
		ChargingStationIdAlreadyExistsException ex = new ChargingStationIdAlreadyExistsException(id);
		ErrorDto actual = unit.handleException(ex);
		assertThat(actual.getCode()).isEqualTo("0003_ChargingStationIdAlreadyExistsException");
		Map<String, String> expected = new HashMap<>();
		expected.put("id", id.toString());
		assertThat(actual.getData()).isEqualTo(expected);
	}

	@Test
	public void whenProcessInvalidRequestException() {
		InvalidRequestException ex = new InvalidRequestException("invalid request");
		ErrorDto actual = unit.handleException(ex);
		assertThat(actual.getCode()).isEqualTo("0004_InvalidRequestException");
	}

	@Test
	public void whenProcessHttpMessageNotReadableException() {
		HttpMessageNotReadableException ex = new HttpMessageNotReadableException("message not readable");
		ErrorDto actual = unit.handleException(ex);
		assertThat(actual.getCode()).isEqualTo("0005_HttpMessageNotReadableException");
	}
}
