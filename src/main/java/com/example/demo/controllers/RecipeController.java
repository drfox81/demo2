package com.example.demo.controllers;

import com.example.demo.model.Recipe;
import com.example.demo.services.IngredientService;
import com.example.demo.services.RecipeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recipe")
public class RecipeController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;

    public RecipeController(RecipeService recipeService, IngredientService ingredientService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
    }

    @PostMapping("/recipe")
    public ResponseEntity<Recipe> addRec(@RequestBody Recipe recipe) {
        recipeService.addRecipe(recipe);
        for (int i = 0; i < recipe.getIngredients().size(); i++) {
            ingredientService.addIngredient(recipe.getIngredients().get(i));
        }
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

    @PutMapping("/{recipeName}")
    public ResponseEntity<Recipe> editRecipe(@PathVariable String recipeName, @PathVariable Recipe newRecipe) {
        Recipe recipe = recipeService.editRecipe(recipeName,newRecipe);
        if (recipe != null) {
            return ResponseEntity.ok(recipe);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{recipeName}")
    public ResponseEntity<?> deleteRecipe(@PathVariable String recipeName){
        if (recipeService.deleteRecipe(recipeName)) {
            ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/get/all")
    public ResponseEntity<?> getAllRecipe(){
        return ResponseEntity.ok(recipeService.getAllRecipe());
    }
}
