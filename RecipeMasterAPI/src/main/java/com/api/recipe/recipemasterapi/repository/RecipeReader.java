package com.api.recipe.recipemasterapi.repository;

import com.api.recipe.recipemasterapi.domain.Recipe;
import com.api.recipe.recipemasterapi.utils.RecipeSearchCriteria;

import java.util.List;
import java.util.Optional;

public interface RecipeReader {
    List<Recipe> findAll();
    Optional<Recipe> findById(Long id);
    List<Recipe> findByVegetarian(Boolean vegetarian);
    List<Recipe> findByServings(Integer servings);
    List<Recipe> findByNameContainingIgnoreCase(String name);
    List<Recipe> search(RecipeSearchCriteria criteria);
    boolean existsById(Long id);
    long count();
}
