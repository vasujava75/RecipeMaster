package com.api.recipe.recipemasterapi.filter;

import com.api.recipe.recipemasterapi.domain.Recipe;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VegetarianFilterStrategy implements RecipeFilterStrategy {

    @Override
    public List<Recipe> filter(List<Recipe> recipes, Object criteria) {
        if (criteria == null || !(criteria instanceof Boolean)) {
            return recipes;
        }

        Boolean isVegetarian = (Boolean) criteria;

        return recipes.stream()
                .filter(recipe -> recipe.getVegetarian() != null && recipe.getVegetarian().equals(isVegetarian))
                .toList();
    }
}
