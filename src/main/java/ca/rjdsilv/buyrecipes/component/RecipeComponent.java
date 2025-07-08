package ca.rjdsilv.buyrecipes.component;

import ca.rjdsilv.buyrecipes.controller.model.RecipeDto;
import ca.rjdsilv.buyrecipes.controller.model.mapper.RecipeMapper;
import ca.rjdsilv.buyrecipes.service.RecipeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Log
@Component
@RequiredArgsConstructor
public class RecipeComponent {
    private final RecipeService recipeService;
    private final RecipeMapper recipeMapper;

    public Page<RecipeDto> fetchRecipes(Pageable pageable) {
        log.info("Fetching recipes from database with pageNumber=%s and pageSize=%s".formatted(pageable.getPageNumber(), pageable.getPageSize()));
        return recipeService.fetchRecipes(pageable)
                .map(recipeMapper::toDto);
    }
}
