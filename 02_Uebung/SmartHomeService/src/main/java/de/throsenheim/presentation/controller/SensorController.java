package de.throsenheim.presentation.controller;

import de.throsenheim.domain.models.Rule;
import de.throsenheim.domain.models.Sensor;
import de.throsenheim.application.connections.SensorConnection;
import de.throsenheim.presentation.dto.SensorDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller which handles requests of Sensors
 */
@Controller
public class SensorController {

    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private SensorConnection sensorConnection;

    private static final Logger LOGGER = LoggerFactory.getLogger(SensorController.class);


    @GetMapping("/sensors/{id}")
    @ResponseBody
    @ApiOperation(
            value = "Get a sensor",
            response = Sensor.class,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Get Sensor data was successfully conducted!"),
                    @ApiResponse(code = 401, message = "Get Sensor data request was denied because missing authentication!"),
                    @ApiResponse(code = 403, message = "Get Sensor data request was denied because no permission!"),
                    @ApiResponse(code = 404, message = "No Sensor with this ID was found!")
            }
    )
    public ResponseEntity<Sensor> sensorGet(@PathVariable String id){
        int resultId;
        try {
            resultId = Integer.parseInt(id);
        }
        catch (NumberFormatException e){
            LOGGER.info("A rule with the ID does not exits");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Sensor sensor = sensorConnection.getSensorFromId(resultId);

        if(sensor == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(sensor, HttpStatus.OK);

    }

    @GetMapping("/sensors")
    @ResponseBody
    @ApiOperation(
            value = "Get a List of Sensors",
            response = Sensor.class,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Get Sensors data was successfully conducted!"),
                    @ApiResponse(code = 401, message = "Get Sensors data request was denied because missing authentication!"),
                    @ApiResponse(code = 403, message = "Get Sensors data request was denied because no permission!"),
                    @ApiResponse(code = 404, message = "No Sensor with this ID was found!")
            }
    )
    public ResponseEntity<List<Sensor>> sensorsGet(){

        return new ResponseEntity<>(sensorConnection.getAllSensors(), HttpStatus.OK);

    }


    @PutMapping("/sensors/{id}")
    @ResponseBody
    @ApiOperation(
            value = "Put a Sensor",
            response = Sensor.class,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Put Sensors was successfully conducted!"),
                    @ApiResponse(code = 401, message = "Put Sensors request was denied because missing authentication!"),
                    @ApiResponse(code = 403, message = "Put Sensors request was denied because no permission!"),
                    @ApiResponse(code = 404, message = "No Sensor with this ID was found!")
            }
    )
    public ResponseEntity<Sensor> sensorPut(@PathVariable String id, @RequestBody SensorDto sensorDto){
        Sensor sensor = modelMapper.map(sensorDto, Sensor.class);
        Integer resultId = 0;
        try {
            resultId = Integer.parseInt(id);
        }
        catch(NumberFormatException e){
            LOGGER.info("Couldn't parse the ID ");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(sensorConnection.sensorPut(sensor, resultId), HttpStatus.CREATED);
    }


    @PostMapping("/sensors")
    @ResponseBody
    @ApiOperation(
            value = "Post a Sensor",
            response = Sensor.class,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Post Sensors was successfully conducted!"),
                    @ApiResponse(code = 401, message = "Post Sensors request was denied because missing authentication!"),
                    @ApiResponse(code = 403, message = "Post Sensors request was denied because no permission!"),
                    @ApiResponse(code = 404, message = "No Sensor with this ID was found!")
            }
    )
    public ResponseEntity<Sensor> sensorPost(@RequestBody SensorDto sensordto){
        Sensor sensor = modelMapper.map(sensordto, Sensor.class);

        Sensor sensornew = sensorConnection.addSensor(sensor);

        return new ResponseEntity<>(sensornew, HttpStatus.CREATED);

    }


    @DeleteMapping("/sensors/{id}")
    @ResponseBody
    @ApiOperation(
            value = "Delete a Sensor",
            response = Sensor.class,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Delete Sensors was successfully conducted!"),
                    @ApiResponse(code = 401, message = "Delete Sensors request was denied because missing authentication!"),
                    @ApiResponse(code = 403, message = "Delete Sensors request was denied because no permission!"),
                    @ApiResponse(code = 404, message = "No Sensor with this ID was found!")
            }
    )
    public ResponseEntity<Sensor> sensorDelete(@PathVariable String id){
        int resultId;
        try {
            resultId = Integer.parseInt(id);
        }
        catch (NumberFormatException e){
            LOGGER.info("A rule with the ID does not exits");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Sensor sensor = sensorConnection.getSensorFromId(resultId);

        sensorConnection.deleteSensor(resultId);

        return new ResponseEntity<>(sensor, HttpStatus.OK);
    }
}
