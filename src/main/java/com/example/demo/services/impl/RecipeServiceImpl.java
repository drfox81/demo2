package com.example.demo.services.impl;

import com.example.demo.model.Ingredients;
import com.example.demo.model.Recipe;
import com.example.demo.services.FileRecipeService;
import com.example.demo.services.RecipeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
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
        try {
            readRecipeFromFile();
            ingredientService.readIngrFromFile();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void addRecipe(Recipe recipe) {
        for (int i = 0; i < recipe.getIngredients().size(); i++) {
            ingredientService.addIngredient(recipe.getIngredients().get(i));
        }
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
        recipeMap.put(recipeName, recipeNew);
        saveToFile();
        return recipeMap.get(recipeName);
    }

    @Override
    public List<Recipe> getAllRecipe() {
        List<Recipe> list = new ArrayList<>();
        for (Recipe recipe : recipeMap.values()) {
            list.add(recipe);
        }
        return list;
    }


    @Override
    public boolean deleteRecipe(String recipeName) {
        if (recipeMap.containsKey(recipeName)) {
            recipeMap.remove(recipeName);
            saveToFile();
            return true;
        }
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

    @Override
    public Path creatAllRecipeTxt() throws IOException {
        Path path = fileRecipeService.creatTempFile("AllRecipe");
        File file = fileRecipeService.getDataRecipeFile();
        for (Recipe value : recipeMap.values()) {
            try (Writer writer = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
                writer.append(value.getNameRecipe() + "\nВремя приготовления -" + value.getCookingTime() + " минут \n\n");
                writer.append("Ингредиенты:"+ "\n");
                for (int i = 0; i < value.getIngredients().size(); i++) {
                    writer.append(value.getIngredients().get(i).getNameIngredients() + " " +
                            value.getIngredients().get(i).getQuantityIngredients() +
                            " " + value.getIngredients().get(i).getMeasureUnit() + "\n");
                }
                writer.append("\nПорядок приготовления:"+ "\n");
                for (int i = 0; i < value.getStep().size(); i++) {
                    writer.append(value.getStep().get(i)+ "\n");
                }
                writer.append("Приятного аппетита!!!" +"\n"+line()+ "\n");

            }

        }
        return path;
    }

    private String line(){
        return "----------------------------------------------------";
    }

}


