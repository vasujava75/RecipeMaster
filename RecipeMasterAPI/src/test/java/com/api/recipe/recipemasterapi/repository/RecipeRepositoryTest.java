
package com.api.recipe.recipemasterapi.repository;

import com.api.recipe.recipemasterapi.TestcontainersConfiguration;
import com.api.recipe.recipemasterapi.domain.DifficultyLevel;
import com.api.recipe.recipemasterapi.domain.Recipe;
import com.api.recipe.recipemasterapi.domain.Ingredient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.context.annotation.Import;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(TestcontainersConfiguration.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RecipeRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RecipeRepository recipeRepository;

    private Recipe testRecipe;
    private Recipe vegetarianRecipe;

    @BeforeEach
    void setUp() {
        // Clear any existing data
        entityManager.getEntityManager().createQuery("DELETE FROM Ingredient").executeUpdate();
        entityManager.getEntityManager().createQuery("DELETE FROM Recipe").executeUpdate();
        entityManager.flush();

        testRecipe = new Recipe();
        testRecipe.setName("Test Recipe");
        testRecipe.setDescription("A test recipe description");
        testRecipe.setInstructions("Step 1, Step 2");
        testRecipe.setVegetarian(false);
        testRecipe.setServings(4);
        testRecipe.setDifficulty(DifficultyLevel.EASY);
        testRecipe.setPrepTimeMinutes(30);
        testRecipe.setCookTimeMinutes(45);

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setName("Chicken");
        ingredient1.setQuantity(500.0);
        ingredient1.setUnit("g");
        ingredient1.setRecipe(testRecipe);
        testRecipe.setIngredients(List.of(ingredient1));

        vegetarianRecipe = new Recipe();
        vegetarianRecipe.setName("Veggie Pasta");
        vegetarianRecipe.setDescription("Delicious vegetarian pasta");
        vegetarianRecipe.setInstructions("Cook pasta, add vegetables");
        vegetarianRecipe.setVegetarian(true);
        vegetarianRecipe.setServings(2);
        vegetarianRecipe.setDifficulty(DifficultyLevel.MEDIUM);
        vegetarianRecipe.setPrepTimeMinutes(15);
        vegetarianRecipe.setCookTimeMinutes(20);

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setName("Tomatoes");
        ingredient2.setQuantity(3.0);
        ingredient2.setUnit("pieces");
        ingredient2.setRecipe(vegetarianRecipe);
        vegetarianRecipe.setIngredients(List.of(ingredient2));
    }



    @Test
    void shouldFindRecipesByVegetarianStatus() {
        entityManager.persistAndFlush(testRecipe);
        entityManager.persistAndFlush(vegetarianRecipe);

        List<Recipe> vegetarianRecipes = recipeRepository.findByVegetarian(true);
        List<Recipe> nonVegetarianRecipes = recipeRepository.findByVegetarian(false);

        assertEquals(1, vegetarianRecipes.size());
        assertEquals("Veggie Pasta", vegetarianRecipes.get(0).getName());
        assertEquals(1, nonVegetarianRecipes.size());
        assertEquals("Test Recipe", nonVegetarianRecipes.get(0).getName());
    }

    @Test
    void shouldFindRecipesByServings() {
        entityManager.persistAndFlush(testRecipe);
        entityManager.persistAndFlush(vegetarianRecipe);

        List<Recipe> recipesForFour = recipeRepository.findByServings(4);
        List<Recipe> recipesForTwo = recipeRepository.findByServings(2);

        assertEquals(1, recipesForFour.size());
        assertEquals("Test Recipe", recipesForFour.get(0).getName());
        assertEquals(1, recipesForTwo.size());
        assertEquals("Veggie Pasta", recipesForTwo.get(0).getName());
    }

    @Test
    void shouldFindRecipesByDifficultyLevel() {
        entityManager.persistAndFlush(testRecipe);
        entityManager.persistAndFlush(vegetarianRecipe);

        List<Recipe> easyRecipes = recipeRepository.findByDifficulty(DifficultyLevel.EASY);
        List<Recipe> mediumRecipes = recipeRepository.findByDifficulty(DifficultyLevel.MEDIUM);

        assertEquals(1, easyRecipes.size());
        assertEquals("Test Recipe", easyRecipes.get(0).getName());
        assertEquals(1, mediumRecipes.size());
        assertEquals("Veggie Pasta", mediumRecipes.get(0).getName());
    }

    @Test
    void shouldFindRecipesByIngredientName() {
        entityManager.persistAndFlush(testRecipe);
        entityManager.persistAndFlush(vegetarianRecipe);

        List<Recipe> recipesWithChicken = recipeRepository.findByIngredientNameContainingIgnoreCase("chicken");
        List<Recipe> recipesWithTomatoes = recipeRepository.findByIngredientNameContainingIgnoreCase("tomato");

        assertEquals(1, recipesWithChicken.size());
        assertEquals("Test Recipe", recipesWithChicken.get(0).getName());
        assertEquals(1, recipesWithTomatoes.size());
        assertEquals("Veggie Pasta", recipesWithTomatoes.get(0).getName());
    }

    @Test
    void shouldFindRecipesByPrepTimeLimit() {
        entityManager.persistAndFlush(testRecipe);
        entityManager.persistAndFlush(vegetarianRecipe);

        List<Recipe> quickPrepRecipes = recipeRepository.findByPrepTimeMinutesLessThanEqual(20);
        List<Recipe> allPrepRecipes = recipeRepository.findByPrepTimeMinutesLessThanEqual(35);

        assertEquals(1, quickPrepRecipes.size());
        assertEquals("Veggie Pasta", quickPrepRecipes.get(0).getName());
        assertEquals(2, allPrepRecipes.size());
    }

    @Test
    void shouldFindRecipesByCookTimeLimit() {
        entityManager.persistAndFlush(testRecipe);
        entityManager.persistAndFlush(vegetarianRecipe);

        List<Recipe> quickCookRecipes = recipeRepository.findByCookTimeMinutesLessThanEqual(25);
        List<Recipe> allCookRecipes = recipeRepository.findByCookTimeMinutesLessThanEqual(50);

        assertEquals(1, quickCookRecipes.size());
        assertEquals("Veggie Pasta", quickCookRecipes.get(0).getName());
        assertEquals(2, allCookRecipes.size());
    }

    @Test
    void shouldFindRecipesByNameContainingIgnoreCase() {
        entityManager.persistAndFlush(testRecipe);
        entityManager.persistAndFlush(vegetarianRecipe);

        List<Recipe> recipesWithTest = recipeRepository.findByNameContainingIgnoreCase("test");
        List<Recipe> recipesWithPasta = recipeRepository.findByNameContainingIgnoreCase("PASTA");

        assertEquals(1, recipesWithTest.size());
        assertEquals("Test Recipe", recipesWithTest.get(0).getName());
        assertEquals(1, recipesWithPasta.size());
        assertEquals("Veggie Pasta", recipesWithPasta.get(0).getName());
    }
}

