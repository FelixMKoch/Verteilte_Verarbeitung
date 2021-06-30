package de.throsenheim.presentation;

import de.throsenheim.Shutter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ShutterController {

    /**
     * Post-Mapping for the status of the Shutter
     * @param status can receive a status for this Shutter
     */
    @PostMapping("/api/v1/shutter")
    public void shutterControl(@RequestParam (required = false) String status){

        if(status == null) return;

        Shutter.setStatus(status.equalsIgnoreCase("open"));

    }
}
