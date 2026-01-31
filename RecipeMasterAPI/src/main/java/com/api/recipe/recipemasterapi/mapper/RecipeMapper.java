package com.api.recipe.recipemasterapi.mapper;

import com.api.recipe.recipemasterapi.domain.Recipe;
import com.api.recipe.recipemasterapi.domain.Ingredient;
import com.api.recipe.recipemasterapi.dto.RecipeDto;
import com.api.recipe.recipemasterapi.dto.IngredientDto;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RecipeMapper {

    public RecipeDto toDto(Recipe recipe) {
        if (recipe == null) return null;

        RecipeDto dto = new RecipeDto();
        dto.setId(recipe.getId());
        dto.setName(recipe.getName());
        dto.setDescription(recipe.getDescription());
        dto.setInstructions(recipe.getInstructions());
        dto.setServings(recipe.getServings());
        dto.setIngredients(mapIngredientsToDto(recipe.getIngredients()));
        return dto;
    }

    public Recipe toEntity(RecipeDto dto) {
        if (dto == null) return null;

        Recipe recipe = new Recipe();
        recipe.setName(dto.getName());
        recipe.setDescription(dto.getDescription());
        recipe.setInstructions(dto.getInstructions());
        recipe.setServings(dto.getServings());
        recipe.setIngredients(mapIngredientsToEntity(dto.getIngredients(), recipe));
        return recipe;
    }

    public void updateEntity(Recipe target, RecipeDto source) {
        if (source == null || target == null) return;

        if (source.getName() != null) target.setName(source.getName());
        if (source.getDescription() != null) target.setDescription(source.getDescription());
        if (source.getInstructions() != null) target.setInstructions(source.getInstructions());
        if (source.getServings() != null) target.setServings(source.getServings());
        if (source.getIngredients() != null) target.setIngredients(mapIngredientsToEntity(source.getIngredients(), target));
    }

    private List<IngredientDto> mapIngredientsToDto(List<Ingredient> ingredients) {
        if (ingredients == null) return null;

        return ingredients.stream()
                .map(ingredient -> {
                    IngredientDto dto = new IngredientDto();
                    dto.setId(ingredient.getId());
                    dto.setName(ingredient.getName());
                    dto.setQuantity(ingredient.getQuantity() != null ? ingredient.getQuantity().toString() : null);
                    dto.setUnit(ingredient.getUnit());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    private List<Ingredient> mapIngredientsToEntity(List<IngredientDto> ingredientDtos, Recipe recipe) {
        if (ingredientDtos == null) return null;

        return ingredientDtos.stream()
                .map(dto -> {
                    Ingredient ingredient = new Ingredient();
                    ingredient.setId(dto.getId());
                    ingredient.setName(dto.getName());
                    ingredient.setQuantity(parseQuantity(dto.getQuantity()));
                    ingredient.setUnit(dto.getUnit());
                    ingredient.setRecipe(recipe);  // Set the parent recipe
                    return ingredient;
                })
                .collect(Collectors.toList());
    }

    private Double parseQuantity(String quantityString) {
        if (quantityString == null || quantityString.trim().isEmpty()) {
            return null;
        }
        try {
            return Double.parseDouble(quantityString.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid quantity format: " + quantityString);
        }
    }
}
