package com.api.recipe.recipemasterapi.utils;

import com.api.recipe.recipemasterapi.domain.DifficultyLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class RecipeSearchCriteria {

    private String name;
    private Boolean vegetarian;
    private Integer servings;
    private DifficultyLevel difficulty;
    private List<String> ingredients;
    private Integer maxPrepTime;
    private Integer maxCookTime;

    public RecipeSearchCriteria(String name, Boolean vegetarian, Integer servings,
                                DifficultyLevel difficulty, List<String> ingredients) {
        this.name = name;
        this.vegetarian = vegetarian;
        this.servings = servings;
        this.difficulty = difficulty;
        this.ingredients = ingredients;
    }
}
