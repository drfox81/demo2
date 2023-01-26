package com.example.demo.services.impl;

import com.example.demo.model.Ingredients;
import com.example.demo.model.Recipe;
import com.example.demo.services.RecipeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RecipeServiceImpl implements RecipeService {

    private static final Map<String, Recipe> recipeMap = new LinkedHashMap<>();

    @Override
    public void addRecipe(Recipe recipe) {
        recipeMap.put(recipe.getNameRecipe(), recipe);
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
            return true;
        }
        return false;
    }

    @Override
    public Recipe findRecipe(Ingredients ingredients) {
//        List<Ingredients> list = new ArrayList<>(Arrays.asList(ingredients));
//        Set<Recipe> listFind=new HashSet<>();
        //for (Ingredients ingr:list) {
        if (StringUtils.isBlank(ingredients.getNameIngredients())&
                StringUtils.isEmpty(ingredients.getNameIngredients())) {
            return null;
        }

            for (Recipe recipe : recipeMap.values()) {
                if (recipe.getIngredients().contains(ingredients)) {
                    return recipe;
                }
            }
        //}
        return null;
        //return (Recipe) listFind;
    }

}
