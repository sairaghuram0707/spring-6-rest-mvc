package learn.springframwork.spring6restmvc.repositories;

import learn.springframwork.spring6restmvc.entities.Beer;
import learn.springframwork.spring6restmvc.model.BeerStyle;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class BeerRepositoryTest {

    @Autowired
    BeerRepository beerRepository;

    @Test
    void testSaveBeer() {
        Beer savedBeer = beerRepository.save(Beer.builder()
                .beerName("Test Beer")
                .upc("234234")
                .beerStyle(BeerStyle.PALE_ALE)
                .price(new BigDecimal("14.57"))
                .build());

        // Triggers Hibernate to add to database
        // Without this the test doesn't check validation of Entities
        beerRepository.flush();

        assertThat(savedBeer).isNotNull();
        assertThat(savedBeer.getId()).isNotNull();
        assertThat(savedBeer.getBeerName()).isEqualTo("Test Beer");
    }
}