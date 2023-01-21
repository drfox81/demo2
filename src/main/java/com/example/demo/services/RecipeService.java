package com.example.demo.services;

import com.example.demo.model.Recipe;
import org.springframework.web.bind.annotation.PathVariable;

public interface RecipeService {

    void addRecipe(Recipe recipe);
    Recipe getRecipe(String recipe);

    Recipe editRecipe(String recipeName, Recipe recipeNew);

    String getAllRecipe();

    boolean deleteRecipe(@PathVariable String recipeName);
}
