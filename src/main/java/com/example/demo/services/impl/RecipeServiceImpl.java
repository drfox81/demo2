package com.example.demo.services.impl;

import com.example.demo.model.Recipe;
import com.example.demo.services.RecipeService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
        return recipeMap.getOrDefault(recipe,null);
    }

    @Override
    public Recipe editRecipe(String recipeName, Recipe recipeNew) {
        if (recipeMap.containsKey(recipeName)) {
            recipeMap.put(recipeName,recipeNew);
        } else {
            recipeMap.put(recipeName,recipeNew);
        }
        return recipeMap.get(recipeName);
    }

    @Override
    public String getAllRecipe() {
        List<String> list=new ArrayList<>();
        for (Recipe recipe : recipeMap.values()) {
            list.add(recipe.getNameRecipe());
        }
        return list.toString();
    }

    @Override
    public boolean deleteRecipe(@PathVariable String recipeName){
        if (recipeMap.containsKey(recipeName)) {
            recipeMap.remove(recipeName);
            return true;
        }
        return false;
    }

}
