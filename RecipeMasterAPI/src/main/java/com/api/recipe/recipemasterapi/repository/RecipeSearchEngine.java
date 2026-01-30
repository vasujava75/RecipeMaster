package com.api.recipe.recipemasterapi.repository;

import com.api.recipe.recipemasterapi.domain.Recipe;
import com.api.recipe.recipemasterapi.utils.RecipeSearchCriteria;

import java.util.List;

public interface RecipeSearchEngine {
    List<Recipe> search(RecipeSearchCriteria criteria);
}
