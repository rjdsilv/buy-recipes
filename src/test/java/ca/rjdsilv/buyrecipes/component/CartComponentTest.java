package ca.rjdsilv.buyrecipes.component;


import ca.rjdsilv.buyrecipes.controller.model.mapper.CartItemMapper;
import ca.rjdsilv.buyrecipes.controller.model.mapper.CartMapper;
import ca.rjdsilv.buyrecipes.controller.model.mapper.ProductMapper;
import ca.rjdsilv.buyrecipes.factory.CartAddRecipeRequestDtoTestFactory;
import ca.rjdsilv.buyrecipes.factory.CartTestFactory;
import ca.rjdsilv.buyrecipes.factory.RecipeTestFactory;
import ca.rjdsilv.buyrecipes.model.Cart;
import ca.rjdsilv.buyrecipes.model.RecipeProduct;
import ca.rjdsilv.buyrecipes.service.CartService;
import ca.rjdsilv.buyrecipes.service.RecipeService;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CartComponentTest {
    private final CartService cartService = mock(CartService.class);
    private final RecipeService recipeService = mock(RecipeService.class);
    private final ProductMapper productMapper = new ProductMapper();
    private final CartItemMapper cartItemMapper = new CartItemMapper(productMapper);
    private final CartMapper cartMapper = new CartMapper(cartItemMapper);
    private final CartComponent cartComponent = new CartComponent(cartService, cartMapper, recipeService);

    @Test
    void shouldFetchCartById() {
        // given
        var cart = CartTestFactory.buildCart();
        var cartId = cart.getId();
        when(cartService.fetchCartById(cartId))
                .thenReturn(cart);

        // when
        var cartDto = cartComponent.fetchCartById(cartId);

        // then
        assertThat(cartDto)
                .usingRecursiveComparison()
                .isEqualTo(cart);
    }

    @Test
    void shouldThrowExceptionWhenCartServiceThrowsExceptionWhenFindingCartById() {
        // given
        var cartId = 15;
        when(cartService.fetchCartById(cartId))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart with cartId=%s not found".formatted(cartId)));

        // when
        // then
        var exception = assertThrows(ResponseStatusException.class, () -> cartComponent.fetchCartById(cartId));
        assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(exception.getReason()).isEqualTo("Cart with cartId=15 not found");
    }

    @Test
    void shouldCreateEmptyCart() {
        // given
        var emptyCartWithId = CartTestFactory.buildEmptyCart();
        emptyCartWithId.setId(100);
        when(cartService.saveOrUpdateCart(any(Cart.class)))
                .thenReturn(emptyCartWithId);

        // when
        var cartDto = cartComponent.createEmptyCart();

        // then
        assertAll(
                () -> assertThat(cartDto).isNotNull(),
                () -> assertThat(cartDto.getId()).isEqualTo(emptyCartWithId.getId()),
                () -> assertThat(cartDto.getTotalInCents()).isZero(),
                () -> assertThat(cartDto.getCartItems()).isEmpty()
        );
    }

    @Test
    void shouldAddRecipeToCart() {
        // given
        var cartId = 37;
        var cart = CartTestFactory.buildCart();
        cart.setId(cartId);
        var recipe = RecipeTestFactory.buildRecipe();
        var recipeId = recipe.getId();
        var addRecipeRequestDto = CartAddRecipeRequestDtoTestFactory.buildCartAddRecipeRequestDto(recipeId);
        when(cartService.fetchCartById(cartId))
                .thenReturn(cart);
        when(recipeService.fetchRecipeById(recipeId))
                .thenReturn(recipe);

        // when
        cartComponent.addRecipeToCart(cartId, addRecipeRequestDto);

        // then
        assertAll(
                () -> verify(cartService).fetchCartById(cartId),
                () -> verify(recipeService).fetchRecipeById(recipeId),
                () -> verify(cartService).saveOrUpdateCart(cart)
        );
    }

    @Test
    void shouldThrowExceptionWhenCartServiceThrowsExceptionWhenAddingRecipeToCart() {
        // given
        var cartId = 37;
        var cart = CartTestFactory.buildCart();
        cart.setId(cartId);
        var recipeId = 23;
        var addRecipeRequestDto = CartAddRecipeRequestDtoTestFactory.buildCartAddRecipeRequestDto(recipeId);
        when(cartService.fetchCartById(cartId))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

        // when
        // then
        var exception = assertThrows(ResponseStatusException.class, () -> cartComponent.addRecipeToCart(cartId, addRecipeRequestDto));
        assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldThrowExceptionWhenRecipeServiceThrowsExceptionWhenAddingRecipeToCart() {
        // given
        var cartId = 37;
        var cart = CartTestFactory.buildCart();
        cart.setId(cartId);
        var recipe = RecipeTestFactory.buildRecipe();
        var recipeId = recipe.getId();
        var addRecipeRequestDto = CartAddRecipeRequestDtoTestFactory.buildCartAddRecipeRequestDto(recipeId);
        when(cartService.fetchCartById(cartId))
                .thenReturn(cart);
        when(recipeService.fetchRecipeById(recipeId))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

        // when
        // then
        var exception = assertThrows(ResponseStatusException.class, () -> cartComponent.addRecipeToCart(cartId, addRecipeRequestDto));
        assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldRemoveRecipeFromCart() {
        // given
        var cartId = 37;
        var cart = CartTestFactory.buildCart();
        cart.setId(cartId);
        var recipe = RecipeTestFactory.buildRecipe();
        var recipeId = recipe.getId();
        recipe.setProducts(List.of(RecipeProduct.builder()
                .product(cart.getCartItems().getFirst().getProduct())
                .build()));
        when(cartService.fetchCartById(cartId))
                .thenReturn(cart);
        when(recipeService.fetchRecipeById(recipeId))
                .thenReturn(recipe);

        // when
        cartComponent.removeRecipeFromCart(cartId, recipeId);

        // then
        assertAll(
                () -> verify(cartService).fetchCartById(cartId),
                () -> verify(recipeService).fetchRecipeById(recipeId),
                () -> verify(cartService).saveOrUpdateCart(cart)
        );
    }

    @Test
    void shouldThrowExceptionWhenCartServiceThrowsExceptionWhenRemovingRecipeFromCart() {
        // given
        var cartId = 37;
        var cart = CartTestFactory.buildCart();
        cart.setId(cartId);
        var recipeId = 23;
        when(cartService.fetchCartById(cartId))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

        // when
        // then
        var exception = assertThrows(ResponseStatusException.class, () -> cartComponent.removeRecipeFromCart(cartId, recipeId));
        assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldThrowExceptionWhenRecipeServiceThrowsExceptionWhenRemovingRecipeFromCart() {
        // given
        var cartId = 37;
        var cart = CartTestFactory.buildCart();
        cart.setId(cartId);
        var recipe = RecipeTestFactory.buildRecipe();
        var recipeId = recipe.getId();
        when(cartService.fetchCartById(cartId))
                .thenReturn(cart);
        when(recipeService.fetchRecipeById(recipeId))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

        // when
        // then
        var exception = assertThrows(ResponseStatusException.class, () -> cartComponent.removeRecipeFromCart(cartId, recipeId));
        assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
