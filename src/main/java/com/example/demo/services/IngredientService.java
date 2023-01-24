package com.example.demo.services;

import com.example.demo.model.Ingredients;

public interface IngredientService {

    void addIngredient(Ingredients ingredient);

    Ingredients getIngredient(int id);

    String getAllIngredients();

    Ingredients editIngredient(int id, Ingredients newIngredients);

    boolean deleteIngredient(int id);
}
