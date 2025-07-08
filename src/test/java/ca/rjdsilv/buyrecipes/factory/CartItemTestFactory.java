package ca.rjdsilv.buyrecipes.factory;

import ca.rjdsilv.buyrecipes.model.CartItem;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class CartItemTestFactory {
    public static CartItem buildCartItem() {
        return CartItem.builder()
                .id(20)
                .product(ProductTestFactory.buildProduct())
                .build();
    }
}
