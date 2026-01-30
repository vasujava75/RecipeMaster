package com.api.recipe.recipemasterapi.service;

import com.api.recipe.recipemasterapi.dto.RecipeDto;
import com.api.recipe.recipemasterapi.utils.RecipeSearchCriteria;
import com.api.recipe.recipemasterapi.mapper.RecipeMapper;
import com.api.recipe.recipemasterapi.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RecipeQueryService {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private RecipeMapper recipeMapper;

    public List<RecipeDto> searchRecipes(RecipeSearchCriteria criteria) {
        return recipeRepository.findAll().stream()
                .map(recipeMapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<RecipeDto> getRecipeById(Long id) {
        return recipeRepository.findById(id)
                .map(recipeMapper::toDto);
    }
}
