package de.throsenheim.presentation.controller;

import de.throsenheim.application.connections.SensorDataConnection;
import de.throsenheim.domain.models.SensorData;
import de.throsenheim.presentation.dto.SensorDataDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
public class SensorDataController {

    @Autowired
    private SensorDataConnection sensorDataConnection;

    private static final Logger LOGGER = LoggerFactory.getLogger(SensorDataController.class);

    @PostMapping("/sensors/{id}")
    @ResponseBody
    @ApiOperation(
            value = "Add new sensor data",
            response = SensorData.class,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Add sensor data was successfully conducted!"),
                    @ApiResponse(code = 401, message = "Add sensor data request was denied because missing authentication!"),
                    @ApiResponse(code = 403, message = "Add sensor data request was denied because no permission!"),
                    @ApiResponse(code = 404, message = "No sensor with this ID was found!")
            }
    )
    public ResponseEntity<SensorData> sensorIdPost(@PathVariable (required = true) String id,
                                                   @RequestBody SensorDataDto sensorDataDto){
        int resultId;

        /*
        Manual mapping from millis to Date
         */
        SensorData sensorData = new SensorData();
        sensorData.setCurrentValue(sensorDataDto.getCurrentValue());
        sensorData.setTemperatureUnit(sensorDataDto.getTemperatureUnit());
        sensorData.setTimestamp(new Date(sensorDataDto.getCurrentMilis()));

        try {
            resultId = Integer.parseInt(id);
        }
        catch(NumberFormatException e){
            LOGGER.info("Couldn't parse the String ");

            return new ResponseEntity<>(sensorData, HttpStatus.BAD_REQUEST);
        }

        sensorData.setSensorid(resultId);

        SensorData sensorDataNew = sensorDataConnection.save(sensorData);

        return new ResponseEntity<>(sensorDataNew, HttpStatus.CREATED);
    }
}
