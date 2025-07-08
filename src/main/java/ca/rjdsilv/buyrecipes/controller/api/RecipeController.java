package ca.rjdsilv.buyrecipes.controller.api;

import ca.rjdsilv.buyrecipes.component.RecipeComponent;
import ca.rjdsilv.buyrecipes.controller.model.RecipeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/recipes")
@RequiredArgsConstructor
public class RecipeController {
    private final RecipeComponent recipeComponent;

    @GetMapping
    public Page<RecipeDto> listRecipes(Pageable pageable) {
        return recipeComponent.fetchRecipes(pageable);
    }
}
