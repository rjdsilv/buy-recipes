package ca.rjdsilv.buyrecipes.factory;

import ca.rjdsilv.buyrecipes.model.Product;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class ProductTestFactory {
    public static Product buildProduct() {
        return Product.builder()
                .id(11)
                .name("Test Product 11")
                .priceInCents(1000)
                .build();
    }
}
