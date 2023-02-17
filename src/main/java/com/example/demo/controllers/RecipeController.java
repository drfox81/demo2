package com.example.demo.controllers;

import com.example.demo.model.Ingredients;
import com.example.demo.model.Recipe;
import com.example.demo.services.IngredientService;
import com.example.demo.services.RecipeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Month;


@RestController
@RequestMapping("/recipe")
@Tag(name = "Рецепты", description = "Вносим и рабтаем с рецептами, при создании рецепта формируется коллекция ингредиентов")
public class RecipeController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;

    public RecipeController(RecipeService recipeService, IngredientService ingredientService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
    }


    @PostMapping("/add")
    @Operation(summary = "Добавляем рецепт (по одному)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Внесен",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = Recipe.class))

                            )
                    })
    })
    public ResponseEntity<Recipe> addRec(@RequestBody Recipe recipe) {
        recipeService.addRecipe(recipe);
        for (int i = 0; i < recipe.getIngredients().size(); i++) {
            ingredientService.addIngredient(recipe.getIngredients().get(i));
        }
        return ResponseEntity.ok(recipe);
    }

    @GetMapping("/get/{recipeName}")
    @Operation(summary = "Получаем рецепт полный рецепт по названию рецепта")
    @Parameter(name = "nameRecipre", example = "Сырники из творога (на 2 порции)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Рецепту быть!",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = Recipe.class))

                            )
                    })
    })
    public ResponseEntity<?> getRec(@PathVariable String recipeName) {
        Recipe recipe = recipeService.getRecipe(recipeName);
        if (recipe == null) {
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recipe);
    }


    @PutMapping("/{recipeName}")
    @Operation(summary = "Изменение рецепта", description = "Вносим изменения в рецепт по названию рецепта")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Изменения внесены",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = Recipe.class))

                            )
                    })
    })
    public ResponseEntity<Recipe> editRecipe(@PathVariable String recipeName, @RequestBody Recipe newRecipe) {
        Recipe recipe = recipeService.editRecipe(recipeName, newRecipe);
        if (recipe != null) {
            return ResponseEntity.ok(recipe);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{recipeName}")
    @Operation(summary = "Удаляем рецепт", description = "Удаление по названию рецепта")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Удален",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = Recipe.class))

                            )
                    })
    })
    public ResponseEntity<?> deleteRecipe(@PathVariable String recipeName) {
        if (recipeService.deleteRecipe(recipeName)) {
            ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/get/all")
    @Operation(summary = "Получить всех рецептов", description = "Получение иассива всех рецептовв формате json")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Готово",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = Recipe.class))

                            )
                    })
    })
    public ResponseEntity<?> getAllRecipe() {
        return ResponseEntity.ok(recipeService.getAllRecipe());
    }


    @PostMapping("/get/ingredients")
    @Operation(summary = "Получаем рецепт по ингредиенту")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Рецепту быть!",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = Recipe.class))

                            )
                    })
    })

    public ResponseEntity<Recipe> findRecipeAnyIngr(@RequestBody Ingredients ingredients) {
        if (ingredients != null) {
            return ResponseEntity.ok(recipeService.findRecipe(ingredients));
        }
        return null;
    }

    @GetMapping("/tempAll")
    @Operation(summary = "Получить всех рецептов  тхт формате", description = "Распечатать все рецепты")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Готово",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = Recipe.class))

                            )
                    })
    })
    public ResponseEntity<?> getTempAllRecipe() {
        try {
            Path path = recipeService.creatAllRecipeTxt();
            if (Files.size(path) == 0) {
                return ResponseEntity.noContent().build();
            } else {
                try {
                    InputStreamResource resource = new InputStreamResource(new FileInputStream(path.toFile()));
                    return ResponseEntity.ok()
                            .contentLength(Files.size(path))
                            .contentType(MediaType.TEXT_PLAIN)
                            .header(HttpHeaders.CONTENT_DISPOSITION,
                                    "attachment;filename=\"" + path + "-report.txt\"")
                            .body(resource);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.toString());
        }
    }


}
