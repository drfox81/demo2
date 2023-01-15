package com.example.demo.services;

import com.example.demo.model.Ingredients;

public interface IngredientService {

    void addIngredient(Ingredients ingredient);

    Ingredients getIngredient(int id);

}
