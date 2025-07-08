package ca.rjdsilv.buyrecipes.service;

import ca.rjdsilv.buyrecipes.model.Recipe;
import ca.rjdsilv.buyrecipes.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class RecipeService {
    private final RecipeRepository recipeRepository;

    public Page<Recipe> fetchRecipes(Pageable pageable) {
        return recipeRepository.findAll(pageable);
    }

    public Recipe fetchRecipeById(int recipeId) {
        return recipeRepository.findById(recipeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe with recipeId=%s not found".formatted(recipeId)));
    }
}
