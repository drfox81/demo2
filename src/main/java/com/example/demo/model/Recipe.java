package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Recipe {
    private String nameRecipe;
    private int cookingTime;
    private List<Ingredients> ingredients = new ArrayList<>();
    private List<String> step = new ArrayList<>();
}
