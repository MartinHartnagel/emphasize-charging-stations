package de.emphasize.chargingstations.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import de.emphasize.chargingstations.exceptions.ChargingStationByIdNotFoundException;
import de.emphasize.chargingstations.exceptions.ChargingStationIdAlreadyExistsException;
import de.emphasize.chargingstations.exceptions.InvalidRequestException;
import de.emphasize.chargingstations.model.ErrorDto;

@ControllerAdvice
public class GlobalExceptionHandler {

	private static final String ERROR_CODE_0001 = "0001_UnexpectedError";
	private static final String ERROR_CODE_0002 = "0002_ChargingStationByIdNotFoundException";
	private static final String ERROR_CODE_0003 = "0003_ChargingStationIdAlreadyExistsException";
	private static final String ERROR_CODE_0004 = "0004_InvalidRequestException";
	private static final String ERROR_CODE_0005 = "0005_HttpMessageNotReadableException";

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(value = { Exception.class })
	protected @ResponseBody ErrorDto handleException(Exception ex) {
		return ErrorDto.builder().message(ex.getMessage()).code(ERROR_CODE_0001).build();
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(value = { ChargingStationByIdNotFoundException.class })
	protected @ResponseBody ErrorDto handleException(ChargingStationByIdNotFoundException ex) {
		Map<String, String> data = new HashMap<>();
		data.put("id", ex.getId().toString());

		return ErrorDto.builder().message(ex.getMessage()).code(ERROR_CODE_0002).data(data).build();
	}

	@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
	@ExceptionHandler(value = { ChargingStationIdAlreadyExistsException.class })
	protected @ResponseBody ErrorDto handleException(ChargingStationIdAlreadyExistsException ex) {
		Map<String, String> data = new HashMap<>();
		data.put("id", ex.getId().toString());
		return ErrorDto.builder().message(ex.getMessage()).code(ERROR_CODE_0003).data(data).build();
	}

	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(value = { InvalidRequestException.class })
	protected @ResponseBody ErrorDto handleException(InvalidRequestException ex) {
		return ErrorDto.builder().message(ex.getMessage()).code(ERROR_CODE_0004).build();
	}

	@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
	@ExceptionHandler(value = { HttpMessageNotReadableException.class })
	protected @ResponseBody ErrorDto handleException(HttpMessageNotReadableException ex) {
		return ErrorDto.builder().message(ex.getMessage()).code(ERROR_CODE_0005).build();
	}
}
