package com.api.recipe.recipemasterapi.filter;

import com.api.recipe.recipemasterapi.domain.Recipe;

import java.util.List;

public interface RecipeFilterStrategy {
    List<Recipe> filter(List<Recipe> recipes, Object criteria);
}
