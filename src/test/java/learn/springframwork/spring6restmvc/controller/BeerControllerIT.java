package learn.springframwork.spring6restmvc.controller;

import jakarta.transaction.Transactional;
import learn.springframwork.spring6restmvc.entities.Beer;
import learn.springframwork.spring6restmvc.mappers.BeerMapper;
import learn.springframwork.spring6restmvc.model.BeerDTO;
import learn.springframwork.spring6restmvc.model.CustomerDTO;
import learn.springframwork.spring6restmvc.repositories.BeerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;


// Integration Tests here (Tests controller Interaction with the repository layer)
@SpringBootTest
class BeerControllerIT {
    
    @Autowired
    private BeerController beerController;

    @Autowired
    private BeerRepository beerRepository;

    @Autowired
    private BeerMapper beerMapper;


    @Test
    void testListBeers() {

        List<BeerDTO> beerList = beerController.listBeers();

        assertThat(beerList).isNotNull();
        assertThat(beerList.size()).isEqualTo(3);

    }

    // As we are altering the DB here that is being persisted for other testcases.
    // We are only adding entries at the very first initialization using Bootstrap class.
    @Rollback
    @Transactional
    // Rolling back the thing
    @Test
    void testEmptyList(){

        beerRepository.deleteAll();

        List<BeerDTO> beerList = beerController.listBeers();

        assertThat(beerList.size()).isEqualTo(0);
    }

    @Test
    void testExceptionBeerById() {

        assertThrows(NotFoundException.class,()->{
            beerController.getBeerById(UUID.randomUUID());
        });

    }

    @Test
    void testGetBeerById() {

        Beer beer = beerRepository.findAll().get(0);

        BeerDTO beerDTO = beerController.getBeerById(beer.getId());

        assertThat(beerDTO).isNotNull();
    }

    @Transactional
    @Rollback
    @Test
    void testAddNewBeer() {

        BeerDTO beer = BeerDTO.builder()
                .beerName("test Beer")
                .build();

        ResponseEntity<BeerDTO> responseEntity = beerController.addBeer(beer);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        String[] locationUuid = responseEntity.getHeaders().getLocation().getPath().split("/" );
        UUID uuid = UUID.fromString(locationUuid[locationUuid.length-1]);

        BeerDTO savedBeer= beerController.getBeerById(uuid);
        assertThat(savedBeer.getBeerName()).isEqualTo(beer.getBeerName());
    }

    @Test
    void updateBeerByIdFailed() {
        assertThrows(NotFoundException.class,()->{
            beerController.updateById(UUID.randomUUID(),any());
        });
    }

    @Test
    @Transactional
    @Rollback
    void updateBeerById() {
        final String beerName = "Test Bear Name";
        Beer beer = beerRepository.findAll().get(0);
        beer.setBeerName(beerName);
        BeerDTO beerDTO = beerMapper.beerToBeerDTO(beer);
        beerDTO.setId(null);
        beerDTO.setVersion(null);

        ResponseEntity responseEntity = beerController.updateById(beer.getId(), beerDTO);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(beerRepository.findById(beer.getId()).get().getBeerName()).isEqualTo(beerName);

        Beer savedBear = beerRepository.findById(beer.getId()).get();
        assertThat(savedBear.getBeerName()).isEqualTo(beerName);
    }

    @Test
    void deleteBeerByIdFailed() {
        assertThrows(NotFoundException.class,()->{
            beerController.deleteById(UUID.randomUUID());
        });
    }

    @Test
    @Transactional
    @Rollback
    void deleteBeerById() {
        Beer beer = beerRepository.findAll().get(0);
        ResponseEntity responseEntity = beerController.deleteById(beer.getId());

        assertThat(beerRepository.findById(beer.getId())).isEmpty();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}