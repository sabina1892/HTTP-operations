package org.example.spring6restmvc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.spring6restmvc.model.Food;
import org.example.spring6restmvc.service.FoodService;
import org.example.spring6restmvc.service.FoodServiceImpl;
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

@WebMvcTest(FoodController.class)
class FoodControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    FoodService foodService;
    FoodServiceImpl foodServiceImpl;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp(){
        foodServiceImpl = new FoodServiceImpl();
    }

    @Test
    void getListFood() throws Exception{
        given(foodService.getAllFoods()).willReturn(foodServiceImpl.getAllFoods());
        mockMvc.perform(get("/api/v1/food")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()",is(3)));
    }
    @Test
    void getFoodById() throws Exception{
        Food food = foodService.getAllFoods().get(0);
        given(foodService.getFoodById(food.getId())).willReturn(food);
        mockMvc.perform(get("/api/v1/food/"+ food.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id",is(food.getId().toString())));
    }
    @Test
    void createFood() throws Exception{
        Food food = foodServiceImpl.getAllFoods().get(0);
        food.setId(null);
        food.setCountry(null);
        given(foodService.saveFood(any(Food.class))).willReturn(foodServiceImpl.getAllFoods().get(1));
        mockMvc.perform(post("/api/v1/food")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(food)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));

    }
    @Test
    void updateFoodTest() throws Exception{
        Food food = foodServiceImpl.getAllFoods().get(0);
        mockMvc.perform(put("/api/v1/food/"+food.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(food)))
                .andExpect(status().isNoContent());
        verify(foodService).updatedFoodById(any(UUID.class), any(Food.class));
    }

    @Test
    void deleteFoodTest() throws Exception{
        Food food = foodServiceImpl.getAllFoods().get(0);
        mockMvc.perform(delete("/api/v1/food/" + food.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        ArgumentCaptor<UUID> argumentCaptor = ArgumentCaptor.forClass(UUID.class);
        verify(foodService).deleteFoodById(argumentCaptor.capture());
        assert(food.getId().equals(argumentCaptor.getValue()));
    }
}