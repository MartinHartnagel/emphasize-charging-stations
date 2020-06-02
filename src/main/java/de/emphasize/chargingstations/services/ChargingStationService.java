package de.emphasize.chargingstations.services;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

import de.emphasize.chargingstations.exceptions.ChargingStationByIdNotFoundException;
import de.emphasize.chargingstations.exceptions.ChargingStationIdAlreadyExistsException;
import de.emphasize.chargingstations.model.ChargingStation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ChargingStationService {

	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
	static {
		OBJECT_MAPPER.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		OBJECT_MAPPER.registerModule(new Jdk8Module());
		OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	// TODO: replace this mock with a db-persistent implementation
	private final Map<UUID, ChargingStation> chargingStations = new HashMap<>();

	/**
	 * Stores a charging station.
	 * 
	 * @param chargingStation
	 *          to store.
	 */
	public void addChargingStation(ChargingStation chargingStation) {
		if (chargingStations.containsKey(chargingStation.getId())) {
			throw new ChargingStationIdAlreadyExistsException(chargingStation.getId());
		}
		chargingStations.put(chargingStation.getId(), chargingStation);
	}

	/**
	 * Returns {@link ChargingStation}s with the given postalCode.
	 *
	 * @param postalCode
	 *          the charging station postal code.
	 * @return the charging stations with a postal code equal to the given one.
	 */
	public Set<ChargingStation> getChargingStations(final String postalCode) {
		log.debug("getChargingStations: " + postalCode);
		return chargingStations.values().stream()
		    .filter(chargingStation -> StringUtils.equals(postalCode, chargingStation.getPostalCode())).collect(Collectors.toSet());
	}

	/**
	 * Returns {@link ChargingStation} by its id.
	 *
	 * @param id
	 *          the charging station id.
	 * @return the charging station.
	 */
	public ChargingStation getChargingStation(final UUID id) {
		log.debug("getChargingStation: " + id);
		if (!chargingStations.containsKey(id)) {
			throw new ChargingStationByIdNotFoundException(id);
		}
		return chargingStations.get(id);
	}

	/**
	 * Returns the known {@link ChargingStation}s.
	 *
	 * @return the known charging stations.
	 */
	public Collection<ChargingStation> getChargingStations() {
		log.debug("getChargingStation");
		return chargingStations.values();
	}

}
