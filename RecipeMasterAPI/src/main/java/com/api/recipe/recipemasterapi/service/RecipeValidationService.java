package com.api.recipe.recipemasterapi.service;

import com.api.recipe.recipemasterapi.domain.Ingredient;
import com.api.recipe.recipemasterapi.domain.Recipe;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeValidationService {

    public void validateRecipe(Recipe recipe) {
        if (recipe == null) {
            throw new IllegalArgumentException("Recipe cannot be null");
        }
        if (recipe.getName() == null || recipe.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Recipe name is required");
        }
        if (recipe.getServings() != null && recipe.getServings() <= 0) {
            throw new IllegalArgumentException("Servings must be positive");
        }
        if (recipe.getPrepTimeMinutes() != null && recipe.getPrepTimeMinutes() < 0) {
            throw new IllegalArgumentException("Preparation time cannot be negative");
        }
        if (recipe.getCookTimeMinutes() != null && recipe.getCookTimeMinutes() < 0) {
            throw new IllegalArgumentException("Cook time cannot be negative");
        }
        if (recipe.getIngredients() != null) {
            validateIngredients(recipe.getIngredients());
        }
    }

    public void validateIngredients(List<Ingredient> ingredients) {
        if (ingredients == null || ingredients.isEmpty()) {
            throw new IllegalArgumentException("Recipe must have at least one ingredient");
        }
        for (Ingredient ingredient : ingredients) {
            if (ingredient.getName() == null || ingredient.getName().trim().isEmpty()) {
                throw new IllegalArgumentException("Ingredient name is required");
            }
            if (ingredient.getQuantity() == null || ingredient.getQuantity() <= 0) {
                throw new IllegalArgumentException("Ingredient quantity must be positive");
            }
        }
    }
}
