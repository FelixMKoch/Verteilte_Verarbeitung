package de.throsenheim.presentation.controller;


import de.throsenheim.application.connections.RuleConnection;
import de.throsenheim.domain.models.Rule;
import de.throsenheim.presentation.dto.RuleDto;
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
public class RulesController {

    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private RuleConnection ruleConnection;

    private static final Logger LOGGER = LoggerFactory.getLogger(RulesController.class);

    /**
     * Get-Mapping for retrieving all the Rule-entries
     * @return A List of all the Rules with a Http-Status
     */
    @GetMapping("/rules")
    @ResponseBody
    @ApiOperation(
            value = "Get Rules",
            response = Rule.class,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Get Rule data was successfully conducted!"),
                    @ApiResponse(code = 401, message = "Get Rule data request was denied because missing authentication!"),
                    @ApiResponse(code = 403, message = "Get Rule data request was denied because no permission!"),
                    @ApiResponse(code = 404, message = "No Rule was found!")
            }
    )
    public ResponseEntity<List<Rule>> rulesGet(){

        return new ResponseEntity<>(ruleConnection.getAllRules(), HttpStatus.OK);

    }


    /**
     * Get-Mapping to get a certain Rule
     * @param id ID of the rule that shoudl be retrieved
     * @return The certain RUle with a Http-status
     */
    @GetMapping("/rules/{id}")
    @ResponseBody
    @ApiOperation(
            value = "Ger a rule",
            response = Rule.class,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Get Rule data was successfully conducted!"),
                    @ApiResponse(code = 401, message = "Get Rule data request was denied because missing authentication!"),
                    @ApiResponse(code = 403, message = "Get Rule data request was denied because no permission!"),
                    @ApiResponse(code = 404, message = "No Rule with this ID was found!")
            }
    )
    public ResponseEntity<Rule> ruleGet(@PathVariable String id){
        int resultId;
        try {
            resultId = Integer.parseInt(id);
        }
        catch (NumberFormatException e){
            LOGGER.info("A rule with the ID does not exits");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(ruleConnection.getById(resultId), HttpStatus.OK);

    }


    /**
     * Post-Request for posting a Rule
     * @param ruleDto Transfer-Object for the Rule in the Request-body
     * @return A HTTP-stauts with the RUle that was posted
     */
    @PostMapping("/rules")
    @ResponseBody
    @ApiOperation(
            value = "Post a rule",
            response = Rule.class,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Post Rule data was successfully conducted!"),
                    @ApiResponse(code = 401, message = "Post Rule data request was denied because missing authentication!"),
                    @ApiResponse(code = 403, message = "Post Rule data request was denied because no permission!"),
                    @ApiResponse(code = 404, message = "No Rule with this ID was found!")
            }
    )
    public ResponseEntity<Rule> rulesPost(@RequestBody RuleDto ruleDto){
        Rule rule = modelMapper.map(ruleDto, Rule.class);

        return new ResponseEntity<>(ruleConnection.addRule(rule), HttpStatus.CREATED);
    }
}
