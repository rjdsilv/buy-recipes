package ca.rjdsilv.buyrecipes.controller.model.mapper;

import ca.rjdsilv.buyrecipes.factory.CartTestFactory;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CartMapperTest {
    private final ProductMapper productMapper = new ProductMapper();
    private final CartItemMapper cartItemMapper = new CartItemMapper(productMapper);
    private final CartMapper cartMapper = new CartMapper(cartItemMapper);

    @Test
    void shouldMapToDtoWhenNull() {
        // given
        // when
        // then
        assertThat(cartMapper.toDto(null)).isNull();
    }

    @Test
    void shouldMapToDtoWhenNotNull() {
        // given
        var cart = CartTestFactory.buildCart();

        // when
        var cartDto =  cartMapper.toDto(cart);

        // then
        assertThat(cartDto)
                .usingRecursiveComparison()
                .isEqualTo(cart);
    }
}
