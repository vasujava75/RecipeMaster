package com.api.recipe.recipemasterapi.filter;

import com.api.recipe.recipemasterapi.domain.Recipe;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InstructionTextFilterStrategy implements RecipeFilterStrategy {

    @Override
    public List<Recipe> filter(List<Recipe> recipes, Object criteria) {
        if (criteria == null || !(criteria instanceof String)) {
            return recipes;
        }

        String searchText = ((String) criteria).toLowerCase().trim();

        if (searchText.isEmpty()) {
            return recipes;
        }

        return recipes.stream()
                .filter(recipe -> recipe.getInstructions() != null &&
                        recipe.getInstructions().toLowerCase().contains(searchText))
                .toList();
    }
}
