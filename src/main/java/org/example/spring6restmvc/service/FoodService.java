package org.example.spring6restmvc.service;

import org.example.spring6restmvc.model.Food;

import java.util.List;
import java.util.UUID;

public interface FoodService {

    List<Food> getAllFoods();

    Food getFoodById(UUID getFoodId);


    Food saveFood(Food food);

    void updatedFoodById(Food food, UUID id);

    void deleteFoodById(UUID id);

    void patchFoodById(UUID id, Food food);
}
