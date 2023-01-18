package com.example.demo.controllers;

import com.example.demo.model.Ingredients;
import com.example.demo.services.IngredientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/ingr")
public class IngredientsController {

    private final IngredientService ingredientService;

    public IngredientsController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Ingredients> getIngr(@PathVariable int id) {
        Ingredients ingredients=ingredientService.getIngredient(id);
        if (ingredients==null){
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ingredients);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addIngr(@RequestBody Ingredients ingredients) {
        ingredientService.addIngredient(ingredients);
        return ResponseEntity.ok(ingredients.getNameIngredients()+"-продукт добавлен");
    }

}