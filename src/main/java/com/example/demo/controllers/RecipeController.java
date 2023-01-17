package com.example.demo.controllers;

import com.example.demo.model.Recipe;
import com.example.demo.services.RecipeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recipe")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping( "/recipe")
    public ResponseEntity<Recipe> addRec(@RequestBody Recipe recipe) {
        recipeService.addRecipe(recipe);
        return ResponseEntity.ok(recipe);
    }

    @GetMapping("/get/{recipeName}")
    public ResponseEntity<Recipe> getRec(@PathVariable String recipeName) {
        Recipe recipe = recipeService.getRecipe(recipeName);
        if (recipe == null) {
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recipe);
    }
}
