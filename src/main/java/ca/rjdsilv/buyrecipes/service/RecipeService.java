package ca.rjdsilv.buyrecipes.service;

import ca.rjdsilv.buyrecipes.model.Recipe;
import ca.rjdsilv.buyrecipes.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecipeService {
    private final RecipeRepository recipeRepository;

    public Page<Recipe> fetchRecipes(Pageable pageable) {
        return recipeRepository.findAll(pageable);
    }
}
