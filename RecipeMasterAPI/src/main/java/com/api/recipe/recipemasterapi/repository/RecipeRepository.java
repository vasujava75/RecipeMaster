package com.api.recipe.recipemasterapi.repository;

import com.api.recipe.recipemasterapi.domain.Recipe;
import com.api.recipe.recipemasterapi.domain.DifficultyLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    // Find recipes by vegetarian status
    List<Recipe> findByVegetarian(Boolean vegetarian);

    // Find recipes by servings
    List<Recipe> findByServings(Integer servings);

    // Find recipes by name containing (case insensitive)
    List<Recipe> findByNameContainingIgnoreCase(String name);

    // Find recipes by difficulty level
    List<Recipe> findByDifficulty(DifficultyLevel difficulty);

    // Find recipes by ingredient name
    @Query("SELECT DISTINCT r FROM Recipe r JOIN r.ingredients i WHERE LOWER(i.name) LIKE LOWER(CONCAT('%', :ingredientName, '%'))")
    List<Recipe> findByIngredientNameContainingIgnoreCase(@Param("ingredientName") String ingredientName);

    // Find recipes with prep time less than or equal to specified minutes
    List<Recipe> findByPrepTimeMinutesLessThanEqual(Integer maxPrepTime);

    // Find recipes with cook time less than or equal to specified minutes
    List<Recipe> findByCookTimeMinutesLessThanEqual(Integer maxCookTime);
}
