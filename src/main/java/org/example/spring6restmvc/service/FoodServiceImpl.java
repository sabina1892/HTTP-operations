package org.example.spring6restmvc.service;

import ch.qos.logback.core.util.StringUtil;
import org.example.spring6restmvc.model.Food;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class FoodServiceImpl implements FoodService {
    Map<UUID, Food> foods;
    public FoodServiceImpl() {
        this.foods = new HashMap<>();
        Food food1 = Food.builder()
                .count(5)
                .id(UUID.randomUUID())
                .country("Azerbaijan")
                .name("Tomato")
                .price(556)
                .build();
        Food food2 = Food.builder()
                .count(51)
                .id(UUID.randomUUID())
                .country("Turkey")
                .name("Potato")
                .price(146)
                .build();
        foods.put(UUID.randomUUID(), food1);
        foods.put(UUID.randomUUID(),food2);
    }
    @Override
    public List<Food> getAllFoods() {
        return new ArrayList<Food>(foods.values());
    }

    @Override
    public Food getFoodById(UUID getFoodId) {
        return foods.get(getFoodId);
    }

    @Override
    public Food saveFood(Food food) {
        Food savedFood = food.builder()
                .name("Reddis")
                .price(15)
                .country("Turkey")
                .id(UUID.randomUUID())
                .count(5)
                .build();
        foods.put(savedFood.getId(), savedFood);
        return savedFood;

    }

    @Override
    public void updatedFoodById(UUID id,Food food) {
        Food existing = foods.get(id);
        existing.setId(food.getId());
        existing.setName(food.getName());
        existing.setCount(food.getCount());
        existing.setPrice(food.getPrice());
        existing.setCountry(food.getCountry());
        foods.put(existing.getId(),existing);
    }

    @Override
    public void deleteFoodById(UUID id) {
        foods.remove(id);
    }

    @Override
    public void patchFoodById(UUID id, Food food) {
        Food existing = foods.get(id);
        if(food.getPrice()!=null) existing.setPrice(food.getPrice());
        if(StringUtils.hasText(food.getName())) existing.setName(food.getName());
        if(food.getCount()!=null) existing.setCount(food.getCount());
        if(StringUtils.hasText(food.getCountry())) existing.setCountry(food.getCountry());
        if(food.getId()!=null) existing.setId(food.getId());
    }


}
