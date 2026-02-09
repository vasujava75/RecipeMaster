package com.api.recipe.recipemasterapi.resource;

import com.api.recipe.recipemasterapi.domain.DifficultyLevel;
import com.api.recipe.recipemasterapi.dto.RecipeDto;
import com.api.recipe.recipemasterapi.service.RecipeQueryService;
import com.api.recipe.recipemasterapi.utils.RecipeSearchCriteria;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/recipes")
public class RecipeQueryController {
    private final RecipeQueryService queryService;

    @Autowired
    public RecipeQueryController(RecipeQueryService queryService) {
        this.queryService = queryService;
    }

    @GetMapping
    public ResponseEntity<List<RecipeDto>> getAllRecipes() {
        List<RecipeDto> recipes = queryService.searchRecipes(null);
        return ResponseEntity.ok(recipes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeDto> getRecipe(@PathVariable Long id) {
        Optional<RecipeDto> recipe = queryService.getRecipeById(id);
        return recipe.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    @Operation(
            summary = "Advanced recipe search",
            description = "Search recipes with multiple filtering criteria"
    )
    public ResponseEntity<List<RecipeDto>> searchRecipes(
            @Parameter(description = "Recipe name to search for")
            @RequestParam(required = false) String name,

            @Parameter(description = "Filter by vegetarian status")
            @RequestParam(required = false) Boolean vegetarian,

            @Parameter(description = "Minimum number of servings")
            @RequestParam(required = false) Integer servings,

            @Parameter(description = "Recipe difficulty level")
            @RequestParam(required = false) DifficultyLevel difficulty,

            @Parameter(description = "Ingredients to include (comma-separated)")
            @RequestParam(required = false) List<String> includeIngredients,

            @Parameter(description = "Ingredients to exclude (comma-separated)")
            @RequestParam(required = false) List<String> excludeIngredients,

            @Parameter(description = "Text to search in instructions")
            @RequestParam(required = false) String instructionsText,

            @Parameter(description = "Maximum preparation time in minutes")
            @RequestParam(required = false) Integer maxPrepTime,

            @Parameter(description = "Maximum cooking time in minutes")
            @RequestParam(required = false) Integer maxCookTime
    ) {
        RecipeSearchCriteria criteria = new RecipeSearchCriteria();
        criteria.setName(name);
        criteria.setVegetarian(vegetarian);
        criteria.setServings(servings);
        criteria.setDifficulty(difficulty);
        criteria.setIncludeIngredients(includeIngredients);
        criteria.setExcludeIngredients(excludeIngredients);
        criteria.setInstructionsText(instructionsText);
        criteria.setMaxPrepTime(maxPrepTime);
        criteria.setMaxCookTime(maxCookTime);

        List<RecipeDto> recipes = queryService.searchRecipes(criteria);
        return ResponseEntity.ok(recipes);
    }
}
