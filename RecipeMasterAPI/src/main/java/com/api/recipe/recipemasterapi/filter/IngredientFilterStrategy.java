package com.api.recipe.recipemasterapi.filter;

import com.api.recipe.recipemasterapi.domain.Recipe;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class IngredientFilterStrategy implements RecipeFilterStrategy {

    @Override
    public List<Recipe> filter(List<Recipe> recipes, Object criteria) {
        if (criteria == null || !(criteria instanceof Set)) {
            return recipes;
        }

        @SuppressWarnings("unchecked")
        Set<String> ingredientNames = (Set<String>) criteria;

        if (ingredientNames.isEmpty()) {
            return recipes;
        }

        return recipes.stream()
                .filter(recipe -> containsAnyIngredient(recipe, ingredientNames))
                .toList();
    }

    private boolean containsAnyIngredient(Recipe recipe, Set<String> ingredientNames) {
        if (recipe.getIngredients() == null || recipe.getIngredients().isEmpty()) {
            return false;
        }

        return recipe.getIngredients().stream()
                .anyMatch(ingredient ->
                    ingredientNames.stream()
                        .anyMatch(name ->
                            ingredient.getName().toLowerCase().contains(name.toLowerCase())
                        )
                );
    }
}
