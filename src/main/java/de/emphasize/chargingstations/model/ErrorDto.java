package de.emphasize.chargingstations.model;

import java.util.Map;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
@JsonDeserialize(builder = ErrorDto.ErrorDtoBuilder.class)
public class ErrorDto {
	String message;
	String code;
	Map<String, String> data;

	@JsonPOJOBuilder(withPrefix = "")
	public static final class ErrorDtoBuilder {
	}
}
