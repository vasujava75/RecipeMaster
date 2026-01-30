package com.api.recipe.recipemasterapi.repository;

import com.api.recipe.recipemasterapi.domain.Recipe;

public interface RecipeWriter {
    Recipe save(Recipe recipe);
    Recipe update(Recipe recipe);
    void deleteById(Long id);
    void delete(Recipe recipe);
    void deleteAll();
}
