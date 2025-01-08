package learn.springframwork.spring6restmvc.controller;

import learn.springframwork.spring6restmvc.model.Beer;
import learn.springframwork.spring6restmvc.services.BeerService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

// Lombok Logging Purpose
@Slf4j
// Lombok handles adding the controller
@RequiredArgsConstructor
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

        // Response headers for POST Request
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location","/api/v1/beer/" + savedBeer.getId().toString());

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PutMapping("/{beerId}")
    public ResponseEntity<Beer> updateById(@PathVariable("beerId") UUID beerId, @RequestBody Beer beer){

        log.debug("Controller - update Beer with ID - " + beer.getId());

        beerService.updateBeerById(beerId,beer);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{beerId}")
    public ResponseEntity<Beer> deleteById(@PathVariable("beerId") UUID beerId){

        log.debug("Controller - Delete Beer with ID - " + beerId);

        beerService.deleteBeerById(beerId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{beerId}")
    public ResponseEntity<Beer> patchBeerById(@PathVariable("beerId") UUID beerId, @RequestBody Beer beer){

        log.debug("Controller - patch Beer with ID - " + beerId);

        beerService.patchBeerById(beerId,beer);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
