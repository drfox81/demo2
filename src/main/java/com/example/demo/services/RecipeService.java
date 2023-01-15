package com.example.demo.services;

import com.example.demo.model.Recipe;

public interface RecipeService {

    void addRecipe(Recipe recipe);
    Recipe getRecipe(String recipe);

}
