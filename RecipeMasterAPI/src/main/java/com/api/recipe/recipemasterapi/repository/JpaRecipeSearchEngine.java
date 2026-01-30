package com.api.recipe.recipemasterapi.repository;

import com.api.recipe.recipemasterapi.domain.Recipe;
import com.api.recipe.recipemasterapi.utils.RecipeSearchCriteria;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JpaRecipeSearchEngine implements RecipeSearchEngine {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Recipe> search(RecipeSearchCriteria criteria) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Recipe> query = cb.createQuery(Recipe.class);
        Root<Recipe> recipe = query.from(Recipe.class);

        List<Predicate> predicates = new ArrayList<>();

        // Filter by name
        if (criteria.getName() != null && !criteria.getName().trim().isEmpty()) {
            predicates.add(cb.like(cb.lower(recipe.get("name")),
                    "%" + criteria.getName().toLowerCase() + "%"));
        }

        // Filter by vegetarian status
        if (criteria.getVegetarian() != null) {
            predicates.add(cb.equal(recipe.get("vegetarian"), criteria.getVegetarian()));
        }

        // Filter by servings
        if (criteria.getServings() != null) {
            predicates.add(cb.equal(recipe.get("servings"), criteria.getServings()));
        }

        // Filter by difficulty
        if (criteria.getDifficulty() != null) {
            predicates.add(cb.equal(recipe.get("difficulty"), criteria.getDifficulty()));
        }

        // Filter by ingredients (if specified)
        if (criteria.getIngredients() != null && !criteria.getIngredients().isEmpty()) {
            Join<Object, Object> ingredientJoin = recipe.join("ingredients");
            predicates.add(ingredientJoin.get("name").in(criteria.getIngredients()));
        }

        // Filter by max prep time
        if (criteria.getMaxPrepTime() != null) {
            predicates.add(cb.lessThanOrEqualTo(recipe.get("prepTimeMinutes"), criteria.getMaxPrepTime()));
        }

        // Filter by max cook time
        if (criteria.getMaxCookTime() != null) {
            predicates.add(cb.lessThanOrEqualTo(recipe.get("cookTimeMinutes"), criteria.getMaxCookTime()));
        }

        query.where(predicates.toArray(new Predicate[0]));

        TypedQuery<Recipe> typedQuery = entityManager.createQuery(query);
        return typedQuery.getResultList();
    }
}
