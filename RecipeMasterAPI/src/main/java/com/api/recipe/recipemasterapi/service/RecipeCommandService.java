package com.api.recipe.recipemasterapi.service;

import com.api.recipe.recipemasterapi.dto.RecipeDto;
import com.api.recipe.recipemasterapi.domain.Recipe;
import com.api.recipe.recipemasterapi.repository.RecipeRepository;
import com.api.recipe.recipemasterapi.mapper.RecipeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecipeCommandService {
    private final RecipeRepository recipeRepository;
    private final RecipeMapper recipeMapper;
    private final RecipeValidationService validationService;

    public RecipeCommandService(RecipeRepository recipeRepository,
                                RecipeMapper recipeMapper,
                                RecipeValidationService validationService) {
        this.recipeRepository = recipeRepository;
        this.recipeMapper = recipeMapper;
        this.validationService = validationService;
    }

    public RecipeDto createRecipe(RecipeDto recipeDto) {
        Recipe recipe = recipeMapper.toEntity(recipeDto);
        validationService.validateRecipe(recipe);
        Recipe savedRecipe = recipeRepository.save(recipe);
        return recipeMapper.toDto(savedRecipe);
    }

    public RecipeDto updateRecipe(Long id, RecipeDto recipeDto) {
        Recipe existingRecipe = recipeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Recipe not found with id: " + id));

        recipeMapper.updateEntity(existingRecipe, recipeDto);
        validationService.validateRecipe(existingRecipe);
        Recipe savedRecipe = recipeRepository.save(existingRecipe);
        return recipeMapper.toDto(savedRecipe);
    }

    public void deleteRecipe(Long id) {
        Recipe existingRecipe = recipeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Recipe not found with id: " + id));

        recipeRepository.delete(existingRecipe);
    }
}
