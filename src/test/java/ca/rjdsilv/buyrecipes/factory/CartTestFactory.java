package ca.rjdsilv.buyrecipes.factory;

import ca.rjdsilv.buyrecipes.model.Cart;
import ca.rjdsilv.buyrecipes.model.CartItem;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class CartTestFactory {
    public static Cart buildCart() {
        var cartItemList = new ArrayList<CartItem>();
        cartItemList.add(CartItemTestFactory.buildCartItem());
        return Cart.builder()
                .id(32)
                .totalInCents(3894)
                .cartItems(cartItemList)
                .build();
    }

    public static Cart buildEmptyCart() {
        return Cart.builder().build();
    }
}
