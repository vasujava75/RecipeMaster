package com.api.recipe.recipemasterapi;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Import(TestcontainersConfiguration.class)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class DatabaseIntegrationTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void contextLoads() {
        // Test that Spring context loads successfully
        assertThat(jdbcTemplate).isNotNull();
    }

    @Test
    void databaseConnectionWorks() {
        // Test basic database connectivity
        Integer result = jdbcTemplate.queryForObject("SELECT 1", Integer.class);
        assertThat(result).isEqualTo(1);
    }

    @Test
    void flywayMigrationsApplied() {
        // Verify that tables were created by Flyway migrations
        Integer recipeTableCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM information_schema.tables WHERE table_name = 'recipes'",
                Integer.class
        );
        assertThat(recipeTableCount).isEqualTo(1);

        Integer ingredientsTableCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM information_schema.tables WHERE table_name = 'ingredients'",
                Integer.class
        );
        assertThat(ingredientsTableCount).isEqualTo(1);
    }

    /*@Test
    void canInsertAndRetrieveRecipe() {
        // Test basic CRUD operations
        jdbcTemplate.update(
                "INSERT INTO recipes (name, instructions, servings, vegetarian) VALUES (?, ?, ?, ?)",
                "Test Recipe", "Test instructions", 4, false
        );

        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM recipes WHERE name = ?",
                Integer.class,
                "Test Recipe"
        );
        assertThat(count).isEqualTo(1);
    }*/



}
