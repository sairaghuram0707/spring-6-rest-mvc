package learn.springframwork.spring6restmvc.controller;

import learn.springframwork.spring6restmvc.model.Beer;
import learn.springframwork.spring6restmvc.services.BeerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import java.util.UUID;

// Lombok Logging Purpose
@Slf4j
// Lombok handles adding the controller
@AllArgsConstructor
@Controller
public class BeerController {
    private final BeerService beerService;

     Beer getBeerById(UUID id){

        log.debug("Controller - get the beer by ID ");

        return beerService.getBeerById(id);
    }
}
