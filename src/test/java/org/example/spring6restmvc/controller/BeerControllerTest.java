package org.example.spring6restmvc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.spring6restmvc.model.Beer;
import org.example.spring6restmvc.service.BeerService;
import org.example.spring6restmvc.service.BeerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.UUID;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BeerController.class)
class BeerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    BeerService beerService;
    BeerServiceImpl beerServiceImpl;

    @BeforeEach
    void setUp() {
        beerServiceImpl = new BeerServiceImpl();
    }
    @Test
    void updateBeerTest() throws Exception{
        Beer beerTest = beerServiceImpl.listBeers().get(0);
        mockMvc.perform(put("/api/v1/beer/" + beerTest.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(beerTest)))
                .andExpect(status().isNoContent());
        verify(beerService).updatedBeerId(any(UUID.class),any(Beer.class));
    }
    @Test
    void testListBeers() throws Exception{
        given(beerService.listBeers()).willReturn(beerServiceImpl.listBeers());
        mockMvc.perform(get("/api/v1/beer")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()",is(3)));

    }
    @Test
    void getBeerById() throws Exception {
        Beer testBeer = beerServiceImpl.listBeers().get(0);
        given(beerService.getBeerById(testBeer.getId())).willReturn(testBeer);
        mockMvc.perform(get("/api/v1/beer/" + testBeer.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id",is(testBeer.getId().toString())))
                .andExpect(jsonPath("$.beerName",is(testBeer.getBeerName())));

    }
    @Test
    void createBeer() throws Exception {
        Beer testBeer = beerServiceImpl.listBeers().get(0);
        testBeer.setVersion(null);
        testBeer.setId(null);
        given(beerService.savedBeer(any(Beer.class))).willReturn(beerServiceImpl.listBeers().get(1));
        mockMvc.perform(post("/api/v1/beer")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testBeer)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void deleteBeerTest() throws Exception{
        Beer beer = beerServiceImpl.listBeers().get(0);
        mockMvc.perform(delete("/api/v1/beer/" + beer.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        ArgumentCaptor<UUID> UUIDargumentCaptor = ArgumentCaptor.forClass(UUID.class);
        verify(beerService).deleteById(UUIDargumentCaptor.capture());
        assert(beer.getId().equals(UUIDargumentCaptor.getValue()));
    }

}