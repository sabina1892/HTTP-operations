package org.example.spring6restmvc.controller;

import org.example.spring6restmvc.model.Food;
import org.example.spring6restmvc.service.FoodService;
import org.example.spring6restmvc.service.FoodServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@WebMvcTest
class FoodControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    FoodService foodService;
    FoodServiceImpl foodServiceImpl = new FoodServiceImpl();

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
}