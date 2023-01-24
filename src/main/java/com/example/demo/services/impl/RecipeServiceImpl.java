package com.example.demo.services.impl;

import com.example.demo.model.Ingredients;
import com.example.demo.model.Recipe;
import com.example.demo.services.RecipeService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RecipeServiceImpl implements RecipeService {

    private static final Map<String, Recipe> recipeMap = new LinkedHashMap<>();

    @Override
    public void addRecipe(Recipe recipe) {
        String name = recipe.getNameRecipe();
        recipeMap.put(name, recipe);
    }

    @Override
    public Recipe getRecipe(String recipe) {
        return recipeMap.getOrDefault(recipe, null);
    }

    @Override
    public Recipe editRecipe(String recipeName, Recipe recipeNew) {
        if (recipeMap.containsKey(recipeName)) {
            recipeMap.put(recipeName, recipeNew);
        } else {
            recipeMap.put(recipeName, recipeNew);
        }
        return recipeMap.get(recipeName);
    }

    @Override
    public String getAllRecipe() {
        List<String> list = new ArrayList<>();
        for (Recipe recipe : recipeMap.values()) {
            list.add(recipe.getNameRecipe());
        }
        return list.toString();
    }


    @Override
    public boolean deleteRecipe(String recipeName) {
        if (recipeMap.containsKey(recipeName)) {
            recipeMap.remove(recipeName);
            return true;
        }
        return false;
    }

    @Override
    public Recipe findRecipe(Ingredients... ingredients) {
        List<Ingredients> list = new ArrayList<>(Arrays.asList(ingredients));
        Set<Recipe> listFind=new HashSet<>();
        for (Ingredients ingr:list) {
            for (Recipe recipe : recipeMap.values()) {
                if (recipe.getIngredients().contains(ingr)) {
                    listFind.add(recipe);
                }
            }
        }
        return (Recipe) listFind;
    }

}
