package com.example.demo.controllers;

import com.example.demo.model.Ingredients;
import com.example.demo.services.IngredientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/ingr")
@Tag(name = "Ингредиенты")
public class IngredientsController {

    private final IngredientService ingredientService;

    public IngredientsController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping("/get/{id}")
    @Operation(summary = "Получить ингредиент", description = "Получение по id")
    @Parameter(name = "id", example = "1")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ингредиент получен",
                    content = {
                            @Content(mediaType = "application/json",
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = Ingredients.class))
                            )


                    })
    })
    public ResponseEntity<Ingredients> getIngr(@PathVariable int id) {
        Ingredients ingredients = ingredientService.getIngredient(id);
        if (ingredients == null) {
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ingredients);
    }

    @PostMapping("/add")
    @Operation(summary = "Добавить ингредиент")

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ингредиент получен",
                    content = {
                            @Content(mediaType = "application/json",
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = Ingredients.class))
                            )


                    })
    })
    public ResponseEntity<Integer> addIngr(@RequestBody Ingredients ingredients) {
        if (StringUtils.isEmpty(ingredients.getNameIngredients()) || StringUtils.isEmpty(ingredients.getNameIngredients()))
        {
            return ResponseEntity.notFound().build();
        }
        int iD=ingredientService.addIngredient(ingredients);
        return ResponseEntity.ok(iD);
    }

}