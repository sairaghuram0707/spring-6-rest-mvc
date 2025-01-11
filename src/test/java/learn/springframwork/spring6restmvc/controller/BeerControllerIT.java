package learn.springframwork.spring6restmvc.controller;

import jakarta.transaction.Transactional;
import learn.springframwork.spring6restmvc.entities.Beer;
import learn.springframwork.spring6restmvc.model.BeerDTO;
import learn.springframwork.spring6restmvc.repositories.BeerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


// Integration Tests here (Tests controller Interaction with the repository layer)
@SpringBootTest
class BeerControllerIT {
    
    @Autowired
    private BeerController beerController;

    @Autowired
    private BeerRepository beerRepository;


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
    void testGetBeerById() {

        Beer beer = beerRepository.findAll().get(0);

        BeerDTO beerDTO = beerController.getBeerById(beer.getId());

        assertThat(beerDTO).isNotNull();
    }

    @Test
    void testExceptionBeerById() {

        assertThrows(NotFoundException.class,()->{
                beerController.getBeerById(UUID.randomUUID());
        });

    }
}