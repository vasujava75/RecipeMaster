package com.api.recipe.recipemasterapi.repository;

import com.api.recipe.recipemasterapi.domain.Recipe;
import com.api.recipe.recipemasterapi.utils.RecipeSearchCriteria;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JpaRecipeSearchEngineTest {

    private EntityManager entityManager;
    private CriteriaBuilder criteriaBuilder;
    private CriteriaQuery<Recipe> criteriaQuery;
    private Root<Recipe> root;
    private TypedQuery<Recipe> typedQuery;
    private JpaRecipeSearchEngine searchEngine;

    @BeforeEach
    void setUp() {
        entityManager = mock(EntityManager.class);
        criteriaBuilder = mock(CriteriaBuilder.class);
        criteriaQuery = mock(CriteriaQuery.class);
        root = mock(Root.class);
        typedQuery = mock(TypedQuery.class);

        searchEngine = new JpaRecipeSearchEngine();
        // Inject mock EntityManager
        java.lang.reflect.Field emField;
        try {
            emField = JpaRecipeSearchEngine.class.getDeclaredField("entityManager");
            emField.setAccessible(true);
            emField.set(searchEngine, entityManager);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(Recipe.class)).thenReturn(criteriaQuery);
        when(criteriaQuery.from(Recipe.class)).thenReturn(root);
        when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
    }

    @Test
    void returnsEmptyResultWhenNoRecipesMatch() {
        RecipeSearchCriteria criteria = new RecipeSearchCriteria();
        when(typedQuery.getResultList()).thenReturn(Collections.emptyList());

        List<Recipe> result = searchEngine.search(criteria);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void returnsMatchingRecipesForName() {
        RecipeSearchCriteria criteria = new RecipeSearchCriteria();
        criteria.setName("Soup");

        Predicate predicate = mock(Predicate.class);
        Path<Object> namePath = mock(Path.class);
        Expression<String> lowerExpression = mock(Expression.class);

        when(root.get("name")).thenReturn(namePath);
        when(criteriaBuilder.lower(any(Expression.class))).thenReturn(lowerExpression);
        when(criteriaBuilder.like(eq(lowerExpression), anyString())).thenReturn(predicate);
        when(criteriaQuery.where(any(Predicate[].class))).thenReturn(criteriaQuery);

        Recipe recipe = new Recipe();
        when(typedQuery.getResultList()).thenReturn(List.of(recipe));

        List<Recipe> result = searchEngine.search(criteria);

        assertEquals(1, result.size());
        assertSame(recipe, result.get(0));
    }

    @Test
    void returnsMatchingRecipesForMultipleCriteria() {
        RecipeSearchCriteria criteria = new RecipeSearchCriteria();
        criteria.setName("Pie");
        criteria.setVegetarian(true);
        criteria.setServings(4);

        Predicate namePredicate = mock(Predicate.class);
        Predicate vegPredicate = mock(Predicate.class);
        Predicate servingsPredicate = mock(Predicate.class);

        Path<Object> namePath = mock(Path.class);
        Expression<String> lowerExpression = mock(Expression.class);
        Path<Object> vegPath = mock(Path.class);
        Path<Object> servingsPath = mock(Path.class);

        when(root.get("name")).thenReturn(namePath);
        when(criteriaBuilder.lower(any(Expression.class))).thenReturn(lowerExpression);
        when(criteriaBuilder.like(eq(lowerExpression), anyString())).thenReturn(namePredicate);

        when(root.get("vegetarian")).thenReturn(vegPath);
        when(criteriaBuilder.equal(eq(vegPath), eq(true))).thenReturn(vegPredicate);

        when(root.get("servings")).thenReturn(servingsPath);
        when(criteriaBuilder.equal(eq(servingsPath), eq(4))).thenReturn(servingsPredicate);

        when(criteriaQuery.where(any(Predicate[].class))).thenReturn(criteriaQuery);

        Recipe recipe = new Recipe();
        when(typedQuery.getResultList()).thenReturn(List.of(recipe));

        List<Recipe> result = searchEngine.search(criteria);

        assertEquals(1, result.size());
    }

    @Test
    void handlesNullCriteriaGracefully() {
        when(typedQuery.getResultList()).thenReturn(Collections.emptyList());
        List<Recipe> result = searchEngine.search(new RecipeSearchCriteria());
        assertNotNull(result);
    }
}
