package com.api.recipe.recipemasterapi.service;

import com.api.recipe.recipemasterapi.dto.RecipeDto;
import com.api.recipe.recipemasterapi.domain.Recipe;
import com.api.recipe.recipemasterapi.repository.RecipeRepository;
import com.api.recipe.recipemasterapi.mapper.RecipeMapper;
import com.api.recipe.recipemasterapi.utils.RecipeSearchCriteria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecipeQueryServiceTest {

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private RecipeMapper recipeMapper;

    private RecipeQueryService recipeQueryService;

    @BeforeEach
    void setUp() {
        recipeQueryService = new RecipeQueryService();
        try {
            java.lang.reflect.Field repoField = RecipeQueryService.class.getDeclaredField("recipeRepository");
            repoField.setAccessible(true);
            repoField.set(recipeQueryService, recipeRepository);

            java.lang.reflect.Field mapperField = RecipeQueryService.class.getDeclaredField("recipeMapper");
            mapperField.setAccessible(true);
            mapperField.set(recipeQueryService, recipeMapper);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void searchRecipes_ReturnsEmptyListWhenNoRecipesExist() {
        RecipeSearchCriteria criteria = new RecipeSearchCriteria();
        when(recipeRepository.findAll()).thenReturn(Collections.emptyList());

        List<RecipeDto> result = recipeQueryService.searchRecipes(criteria);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void searchRecipes_ReturnsMappedRecipesWhenRecipesExist() {
        RecipeSearchCriteria criteria = new RecipeSearchCriteria();
        Recipe recipe1 = new Recipe();
        Recipe recipe2 = new Recipe();
        RecipeDto dto1 = new RecipeDto();
        RecipeDto dto2 = new RecipeDto();

        when(recipeRepository.findAll()).thenReturn(List.of(recipe1, recipe2));
        when(recipeMapper.toDto(recipe1)).thenReturn(dto1);
        when(recipeMapper.toDto(recipe2)).thenReturn(dto2);

        List<RecipeDto> result = recipeQueryService.searchRecipes(criteria);

        assertEquals(2, result.size());
        assertEquals(dto1, result.get(0));
        assertEquals(dto2, result.get(1));
    }

    @Test
    void searchRecipes_HandlesNullCriteriaGracefully() {
        Recipe recipe = new Recipe();
        RecipeDto dto = new RecipeDto();

        when(recipeRepository.findAll()).thenReturn(List.of(recipe));
        when(recipeMapper.toDto(recipe)).thenReturn(dto);

        List<RecipeDto> result = recipeQueryService.searchRecipes(null);

        assertEquals(1, result.size());
        assertEquals(dto, result.get(0));
    }

    @Test
    void getRecipeById_ReturnsMappedRecipeWhenRecipeExists() {
        Long id = 1L;
        Recipe recipe = new Recipe();
        RecipeDto dto = new RecipeDto();

        when(recipeRepository.findById(id)).thenReturn(Optional.of(recipe));
        when(recipeMapper.toDto(recipe)).thenReturn(dto);

        Optional<RecipeDto> result = recipeQueryService.getRecipeById(id);

        assertTrue(result.isPresent());
        assertEquals(dto, result.get());
    }

    @Test
    void getRecipeById_ReturnsEmptyOptionalWhenRecipeDoesNotExist() {
        Long id = 1L;

        when(recipeRepository.findById(id)).thenReturn(Optional.empty());

        Optional<RecipeDto> result = recipeQueryService.getRecipeById(id);

        assertFalse(result.isPresent());
    }

    @Test
    void getRecipeById_HandlesNullId() {
        when(recipeRepository.findById(null)).thenReturn(Optional.empty());

        Optional<RecipeDto> result = recipeQueryService.getRecipeById(null);

        assertFalse(result.isPresent());
    }
}
