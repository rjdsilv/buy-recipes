package ca.rjdsilv.buyrecipes.factory;

import ca.rjdsilv.buyrecipes.model.Product;
import ca.rjdsilv.buyrecipes.model.Recipe;
import ca.rjdsilv.buyrecipes.model.RecipeProduct;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class RecipeTestFactory {
    public static Recipe buildRecipe() {
        return Recipe.builder()
                .id(15)
                .name("Test Recipe")
                .products(List.of(
                        RecipeProduct.builder()
                                .product(Product.builder()
                                        .id(1)
                                        .name("Test Product 1")
                                        .priceInCents(2079)
                                        .build())
                                .quantity(new BigDecimal("0.75"))
                                .unit("liters")
                                .build(),
                        RecipeProduct.builder()
                                .product(Product.builder()
                                        .id(2)
                                        .name("Test Product 2")
                                        .priceInCents(109)
                                        .build())
                                .quantity(new BigDecimal("500"))
                                .unit("grams")
                                .build()))
                .build();
    }
}
