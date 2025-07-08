package ca.rjdsilv.buyrecipes.controller.model.mapper;

import ca.rjdsilv.buyrecipes.controller.model.CartItemDto;
import ca.rjdsilv.buyrecipes.model.CartItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CartItemMapper {
    private final ProductMapper productMapper;

    public CartItemDto toDto(CartItem cartItem) {
        if (cartItem == null) {
            return null;
        }

        return CartItemDto.builder()
                .id(cartItem.getId())
                .product(productMapper.toDto(cartItem.getProduct()))
                .build();
    }
}
