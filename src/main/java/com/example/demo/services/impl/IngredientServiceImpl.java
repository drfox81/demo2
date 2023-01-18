package com.example.demo.services.impl;

import com.example.demo.model.Ingredients;
import com.example.demo.services.IngredientService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class IngredientServiceImpl implements IngredientService {

    private static int idIngredient = 0;

    private static final Map<Integer, Ingredients> ingredientMap = new HashMap<>();

    public static int getIdIngredient() {
        return idIngredient;
    }

    @Override
    public void addIngredient(Ingredients ingredient) {
        ingredientMap.put(idIngredient++, ingredient);
    }

    @Override
    public Ingredients getIngredient(int id) {
        return ingredientMap.getOrDefault(id,null);
    }
}
