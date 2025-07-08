package ca.rjdsilv.buyrecipes.factory;

import ca.rjdsilv.buyrecipes.model.Cart;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class CartTestFactory {
    public static Cart buildCart() {
        return Cart.builder()
                .id(32)
                .totalInCents(3894)
                .cartItems(List.of(CartItemTestFactory.buildCartItem()))
                .build();
    }
}
