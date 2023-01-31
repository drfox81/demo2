package com.example.demo.services.impl;

import com.example.demo.model.Ingredients;
import com.example.demo.model.Recipe;
import com.example.demo.services.FileRecipeService;
import com.example.demo.services.RecipeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class RecipeServiceImpl implements RecipeService {

    private final FileRecipeService fileRecipeService;
    private final IngredientServiceImpl ingredientService;

    private static Map<String, Recipe> recipeMap = new LinkedHashMap<>();

    public RecipeServiceImpl(FileRecipeService fileRecipeService, IngredientServiceImpl ingredientService) {
        this.fileRecipeService = fileRecipeService;
        this.ingredientService = ingredientService;
    }

    @PostConstruct
    private void init() {
        readRecipeFromFile();
        ingredientService.readIngrFromFile();
    }

    @Override
    public void addRecipe(Recipe recipe) {
        recipeMap.put(recipe.getNameRecipe(), recipe);
        saveToFile();
        ingredientService.saveToIngrFile();
    }

    @Override
    public Recipe getRecipe(String recipe) {
        return recipeMap.getOrDefault(recipe, null);
    }

    @Override
    public Recipe editRecipe(String recipeName, Recipe recipeNew) {
        if (recipeMap.containsKey(recipeName)) {
            recipeMap.put(recipeName, recipeNew);
            saveToFile();
        } else {
            recipeMap.put(recipeName, recipeNew);
            saveToFile();
        }
        saveToFile();
        return recipeMap.get(recipeName);
    }

    @Override
    public String getAllRecipe() {
        List<Recipe> list = new ArrayList<>();
        for (Recipe recipe : recipeMap.values()) {
            list.add(recipe);
        }
        return list.toString();
    }


    @Override
    public boolean deleteRecipe(String recipeName) {
        if (recipeMap.containsKey(recipeName)) {
            recipeMap.remove(recipeName);
            saveToFile();
            return true;
        }
        saveToFile();
        return false;
    }

    @Override
    public Recipe findRecipe(Ingredients ingredients) {
        if (StringUtils.isBlank(ingredients.getNameIngredients()) &
                StringUtils.isEmpty(ingredients.getNameIngredients())) {
            return null;
        }

        for (Recipe recipe : recipeMap.values()) {
            if (recipe.getIngredients().contains(ingredients)) {
                return recipe;
            }
        }
        return null;
    }

    private void saveToFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(recipeMap);
            fileRecipeService.saveToRecipeFile(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void readRecipeFromFile() {
        String json = fileRecipeService.readFromRecipeFile();
        try {
            recipeMap = new ObjectMapper().readValue(json, new TypeReference<LinkedHashMap<String, Recipe>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
