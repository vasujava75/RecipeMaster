package com.api.recipe.recipemasterapi.filter;

import com.api.recipe.recipemasterapi.domain.Recipe;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ServingFilterStrategy implements RecipeFilterStrategy {

    @Override
    public List<Recipe> filter(List<Recipe> recipes, Object criteria) {
        if (criteria == null || !(criteria instanceof Integer)) {
            return recipes;
        }

        Integer servings = (Integer) criteria;

        return recipes.stream()
                .filter(recipe -> recipe.getServings() != null && recipe.getServings() >= servings)
                .toList();
    }
}
