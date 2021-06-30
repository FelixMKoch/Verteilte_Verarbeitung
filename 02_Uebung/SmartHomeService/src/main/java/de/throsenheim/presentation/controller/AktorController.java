package de.throsenheim.presentation.controller;

import de.throsenheim.domain.models.SensorData;
import de.throsenheim.presentation.dto.AktorDto;
import de.throsenheim.domain.models.Aktor;
import de.throsenheim.application.connections.AktorConnection;
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

@Controller
public class AktorController {

    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private AktorConnection aktorConnection;

    private static final Logger LOGGER = LoggerFactory.getLogger(AktorController.class);


    /**
     * GET-Mapping for an Aktor with a certain ID
     * @param id ID of the Aktor that the request needs
     * @return The Aktor with the certain ID and an HttpStatus
     */
    @GetMapping("/aktors/{id}")
    @ResponseBody
    @ApiOperation(
            value = "Get Aktor data",
            response = Aktor.class,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Get Aktor data was successfully conducted!"),
                    @ApiResponse(code = 401, message = "Get Aktor data request was denied because missing authentication!"),
                    @ApiResponse(code = 403, message = "Get Aktor data request was denied because no permission!"),
                    @ApiResponse(code = 404, message = "No Aktor with this ID was found!")
            }
    )
    public ResponseEntity<Aktor> aktorGet(@PathVariable String id){

        int resultId;

        try {
            resultId = Integer.parseInt(id);
        }
        catch(NumberFormatException e){
            LOGGER.info("An Aktor with this ID doesn't eixst");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Aktor result = aktorConnection.getAktor(resultId);

        if(result == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    /**
     * Get-Mapping for giving out all the Aktors there is in the database
     * @return A list of all the Aktors
     */
    @GetMapping("/aktors")
    @ResponseBody
    @ApiOperation(
            value = "Get aktors data",
            response = Aktor.class,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Get AktorList data was successfully conducted!"),
                    @ApiResponse(code = 401, message = "Get AktorList data request was denied because missing authentication!"),
                    @ApiResponse(code = 403, message = "Get AktorList data request was denied because no permission!"),
                    @ApiResponse(code = 404, message = "No Aktor with this ID was found!")
            }
    )
    public ResponseEntity<List<Aktor>> aktorsGet(){

        return new ResponseEntity<>(aktorConnection.getAktorList(), HttpStatus.OK);

    }

    /**
     * Post-Request to post an Aktor
     * @param aktorDto An Aktor in the RequestBody that is to be posted
     * @return Status if the request was successful
     */
    @PostMapping("/aktors")
    @ResponseBody
    @ApiOperation(
            value = "Save an Aktor",
            response = SensorData.class,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Post Aktor data was successfully conducted!"),
                    @ApiResponse(code = 401, message = "Post Aktor data request was denied because missing authentication!"),
                    @ApiResponse(code = 403, message = "Post Aktor data request was denied because no permission!"),
                    @ApiResponse(code = 404, message = "No Aktor with this ID was found!")
            }
    )
    public ResponseEntity<Aktor> aktorsPost(@RequestBody AktorDto aktorDto){

        Aktor aktor = modelMapper.map(aktorDto, Aktor.class);

        return new ResponseEntity<>(aktorConnection.save(aktor), HttpStatus.CREATED);

    }
}
