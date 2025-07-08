package ca.rjdsilv.buyrecipes.factory;

import ca.rjdsilv.buyrecipes.controller.model.CartAddRecipeRequestDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class CartAddRecipeRequestDtoTestFactory {
    public static CartAddRecipeRequestDto buildCartAddRecipeRequestDto(int recipeId) {
        return CartAddRecipeRequestDto.builder()
                .recipeId(recipeId)
                .build();
    }
}
