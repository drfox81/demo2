package com.example.demo.services.impl;

import com.example.demo.model.Ingredients;
import com.example.demo.services.IngredientService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        return ingredientMap.getOrDefault(id, null);
    }

    @Override
    public String getAllIngredients() {
        List<String> list=new ArrayList<>();
        for (Ingredients integer : ingredientMap.values()) {
            list.add(integer.getNameIngredients());
        }
        return list.toString();
    }

    @Override
    public Ingredients editIngredient(int id, Ingredients newIngredients) {
        Ingredients ingredients = ingredientMap.get(id);
        if (ingredients != null) {
            return ingredientMap.put(id, newIngredients);
        }
        return ingredientMap.put(id, newIngredients);
    }

    @Override
    public boolean deleteIngredient(int id) {
        Ingredients ingredients = ingredientMap.get(id);
        if (ingredients != null) {
            ingredientMap.remove(id);
            return true;
        }
        return false;
    }
}
