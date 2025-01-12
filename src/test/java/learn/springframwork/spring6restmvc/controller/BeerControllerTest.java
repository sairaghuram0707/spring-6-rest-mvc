package learn.springframwork.spring6restmvc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import learn.springframwork.spring6restmvc.model.BeerDTO;
import learn.springframwork.spring6restmvc.services.BeerService;
import learn.springframwork.spring6restmvc.services.BeerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// Kind of Unit test(Only initializing a test splice)
@WebMvcTest(BeerController.class)
class BeerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    BeerService beerService;

    @Captor
    ArgumentCaptor<BeerDTO> beerArgumentCaptor;

    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;

    BeerServiceImpl beerServiceImpl;

    @BeforeEach
    void setUp() {
        beerServiceImpl = new BeerServiceImpl();
    }

    @Test
    void getBeerById() throws Exception {

        BeerDTO testBeer = beerServiceImpl.listBeers().get(0);

        // Stub Declared here(Function Call and Return Value);
        given(beerService.getBeerById(testBeer.getId())).willReturn(Optional.of(testBeer));

        mockMvc.perform(get(BeerController.BEER_PATH_WITH_ID,testBeer.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                // Checking using Jayway JsonPath for Beer fields
                .andExpect(jsonPath("$.id", is(testBeer.getId().toString())))
                .andExpect(jsonPath("$.beerName", is(testBeer.getBeerName())));
    }

    @Test
    void listBeers() throws Exception {

        List<BeerDTO> testBeerList = beerServiceImpl.listBeers();

        given(beerService.listBeers()).willReturn(beerServiceImpl.listBeers());

        mockMvc.perform(get(BeerController.BEER_PATH)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()",is(testBeerList.size())));
    }

    @Test
    void testAddBeer() throws Exception {

        // Sending Object in request
        BeerDTO beer = beerServiceImpl.listBeers().get(0);

        // Similar to what the service returns to thr controller
        given(beerService.addBeer(any(BeerDTO.class))).willReturn(beerServiceImpl.listBeers().get(1));

        mockMvc.perform(post(BeerController.BEER_PATH)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(beer))
                )
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void testUpdateBeer() throws Exception {
        BeerDTO updatedBeer = beerServiceImpl.listBeers().get(0);

        given(beerService.updateBeerById(updatedBeer.getId(),updatedBeer)).willReturn(Optional.of(updatedBeer));

        mockMvc.perform(put(BeerController.BEER_PATH_WITH_ID,updatedBeer.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedBeer)))
                .andExpect(status().isNoContent());

        // Beer Argument Captor already declared above.
        // ArgumentCaptor<Beer> beerArgumentCaptor = ArgumentCaptor.forClass(Beer.class);
        verify(beerService).updateBeerById(any(UUID.class),beerArgumentCaptor.capture());

        assertThat(updatedBeer.getBeerName()).isEqualTo(beerArgumentCaptor.getValue().getBeerName());
        assertThat(updatedBeer.getUpc()).isEqualTo(beerArgumentCaptor.getValue().getUpc());
    }

    @Test
    void testDeleteBeer() throws Exception {
        BeerDTO testBeer = beerServiceImpl.listBeers().get(0);

        given(beerService.deleteBeerById(testBeer.getId())).willReturn(true);

        mockMvc.perform(delete(BeerController.BEER_PATH_WITH_ID,testBeer.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        /* Spy's Mock for the argument being passed for the call
           ArgumentCaptor<UUID> uuidArgumentCaptor = ArgumentCaptor.forClass(UUID.class); */
        verify(beerService).deleteBeerById(uuidArgumentCaptor.capture());

        assertThat(testBeer.getId()).isEqualTo(uuidArgumentCaptor.getValue());
    }

    @Test
    void testPatchBeer() throws Exception {
        BeerDTO beer = beerServiceImpl.listBeers().get(0);

        Map<String,Object> beerMap = new HashMap<>();
        beerMap.put("beerName","Jason's Special");

        mockMvc.perform(patch(BeerController.BEER_PATH_WITH_ID, beer.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(beerMap)))
                .andExpect(status().isNoContent());

        verify(beerService).patchBeerById(uuidArgumentCaptor.capture(),beerArgumentCaptor.capture());

        assertThat(uuidArgumentCaptor.getValue()).isEqualTo(beer.getId());
        assertThat(beerArgumentCaptor.getValue().getBeerName()).isEqualTo(beerMap.get("beerName"));
    }

    @Test
    void getBeerByIdNotFound() throws Exception {
        given(beerService.getBeerById(any(UUID.class))).willReturn(Optional.empty());

        mockMvc.perform(get(BeerController.BEER_PATH_WITH_ID,UUID.randomUUID().toString()))
                .andExpect(status().isNotFound());
    }

    @Test
    void testcreateNewNullBeer() throws Exception {

        BeerDTO testBeer = BeerDTO.builder().build();

        given(beerService.addBeer(any(BeerDTO.class))).willReturn(beerServiceImpl.listBeers().get(0));

        mockMvc.perform(post(BeerController.BEER_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testBeer)))
                .andExpect(status().isBadRequest());
    }
}