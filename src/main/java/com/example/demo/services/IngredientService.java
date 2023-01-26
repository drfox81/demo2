package com.example.demo.services;

import com.example.demo.model.Ingredients;

public interface IngredientService {

    int addIngredient(Ingredients ingredient);

    Ingredients getIngredient(int id);

}
