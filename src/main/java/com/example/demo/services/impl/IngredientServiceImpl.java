package com.example.demo.services.impl;

import com.example.demo.model.Ingredients;
import com.example.demo.model.Recipe;
import com.example.demo.services.IngredientService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final FileIngrServiceImpl fileIngrService;
    private static int idIngredient = 0;

    private static Map<Integer, Ingredients> ingredientMap = new HashMap<>();

    public IngredientServiceImpl(FileIngrServiceImpl fileIngrService) {
        this.fileIngrService = fileIngrService;
    }

//    private void init() {
//        readIngrFromFile();
//    }


    public static int getIdIngredient() {
        return idIngredient;
    }

    @Override
    public int addIngredient(Ingredients ingredient) {
        int number = idIngredient;
        ingredientMap.put(idIngredient++, ingredient);
        saveToIngrFile();
        return number;
    }

    @Override
    public Ingredients getIngredient(int id) {
        return ingredientMap.getOrDefault(id, null);
    }

    void saveToIngrFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(ingredientMap);
            fileIngrService.saveToIngrFile(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    void readIngrFromFile() {
        String json = fileIngrService.readFromIngrFile();
        try {
            ingredientMap = new ObjectMapper().readValue(json, new TypeReference<HashMap<Integer, Ingredients>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
