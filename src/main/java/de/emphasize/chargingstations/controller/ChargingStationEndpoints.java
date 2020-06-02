package de.emphasize.chargingstations.controller;

import java.util.Set;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.emphasize.chargingstations.model.ChargingStation;
import de.emphasize.chargingstations.model.ErrorDto;
import de.emphasize.chargingstations.services.ChargingStationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "charging-stations")
@RequestMapping(path = "/v1.0/charging-stations")
@RestController
public class ChargingStationEndpoints {

	private final ChargingStationService chargingStationService;

	public ChargingStationEndpoints(ChargingStationService chargingStationService) {
		this.chargingStationService = chargingStationService;
	}

	@PostMapping()
	@ApiOperation(value = "add a new charging station", notes = "the id must be uniquely new.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Returns data, if all went well.", response = ChargingStation.class),
	    @ApiResponse(code = 406, message = "If there is already a charging station with the given id.", response = ErrorDto.class),
	    @ApiResponse(code = 500, message = "Unexpected error.", response = ErrorDto.class) })
	public ResponseEntity<Void> postChargingStation(@RequestBody ChargingStation chargingStation) {
		chargingStationService.addChargingStation(chargingStation);
		return ResponseEntity.ok(null);
	}

	@GetMapping(value = "/{id}")
	@ApiOperation(value = "get a charging station", notes = "If a charging station with the given id exists, it is returned.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Returns data, if all went well.", response = ChargingStation.class),
	    @ApiResponse(code = 404, message = "If there is no charging station with the given id.", response = ErrorDto.class),
	    @ApiResponse(code = 500, message = "Unexpected error.", response = ErrorDto.class) })
	public ResponseEntity<ChargingStation> getChargingStation(@PathVariable(value = "id") UUID id) {
		ChargingStation response = chargingStationService.getChargingStation(id);
		return ResponseEntity.ok(response);
	}

	@GetMapping(value = "/by-postal-code/{postalCode}")
	@ApiOperation(value = "get all charging stations with a postal code", notes = "All charging stations with a postal code equal to the given one are returned.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Returns data, if all went well.", response = ChargingStation.class),
	    @ApiResponse(code = 500, message = "Unexpected error.", response = ErrorDto.class) })
	public ResponseEntity<Set<ChargingStation>> getChargingStations(@PathVariable(value = "postalCode") String postalCode) {
		Set<ChargingStation> response = chargingStationService.getChargingStations(postalCode);
		return ResponseEntity.ok(response);
	}
}
