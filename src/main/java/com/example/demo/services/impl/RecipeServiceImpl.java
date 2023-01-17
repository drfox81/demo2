package com.example.demo.services.impl;

import com.example.demo.model.Recipe;
import com.example.demo.services.RecipeService;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class RecipeServiceImpl implements RecipeService {

    private static final Map<String, Recipe> recipeMap = new LinkedHashMap<>();

    @Override
    public void addRecipe(Recipe recipe) {
        recipeMap.put(recipe.getNameRecipre(), recipe);
    }

    @Override
    public Recipe getRecipe(String recipe) {
        return recipeMap.getOrDefault(recipe,null);
    }
}
