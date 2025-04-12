package org.example.spring6restmvc.controller;


import lombok.RequiredArgsConstructor;
import org.example.spring6restmvc.model.Food;
import org.example.spring6restmvc.service.FoodService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/food")
public class FoodController {
    private final FoodService foodService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Food> getAllFoods(){
        return foodService.getAllFoods();
    }

    @RequestMapping(value = "{foodId}", method = RequestMethod.GET)
    public Food getFoodById(@PathVariable("foodId") UUID getFoodId){
        return foodService.getFoodById(getFoodId);
    }
    @PostMapping
    public ResponseEntity saveFood(@RequestBody Food food){
        Food saveFood = foodService.saveFood(food);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Location", "/api/v1/food/"+saveFood.getId());
        return new ResponseEntity(HttpStatus.CREATED);
    }
    @PutMapping("{foodId}")
    public ResponseEntity updatedFood(@RequestBody Food food, @PathVariable("foodId") UUID id){
        foodService.updatedFoodById(food,id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
    @DeleteMapping("{foodId}")
    public ResponseEntity deleteFood(@PathVariable("foodId") UUID id){
        foodService.deleteFoodById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
    @PatchMapping("{foodId}")
    public ResponseEntity patchFood(@PathVariable("foodId") UUID id, @RequestBody Food food){
        foodService.patchFoodById(id, food);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
