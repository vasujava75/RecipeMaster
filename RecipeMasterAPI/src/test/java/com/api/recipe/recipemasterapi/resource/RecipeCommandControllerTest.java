package com.api.recipe.recipemasterapi.resource;

import com.api.recipe.recipemasterapi.TestcontainersConfiguration;
import com.api.recipe.recipemasterapi.dto.RecipeDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Import(TestcontainersConfiguration.class)
@Transactional
class RecipeCommandControllerTest {

    @Autowired
    private MockMvc mockMvc;


    private ObjectMapper objectMapper= new ObjectMapper();

    private RecipeDto createSampleRecipeDto() {
        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setName("Test Recipe");
        recipeDto.setDescription("A test recipe description");
        recipeDto.setInstructions("Mix ingredients and cook");
        recipeDto.setServings(4);
        recipeDto.setVegetarian(true);
        recipeDto.setCookTimeMinutes(30);
        return recipeDto;
    }

    @Test
    void shouldCreateRecipeSuccessfully() throws Exception {
        RecipeDto recipeDto = createSampleRecipeDto();

        mockMvc.perform(post("/api/v1/recipes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(recipeDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.name").value("Test Recipe"))
                .andExpect(jsonPath("$.description").value("A test recipe description"));
    }

    @Test
    void shouldReturnBadRequestForInvalidRecipe() throws Exception {
        RecipeDto invalidRecipe = new RecipeDto(); // Missing required fields

        mockMvc.perform(post("/api/v1/recipes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRecipe)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldUpdateExistingRecipe() throws Exception {
        // First create a recipe
        RecipeDto originalRecipe = createSampleRecipeDto();
        String createResponse = mockMvc.perform(post("/api/v1/recipes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(originalRecipe)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        RecipeDto createdRecipe = objectMapper.readValue(createResponse, RecipeDto.class);
        Long recipeId = createdRecipe.getId();

        // Update the recipe
        RecipeDto updatedRecipe = createSampleRecipeDto();
        updatedRecipe.setName("Updated Recipe Name");
        updatedRecipe.setDescription("Updated description");

        mockMvc.perform(put("/api/v1/recipes/{id}", recipeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedRecipe)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(recipeId))
                .andExpect(jsonPath("$.name").value("Updated Recipe Name"))
                .andExpect(jsonPath("$.description").value("Updated description"));
    }

    @Test
    void shouldReturnNotFoundWhenUpdatingNonExistentRecipe() throws Exception {
        RecipeDto recipeDto = createSampleRecipeDto();
        Long nonExistentId = 99999L;

        mockMvc.perform(put("/api/v1/recipes/{id}", nonExistentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(recipeDto)))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteExistingRecipe() throws Exception {
        // First create a recipe
        RecipeDto recipeDto = createSampleRecipeDto();
        String createResponse = mockMvc.perform(post("/api/v1/recipes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(recipeDto)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        RecipeDto createdRecipe = objectMapper.readValue(createResponse, RecipeDto.class);
        Long recipeId = createdRecipe.getId();

        // Delete the recipe
        mockMvc.perform(delete("/api/v1/recipes/{id}", recipeId))
                .andExpect(status().isNoContent());

        // Verify it's deleted by trying to update it
        mockMvc.perform(put("/api/v1/recipes/{id}", recipeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(recipeDto)))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnNotFoundWhenDeletingNonExistentRecipe() throws Exception {
        Long nonExistentId = 99999L;

        mockMvc.perform(delete("/api/v1/recipes/{id}", nonExistentId))
                .andExpect(status().isNotFound());
    }
}
