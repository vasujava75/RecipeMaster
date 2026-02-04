package com.api.recipe.recipemasterapi.service;

import com.api.recipe.recipemasterapi.domain.Ingredient;
import com.api.recipe.recipemasterapi.domain.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class RecipeValidationServiceTest {

    private RecipeValidationService validationService;

    @BeforeEach
    void setUp() {
        validationService = new RecipeValidationService();
    }

    @Test
    void validateRecipe_AcceptsValidRecipe() {
        Recipe recipe = new Recipe();
        recipe.setName("Pasta");
        recipe.setServings(4);
        recipe.setPrepTimeMinutes(15);
        recipe.setCookTimeMinutes(20);

        assertDoesNotThrow(() -> validationService.validateRecipe(recipe));
    }

    @Test
    void validateRecipe_ThrowsExceptionWhenRecipeIsNull() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> validationService.validateRecipe(null)
        );

        assertEquals("Recipe cannot be null", exception.getMessage());
    }

    @Test
    void validateRecipe_ThrowsExceptionWhenNameIsNull() {
        Recipe recipe = new Recipe();
        recipe.setName(null);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> validationService.validateRecipe(recipe)
        );

        assertEquals("Recipe name is required", exception.getMessage());
    }

    @Test
    void validateRecipe_ThrowsExceptionWhenNameIsEmpty() {
        Recipe recipe = new Recipe();
        recipe.setName("");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> validationService.validateRecipe(recipe)
        );

        assertEquals("Recipe name is required", exception.getMessage());
    }

    @Test
    void validateRecipe_ThrowsExceptionWhenNameIsOnlyWhitespace() {
        Recipe recipe = new Recipe();
        recipe.setName("   ");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> validationService.validateRecipe(recipe)
        );

        assertEquals("Recipe name is required", exception.getMessage());
    }

    @Test
    void validateRecipe_ThrowsExceptionWhenServingsIsZero() {
        Recipe recipe = new Recipe();
        recipe.setName("Pasta");
        recipe.setServings(0);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> validationService.validateRecipe(recipe)
        );

        assertEquals("Servings must be positive", exception.getMessage());
    }

    @Test
    void validateRecipe_ThrowsExceptionWhenServingsIsNegative() {
        Recipe recipe = new Recipe();
        recipe.setName("Pasta");
        recipe.setServings(-1);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> validationService.validateRecipe(recipe)
        );

        assertEquals("Servings must be positive", exception.getMessage());
    }

    @Test
    void validateRecipe_AcceptsNullServings() {
        Recipe recipe = new Recipe();
        recipe.setName("Pasta");
        recipe.setServings(null);

        assertDoesNotThrow(() -> validationService.validateRecipe(recipe));
    }

    @Test
    void validateRecipe_ThrowsExceptionWhenPrepTimeIsNegative() {
        Recipe recipe = new Recipe();
        recipe.setName("Pasta");
        recipe.setPrepTimeMinutes(-1);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> validationService.validateRecipe(recipe)
        );

        assertEquals("Preparation time cannot be negative", exception.getMessage());
    }

    @Test
    void validateRecipe_AcceptsZeroPrepTime() {
        Recipe recipe = new Recipe();
        recipe.setName("Pasta");
        recipe.setPrepTimeMinutes(0);

        assertDoesNotThrow(() -> validationService.validateRecipe(recipe));
    }

    @Test
    void validateRecipe_AcceptsNullPrepTime() {
        Recipe recipe = new Recipe();
        recipe.setName("Pasta");
        recipe.setPrepTimeMinutes(null);

        assertDoesNotThrow(() -> validationService.validateRecipe(recipe));
    }

    @Test
    void validateRecipe_ThrowsExceptionWhenCookTimeIsNegative() {
        Recipe recipe = new Recipe();
        recipe.setName("Pasta");
        recipe.setCookTimeMinutes(-1);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> validationService.validateRecipe(recipe)
        );

        assertEquals("Cook time cannot be negative", exception.getMessage());
    }

    @Test
    void validateRecipe_AcceptsZeroCookTime() {
        Recipe recipe = new Recipe();
        recipe.setName("Pasta");
        recipe.setCookTimeMinutes(0);

        assertDoesNotThrow(() -> validationService.validateRecipe(recipe));
    }

    @Test
    void validateRecipe_AcceptsNullCookTime() {
        Recipe recipe = new Recipe();
        recipe.setName("Pasta");
        recipe.setCookTimeMinutes(null);

        assertDoesNotThrow(() -> validationService.validateRecipe(recipe));
    }

    @Test
    void validateRecipe_AcceptsNullIngredients() {
        Recipe recipe = new Recipe();
        recipe.setName("Pasta");
        recipe.setIngredients(null);

        assertDoesNotThrow(() -> validationService.validateRecipe(recipe));
    }

    @Test
    void validateIngredients_AcceptsValidIngredients() {
        List<Ingredient> ingredients = List.of(
                createValidIngredient("Flour", 2.0),
                createValidIngredient("Salt", 1.0)
        );

        assertDoesNotThrow(() -> validationService.validateIngredients(ingredients));
    }

    @Test
    void validateIngredients_ThrowsExceptionWhenIngredientsIsNull() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> validationService.validateIngredients(null)
        );

        assertEquals("Recipe must have at least one ingredient", exception.getMessage());
    }

    @Test
    void validateIngredients_ThrowsExceptionWhenIngredientsIsEmpty() {
        List<Ingredient> ingredients = Collections.emptyList();

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> validationService.validateIngredients(ingredients)
        );

        assertEquals("Recipe must have at least one ingredient", exception.getMessage());
    }

    @Test
    void validateIngredients_ThrowsExceptionWhenIngredientNameIsNull() {
        List<Ingredient> ingredients = List.of(createIngredient(null, 1.0));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> validationService.validateIngredients(ingredients)
        );

        assertEquals("Ingredient name is required", exception.getMessage());
    }

    @Test
    void validateIngredients_ThrowsExceptionWhenIngredientNameIsEmpty() {
        List<Ingredient> ingredients = List.of(createIngredient("", 1.0));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> validationService.validateIngredients(ingredients)
        );

        assertEquals("Ingredient name is required", exception.getMessage());
    }

    @Test
    void validateIngredients_ThrowsExceptionWhenIngredientNameIsOnlyWhitespace() {
        List<Ingredient> ingredients = List.of(createIngredient("   ", 1.0));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> validationService.validateIngredients(ingredients)
        );

        assertEquals("Ingredient name is required", exception.getMessage());
    }

    @Test
    void validateIngredients_ThrowsExceptionWhenIngredientQuantityIsNull() {
        List<Ingredient> ingredients = List.of(createIngredient("Flour", null));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> validationService.validateIngredients(ingredients)
        );

        assertEquals("Ingredient quantity must be positive", exception.getMessage());
    }

    @Test
    void validateIngredients_ThrowsExceptionWhenIngredientQuantityIsZero() {
        List<Ingredient> ingredients = List.of(createIngredient("Flour", 0.0));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> validationService.validateIngredients(ingredients)
        );

        assertEquals("Ingredient quantity must be positive", exception.getMessage());
    }

    @Test
    void validateIngredients_ThrowsExceptionWhenIngredientQuantityIsNegative() {
        List<Ingredient> ingredients = List.of(createIngredient("Flour", -1.0));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> validationService.validateIngredients(ingredients)
        );

        assertEquals("Ingredient quantity must be positive", exception.getMessage());
    }

    private Ingredient createValidIngredient(String name, Double quantity) {
        return createIngredient(name, quantity);
    }

    private Ingredient createIngredient(String name, Double quantity) {
        Ingredient ingredient = new Ingredient();
        ingredient.setName(name);
        ingredient.setQuantity(quantity);
        return ingredient;
    }

}