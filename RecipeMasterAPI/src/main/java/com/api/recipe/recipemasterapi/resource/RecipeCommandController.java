package com.api.recipe.recipemasterapi.resource;

import com.api.recipe.recipemasterapi.dto.RecipeDto;
import com.api.recipe.recipemasterapi.service.RecipeCommandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/recipes")
public class RecipeCommandController {
    private final RecipeCommandService recipeCommandService;

    @Autowired
    public RecipeCommandController(RecipeCommandService commandService) {
        this.recipeCommandService = commandService;
    }

    @PostMapping
    @Operation(
            summary = "Create a new recipe",
            description = "Creates a new recipe with ingredients"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Recipe created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "422", description = "Validation error")
    })
    public ResponseEntity<RecipeDto> createRecipe(@RequestBody @Valid RecipeDto recipeDto) {
        RecipeDto createdRecipe = recipeCommandService.createRecipe(recipeDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRecipe);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update an existing recipe",
            description = "Updates a recipe by ID"
    )
    public ResponseEntity<RecipeDto> updateRecipe(@PathVariable Long id, @RequestBody RecipeDto recipeDto) {
        RecipeDto updatedRecipe = recipeCommandService.updateRecipe(id, recipeDto);
        return ResponseEntity.ok(updatedRecipe);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete an existing recipe",
            description = "Delete an recipe by ID"
    )
    public ResponseEntity<Void> deleteRecipe(@PathVariable Long id) {
        recipeCommandService.deleteRecipe(id);
        return ResponseEntity.noContent().build();
    }
}
