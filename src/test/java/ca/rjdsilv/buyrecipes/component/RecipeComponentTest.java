package ca.rjdsilv.buyrecipes.component;

import ca.rjdsilv.buyrecipes.controller.model.mapper.ProductMapper;
import ca.rjdsilv.buyrecipes.controller.model.mapper.RecipeMapper;
import ca.rjdsilv.buyrecipes.factory.RecipeTestFactory;
import ca.rjdsilv.buyrecipes.service.RecipeService;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RecipeComponentTest {
    private final RecipeService recipeService = mock(RecipeService.class);
    private final ProductMapper productMapper = new ProductMapper();
    private final RecipeMapper recipeMapper = new RecipeMapper(productMapper);
    private final RecipeComponent recipeComponent = new RecipeComponent(recipeService, recipeMapper);

    @Test
    void shouldReturnRecipes() {
        // given
        var pageable = PageRequest.of(0, 10);
        var recipe = RecipeTestFactory.buildRecipe();
        var recipes = List.of(recipe);
        when(recipeService.fetchRecipes(pageable))
                .thenReturn(new PageImpl<>(recipes, pageable, 1));

        // when
        var recipeDtoPage = recipeComponent.fetchRecipes(pageable);

        // then
        assertAll(
                () -> assertThat(recipeDtoPage).isNotNull(),
                () -> assertThat(recipeDtoPage.getTotalPages()).isEqualTo(1),
                () -> assertThat(recipeDtoPage.getTotalElements()).isEqualTo(1),
                () -> assertThat(recipeDtoPage.getContent().getFirst().getId()).isEqualTo(recipe.getId()),
                () -> assertThat(recipeDtoPage.getContent().getFirst().getName()).isEqualTo(recipe.getName()),
                () -> assertThat(recipeDtoPage.getContent().getFirst().getProducts().getFirst().getId()).isEqualTo(recipe.getProducts().getFirst().getProduct().getId()),
                () -> assertThat(recipeDtoPage.getContent().getFirst().getProducts().getFirst().getName()).isEqualTo(recipe.getProducts().getFirst().getProduct().getName()),
                () -> assertThat(recipeDtoPage.getContent().getFirst().getProducts().getFirst().getPriceInCents()).isEqualTo(recipe.getProducts().getFirst().getProduct().getPriceInCents()),
                () -> assertThat(recipeDtoPage.getContent().getFirst().getProducts().getFirst().getQuantity()).isEqualTo(recipe.getProducts().getFirst().getQuantity()),
                () -> assertThat(recipeDtoPage.getContent().getFirst().getProducts().getFirst().getUnit()).isEqualTo(recipe.getProducts().getFirst().getUnit()),
                () -> assertThat(recipeDtoPage.getContent().getFirst().getProducts().getLast().getId()).isEqualTo(recipe.getProducts().getLast().getProduct().getId()),
                () -> assertThat(recipeDtoPage.getContent().getFirst().getProducts().getLast().getName()).isEqualTo(recipe.getProducts().getLast().getProduct().getName()),
                () -> assertThat(recipeDtoPage.getContent().getFirst().getProducts().getLast().getPriceInCents()).isEqualTo(recipe.getProducts().getLast().getProduct().getPriceInCents()),
                () -> assertThat(recipeDtoPage.getContent().getFirst().getProducts().getLast().getQuantity()).isEqualTo(recipe.getProducts().getLast().getQuantity()),
                () -> assertThat(recipeDtoPage.getContent().getFirst().getProducts().getLast().getUnit()).isEqualTo(recipe.getProducts().getLast().getUnit())
        );
    }
}
