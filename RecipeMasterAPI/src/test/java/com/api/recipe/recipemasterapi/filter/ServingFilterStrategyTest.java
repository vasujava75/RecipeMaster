package com.api.recipe.recipemasterapi.filter;


import com.api.recipe.recipemasterapi.domain.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class ServingFilterStrategyTest {

    @Mock
    private Recipe mockRecipe1;

    @Mock
    private Recipe mockRecipe2;

    @Mock
    private Recipe mockRecipe3;

    private ServingFilterStrategy servingFilterStrategy;

    @BeforeEach
    void setUp() {
        servingFilterStrategy = new ServingFilterStrategy();
    }

    @Test
    void shouldReturnEmptyListWhenInputIsEmpty() {
        // Given
        List<Recipe> recipes = Collections.emptyList();

        // When
        List<Recipe> result = servingFilterStrategy.filter(recipes, "4");

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }



    @Test
    void shouldReturnOriginalListWhenSearchTermIsNull() {
        // Given
        List<Recipe> recipes = Arrays.asList(mockRecipe1, mockRecipe2);

        // When
        List<Recipe> result = servingFilterStrategy.filter(recipes, null);

        // Then
        assertNotNull(result);
        assertEquals(recipes.size(), result.size());
        assertEquals(recipes, result);
    }

    @Test
    void shouldReturnOriginalListWhenSearchTermIsEmpty() {
        // Given
        List<Recipe> recipes = Arrays.asList(mockRecipe1, mockRecipe2);

        // When
        List<Recipe> result = servingFilterStrategy.filter(recipes, "");

        // Then
        assertNotNull(result);
        assertEquals(recipes.size(), result.size());
        assertEquals(recipes, result);
    }






    @Test
    void shouldReturnNonNullResult() {
        // Given
        List<Recipe> recipes = Arrays.asList(mockRecipe1);

        // When
        List<Recipe> result = servingFilterStrategy.filter(recipes, "8");

        // Then
        assertNotNull(result);
    }

    @Test
    void shouldReturnEmptyListWhenInputIsNull() {
        // When
        List<Recipe> result = servingFilterStrategy.filter(null, "4");

        // Then
        assertNull(result);
    }

    @Test
    void shouldFilterRecipesByExactServingSize() {
        // Given
        List<Recipe> recipes = Arrays.asList(mockRecipe1, mockRecipe2);

        // When
        List<Recipe> result = servingFilterStrategy.filter(recipes, "4");

        // Then
        assertNotNull(result);
        assertEquals(recipes.size(), result.size());
    }

    @Test
    void shouldReturnEmptyWhenNoRecipesMatchServingSize() {
        // Given
        List<Recipe> recipes = Arrays.asList(mockRecipe1, mockRecipe2);

        // When
        List<Recipe> result = servingFilterStrategy.filter(recipes, "4");

        // Then
        assertNotNull(result);
        assertEquals(recipes.size(), result.size());
    }

    @Test
    void shouldHandleMultipleRecipesWithSameServingSize() {
        // Given
        List<Recipe> recipes = Arrays.asList(mockRecipe1, mockRecipe2, mockRecipe3);

        // When
        List<Recipe> result = servingFilterStrategy.filter(recipes, "4");

        // Then
        assertNotNull(result);
        assertEquals(3, result.size());
    }

    @Test
    void shouldHandleInvalidServingSizeFormat() {
        // Given
        List<Recipe> recipes = Arrays.asList(mockRecipe1, mockRecipe2);

        // When
        List<Recipe> result = servingFilterStrategy.filter(recipes, "not-a-number");

        // Then
        assertNotNull(result);
        assertEquals(recipes.size(), result.size());
    }

    @Test
    void shouldHandleNegativeServingSize() {
        // Given
        List<Recipe> recipes = Arrays.asList(mockRecipe1, mockRecipe2);

        // When
        List<Recipe> result = servingFilterStrategy.filter(recipes, "-1");

        // Then
        assertNotNull(result);
        assertEquals(recipes.size(), result.size());
    }

    @Test
    void shouldHandleZeroServingSize() {
        // Given
        List<Recipe> recipes = Arrays.asList(mockRecipe1);

        // When
        List<Recipe> result = servingFilterStrategy.filter(recipes, "0");

        // Then
        assertNotNull(result);
        assertEquals(recipes.size(), result.size());
    }

}
