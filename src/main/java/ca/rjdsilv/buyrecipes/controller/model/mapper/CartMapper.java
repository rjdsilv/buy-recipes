package ca.rjdsilv.buyrecipes.controller.model.mapper;

import ca.rjdsilv.buyrecipes.controller.model.CartDto;
import ca.rjdsilv.buyrecipes.model.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CartMapper {
    private final CartItemMapper cartItemMapper;

    public CartDto toDto(Cart cart) {
        if (cart == null) {
            return null;
        }

        return CartDto.builder()
                .id(cart.getId())
                .totalInCents(cart.getTotalInCents())
                .cartItems(cart.getCartItems()
                        .stream()
                        .map(cartItemMapper::toDto)
                        .toList())
                .build();
    }
}
