package com.api.recipe.recipemasterapi.service;

import com.api.recipe.recipemasterapi.dto.RecipeDto;
import com.api.recipe.recipemasterapi.domain.Recipe;
import com.api.recipe.recipemasterapi.exceptions.RecipeNotFoundException;
import com.api.recipe.recipemasterapi.repository.RecipeRepository;
import com.api.recipe.recipemasterapi.mapper.RecipeMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecipeCommandServiceTest {

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private RecipeMapper recipeMapper;

    @Mock
    private RecipeValidationService validationService;

    private RecipeCommandService recipeCommandService;

    @BeforeEach
    void setUp() {
        recipeCommandService = new RecipeCommandService(recipeRepository, recipeMapper, validationService);
    }

    @Test
    void createRecipe_ShouldReturnRecipeDto_WhenValidRecipeProvided() {
        // Given
        RecipeDto inputDto = new RecipeDto();
        Recipe recipe = new Recipe();
        Recipe savedRecipe = new Recipe();
        RecipeDto expectedDto = new RecipeDto();

        when(recipeMapper.toEntity(inputDto)).thenReturn(recipe);
        when(recipeRepository.save(recipe)).thenReturn(savedRecipe);
        when(recipeMapper.toDto(savedRecipe)).thenReturn(expectedDto);

        // When
        RecipeDto result = recipeCommandService.createRecipe(inputDto);

        // Then
        assertSame(expectedDto, result);
        verify(recipeMapper).toEntity(inputDto);
        verify(validationService).validateRecipe(recipe);
        verify(recipeRepository).save(recipe);
        verify(recipeMapper).toDto(savedRecipe);
    }

    @Test
    void createRecipe_ShouldValidateRecipe_BeforeSaving() {
        // Given
        RecipeDto inputDto = new RecipeDto();
        Recipe recipe = new Recipe();
        Recipe savedRecipe = new Recipe();
        RecipeDto expectedDto = new RecipeDto();

        when(recipeMapper.toEntity(inputDto)).thenReturn(recipe);
        when(recipeRepository.save(recipe)).thenReturn(savedRecipe);
        when(recipeMapper.toDto(savedRecipe)).thenReturn(expectedDto);

        // When
        recipeCommandService.createRecipe(inputDto);

        // Then
        verify(validationService).validateRecipe(recipe);
    }

    @Test
    void updateRecipe_ShouldReturnUpdatedRecipeDto_WhenRecipeExists() {
        // Given
        Long id = 1L;
        RecipeDto inputDto = new RecipeDto();
        Recipe existingRecipe = new Recipe();
        Recipe savedRecipe = new Recipe();
        RecipeDto expectedDto = new RecipeDto();

        when(recipeRepository.findById(id)).thenReturn(Optional.of(existingRecipe));
        when(recipeRepository.save(existingRecipe)).thenReturn(savedRecipe);
        when(recipeMapper.toDto(savedRecipe)).thenReturn(expectedDto);

        // When
        RecipeDto result = recipeCommandService.updateRecipe(id, inputDto);

        // Then
        assertSame(expectedDto, result);
        verify(recipeRepository).findById(id);
        verify(recipeMapper).updateEntity(existingRecipe, inputDto);
        verify(validationService).validateRecipe(existingRecipe);
        verify(recipeRepository).save(existingRecipe);
        verify(recipeMapper).toDto(savedRecipe);
    }

    @Test
    void updateRecipe_ShouldThrowException_WhenRecipeNotFound() {
        // Given
        Long id = 1L;
        RecipeDto inputDto = new RecipeDto();

        when(recipeRepository.findById(id)).thenReturn(Optional.empty());

        // When & Then
        RecipeNotFoundException exception = assertThrows(
                RecipeNotFoundException.class,
                () -> recipeCommandService.updateRecipe(id, inputDto)
        );

        assertEquals("Recipe not found with id: 1", exception.getMessage());
        verify(recipeRepository).findById(id);
        verifyNoInteractions(recipeMapper);
        verifyNoInteractions(validationService);
        verify(recipeRepository, never()).save(any());
    }

    @Test
    void updateRecipe_ShouldValidateRecipe_BeforeSaving() {
        // Given
        Long id = 1L;
        RecipeDto inputDto = new RecipeDto();
        Recipe existingRecipe = new Recipe();
        Recipe savedRecipe = new Recipe();
        RecipeDto expectedDto = new RecipeDto();

        when(recipeRepository.findById(id)).thenReturn(Optional.of(existingRecipe));
        when(recipeRepository.save(existingRecipe)).thenReturn(savedRecipe);
        when(recipeMapper.toDto(savedRecipe)).thenReturn(expectedDto);

        // When
        recipeCommandService.updateRecipe(id, inputDto);

        // Then
        verify(validationService).validateRecipe(existingRecipe);
    }

    @Test
    void deleteRecipe_ShouldDeleteRecipe_WhenRecipeExists() {
        // Given
        Long id = 1L;
        Recipe existingRecipe = new Recipe();

        when(recipeRepository.findById(id)).thenReturn(Optional.of(existingRecipe));

        // When
        recipeCommandService.deleteRecipe(id);

        // Then
        verify(recipeRepository).findById(id);
        verify(recipeRepository).delete(existingRecipe);
    }

    @Test
    void deleteRecipe_ShouldThrowException_WhenRecipeNotFound() {
        // Given
        Long id = 1L;

        when(recipeRepository.findById(id)).thenReturn(Optional.empty());

        // When & Then
        RecipeNotFoundException exception = assertThrows(
                RecipeNotFoundException.class,
                () -> recipeCommandService.deleteRecipe(id)
        );

        assertEquals("Recipe not found with id: 1", exception.getMessage());
        verify(recipeRepository).findById(id);
        verify(recipeRepository, never()).delete(any());
    }
}
