package de.emphasize.chargingstations.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class ResponseWrapper<V> {

	V value;

	@JsonCreator
	public ResponseWrapper(@JsonProperty("value") V value) {
		this.value = value;
	}
}
