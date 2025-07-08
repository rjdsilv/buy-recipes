package ca.rjdsilv.buyrecipes.controller.model.mapper;

import ca.rjdsilv.buyrecipes.factory.CartItemTestFactory;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CartItemMapperTest {
    private final ProductMapper productMapper = new ProductMapper();
    private final CartItemMapper cartItemMapper = new CartItemMapper(productMapper);

    @Test
    void shouldMapToDtoWhenNull() {
        // given
        // when
        // then
        assertThat(cartItemMapper.toDto(null)).isNull();
    }

    @Test
    void shouldMapToDtoWhenNotNull() {
        // given
        var cartItem = CartItemTestFactory.buildCartItem();

        // when
        var cartItemDto = cartItemMapper.toDto(cartItem);

        // then
        assertThat(cartItemDto)
                .usingRecursiveComparison()
                .isEqualTo(cartItem);
    }
}
