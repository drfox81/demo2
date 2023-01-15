package com.example.demo.services.impl;

import com.example.demo.model.Ingredients;
import com.example.demo.services.IngredientService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class IngredientServiceImpl implements IngredientService {

    private static int idIngredient = 0;

    private static Map<Integer, Ingredients> ingredientMap = new HashMap<>();

    public static int getIdIngredient() {
        return idIngredient;
    }

    public static Map<Integer, Ingredients> getIngredientMap() {
        return ingredientMap;
    }

    @Override
    public void addIngredient(Ingredients ingredient) {
        ingredientMap.put(getIdIngredient(), ingredient);
        idIngredient++;
    }

    @Override
    public Ingredients getIngredient(int id) {
        return getIngredientMap().getOrDefault(id,null);
    }
}
