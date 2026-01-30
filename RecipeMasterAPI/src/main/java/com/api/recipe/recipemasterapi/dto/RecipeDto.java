package com.api.recipe.recipemasterapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class RecipeDto {

    @JsonProperty("id")
    private Long id;

    @NotBlank(message = "Recipe name is required")
    @Size(max = 255, message = "Recipe name must not exceed 255 characters")
    @JsonProperty("name")
    private String name;

    @NotBlank(message = "Instructions are required")
    @JsonProperty("instructions")
    private String instructions;

    @NotNull(message = "Servings is required")
    @Min(value = 1, message = "Servings must be at least 1")
    @JsonProperty("servings")
    private Integer servings;

    @NotNull(message = "Vegetarian status is required")
    @JsonProperty("vegetarian")
    private Boolean vegetarian;

    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    @JsonProperty("description")
    private String description;

    @Min(value = 0, message = "Prep time must be non-negative")
    @JsonProperty("prepTimeMinutes")
    private Integer prepTimeMinutes;

    @Min(value = 0, message = "Cook time must be non-negative")
    @JsonProperty("cookTimeMinutes")
    private Integer cookTimeMinutes;

    @Pattern(regexp = "^(EASY|MEDIUM|HARD)$", message = "Difficulty must be EASY, MEDIUM, or HARD")
    @JsonProperty("difficulty")
    private String difficulty;

    @Valid
    @JsonProperty("ingredients")
    private List<IngredientDto> ingredients;

    @JsonProperty("createdAt")
    private LocalDateTime createdAt;

    @JsonProperty("updatedAt")
    private LocalDateTime updatedAt;
}
