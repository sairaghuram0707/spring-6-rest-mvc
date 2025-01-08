package learn.springframwork.spring6restmvc.controller;

import learn.springframwork.spring6restmvc.model.Beer;
import learn.springframwork.spring6restmvc.services.BeerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

// Lombok Logging Purpose
@Slf4j
// Lombok handles adding the controller
@AllArgsConstructor
// The return statements will be converted to JSON's (POJO's to JSON - View)
@RestController
// Base API Route
@RequestMapping("/api/v1/beer")
public class BeerController {
    private final BeerService beerService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Beer> listBeers(){

        log.debug("Controller - List all the beers");

        return beerService.listBeers();
    }

    @RequestMapping(value = "/{beerId}", method = RequestMethod.GET)
     public Beer getBeerById(@PathVariable("beerId") UUID beerId){

        log.debug("Controller - get the beer by ID - 56688");

        return beerService.getBeerById(beerId);
    }

    @PostMapping
    public ResponseEntity<Beer> addBeer(@RequestBody Beer beer){

        log.debug("Controller - add Beer with ID - " + beer.getId());

        Beer savedBeer = beerService.addBeer(beer);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
