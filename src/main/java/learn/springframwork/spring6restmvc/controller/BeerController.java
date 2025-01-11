package learn.springframwork.spring6restmvc.controller;

import learn.springframwork.spring6restmvc.model.BeerDTO;
import learn.springframwork.spring6restmvc.services.BeerService;
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
//@RequestMapping("/api/v1/beer")
public class BeerController {

    public static final String BEER_PATH = "/api/v1/beer";
    public static final String BEER_PATH_WITH_ID = BEER_PATH + "/{beerId}";

    private final BeerService beerService;

    @GetMapping(BEER_PATH)
    public List<BeerDTO> listBeers(){

        log.debug("Controller - List all the beers");

        return beerService.listBeers();
    }

    @GetMapping(BEER_PATH_WITH_ID)
     public BeerDTO getBeerById(@PathVariable("beerId") UUID beerId){

        log.debug("Controller - get the beer by ID - 56688");

        return beerService.getBeerById(beerId).orElseThrow(NotFoundException::new);
    }

    @PostMapping(BEER_PATH)
    public ResponseEntity<BeerDTO> addBeer(@RequestBody BeerDTO beer){

        log.debug("Controller - add Beer with ID - " + beer.getId());

        BeerDTO savedBeer = beerService.addBeer(beer);

        // Response headers for POST Request
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location",BEER_PATH + "/" + savedBeer.getId().toString());

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PutMapping(BEER_PATH_WITH_ID)
    public ResponseEntity<BeerDTO> updateById(@PathVariable("beerId") UUID beerId, @RequestBody BeerDTO beer){

        log.debug("Controller - update Beer with ID - " + beer.getId());

        beerService.updateBeerById(beerId,beer);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(BEER_PATH_WITH_ID)
    public ResponseEntity<BeerDTO> deleteById(@PathVariable("beerId") UUID beerId){

        log.debug("Controller - Delete Beer with ID - " + beerId);

        beerService.deleteBeerById(beerId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(BEER_PATH_WITH_ID)
    public ResponseEntity<BeerDTO> patchBeerById(@PathVariable("beerId") UUID beerId, @RequestBody BeerDTO beer){

        log.debug("Controller - patch Beer with ID - " + beerId);

        beerService.patchBeerById(beerId,beer);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
