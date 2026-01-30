package com.api.recipe.recipemasterapi.resource;

import com.api.recipe.recipemasterapi.dto.RecipeDto;
import com.api.recipe.recipemasterapi.service.RecipeCommandService;
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
    public ResponseEntity<RecipeDto> createRecipe(@RequestBody @Valid RecipeDto recipeDto) {
        RecipeDto createdRecipe = recipeCommandService.createRecipe(recipeDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRecipe);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecipeDto> updateRecipe(@PathVariable Long id, @RequestBody RecipeDto recipeDto) {
        RecipeDto updatedRecipe = recipeCommandService.updateRecipe(id, recipeDto);
        return ResponseEntity.ok(updatedRecipe);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable Long id) {
        recipeCommandService.deleteRecipe(id);
        return ResponseEntity.noContent().build();
    }
}
