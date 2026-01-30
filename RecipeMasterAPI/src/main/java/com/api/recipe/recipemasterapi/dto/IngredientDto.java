package com.api.recipe.recipemasterapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class IngredientDto {

    @JsonProperty("id")
    private Long id;

    @NotBlank(message = "Ingredient name is required")
    @Size(max = 255, message = "Ingredient name must not exceed 255 characters")
    @JsonProperty("name")
    private String name;

    @NotBlank(message = "Quantity is required")
    @Size(max = 100, message = "Quantity must not exceed 100 characters")
    @JsonProperty("quantity")
    private String quantity;

    @Size(max = 50, message = "Unit must not exceed 50 characters")
    @JsonProperty("unit")
    private String unit;
}
