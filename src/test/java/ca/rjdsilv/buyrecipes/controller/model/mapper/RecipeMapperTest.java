package ca.rjdsilv.buyrecipes.controller.model.mapper;

import ca.rjdsilv.buyrecipes.factory.RecipeTestFactory;
import ca.rjdsilv.buyrecipes.model.RecipeProduct;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class RecipeMapperTest {
    private final ProductMapper productMapper = new ProductMapper();
    private final RecipeMapper recipeMapper = new RecipeMapper(productMapper);

    @Test
    void shouldMapToDtoWhenNull() {
        // given
        // when
        // then
        assertThat(recipeMapper.toDto(null)).isNull();
    }

    @Test
    void shouldMapToDtoWhenNotNull() {
        // given
        var recipe = RecipeTestFactory.buildRecipe();

        // when
        var recipeDto =  recipeMapper.toDto(recipe);

        // then
        assertAll(
                () -> assertThat(recipeDto.getId()).isEqualTo(recipe.getId()),
                () -> assertThat(recipeDto.getName()).isEqualTo(recipe.getName()),
                () -> assertThat(recipeDto.getProducts()).hasSize(recipe.getProducts().size()),
                () -> assertThat(recipeDto.getProducts())
                        .usingRecursiveComparison()
                        .isEqualTo(recipe.getProducts().stream().map(RecipeProduct::getProduct).toList())
        );
    }
}
