package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class Recipe {
    private String nameRecipre;
    private int cookingTime;
    private List<Ingredients> ingredients = new ArrayList<>();
    private List<String> step = new ArrayList<>();
}
