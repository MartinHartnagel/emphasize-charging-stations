package de.emphasize.chargingstations;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.HttpStatusCodeException;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import de.emphasize.chargingstations.model.ChargingStation;
import de.emphasize.chargingstations.model.ErrorDto;
import lombok.Getter;

@Component
@Getter
public class ChargingStationUtils {
	@Autowired
	private TestRestTemplate restTemplate;
	HttpStatusCodeException serverError;
	HttpStatus statusCode;
	String result;
	String errorResult;

	public void postChargingStation(ChargingStation chargingStation) {
		restTemplate.getRestTemplate().setErrorHandler(new DefaultResponseErrorHandler());
		try {
			ResponseEntity<Void> resultEntity = restTemplate.postForEntity("/v1.0/charging-stations/", chargingStation, Void.class);
			result = "";
			statusCode = resultEntity.getStatusCode();
		} catch (HttpStatusCodeException e) {
			serverError = e;
			errorResult = e.getResponseBodyAsString();
			statusCode = e.getStatusCode();
		}
	}

	public void getChargingStation(String id) {
		restTemplate.getRestTemplate().setErrorHandler(new DefaultResponseErrorHandler());
		try {
			ResponseEntity<String> resultEntity = restTemplate.getForEntity("/v1.0/charging-stations/" + id, String.class);
			result = resultEntity.getBody();
			statusCode = resultEntity.getStatusCode();
		} catch (HttpStatusCodeException e) {
			serverError = e;
			errorResult = e.getResponseBodyAsString();
			statusCode = e.getStatusCode();
		}
	}

	public void getChargingStations(String postalCode) {
		restTemplate.getRestTemplate().setErrorHandler(new DefaultResponseErrorHandler());
		try {
			ResponseEntity<String> resultEntity = restTemplate.getForEntity("/v1.0/charging-stations/by-postal-code/" + postalCode,
			    String.class);
			result = resultEntity.getBody();
			statusCode = resultEntity.getStatusCode();
		} catch (HttpStatusCodeException e) {
			serverError = e;
			errorResult = e.getResponseBodyAsString();
			statusCode = e.getStatusCode();
		}
	}

	public ChargingStation readResult() throws IOException {
		return getObjectMapper().readValue(result, ChargingStation.class);
	}

	public ChargingStation[] readResults() throws IOException {
		return getObjectMapper().readValue(result, ChargingStation[].class);
	}

	public ErrorDto readErrorResult() throws IOException {
		return getObjectMapper().readValue(errorResult, ErrorDto.class);
	}

	private static ObjectMapper getObjectMapper() {
		return new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL)
		    .configure(SerializationFeature.INDENT_OUTPUT, true).configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}
}
