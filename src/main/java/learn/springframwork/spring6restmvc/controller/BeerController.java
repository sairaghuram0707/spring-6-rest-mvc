package learn.springframwork.spring6restmvc.controller;

import learn.springframwork.spring6restmvc.model.Beer;
import learn.springframwork.spring6restmvc.services.BeerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

// Lombok Logging Purpose
@Slf4j
// Lombok handles adding the controller
@AllArgsConstructor
@Controller
// The return statements will be converted to JSON's
@RestController
public class BeerController {
    private final BeerService beerService;

    @RequestMapping("api/v1/beer")
    List<Beer> listBeers(){

        log.debug("Controller - List all the beers ");

        return beerService.listBeers();
    }

     Beer getBeerById(UUID id){

        log.debug("Controller - get the beer by ID ");

        return beerService.getBeerById(id);
    }
}
