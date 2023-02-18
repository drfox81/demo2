package com.example.demo.services;

import com.example.demo.model.Ingredients;
import com.example.demo.model.Recipe;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface RecipeService {

    void addRecipe(Recipe recipe);
    Recipe getRecipe(String recipe);

    Recipe editRecipe(String recipeName, Recipe recipeNew);

    List<Recipe> getAllRecipe();

    boolean deleteRecipe(@PathVariable String recipeName);

    Recipe findRecipe(Ingredients ingredients);

    Path creatAllRecipeTxt() throws IOException;
}
