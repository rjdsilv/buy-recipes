package ca.rjdsilv.buyrecipes.factory;

import ca.rjdsilv.buyrecipes.controller.model.ProductDto;
import ca.rjdsilv.buyrecipes.model.Product;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class ProductTestFactory {
    public static ProductDto buildProductDto() {
        return ProductDto.builder()
                .id(10)
                .name("Product 10")
                .priceInCents(1000)
                .build();
    }

    public static Product buildProduct() {
        return Product.builder()
                .id(11)
                .name("Product 11")
                .priceInCents(2000)
                .build();
    }

    public static Product buildProductFromDto(ProductDto productDto) {
        return Product.builder()
                .id(productDto.getId())
                .name(productDto.getName())
                .priceInCents(productDto.getPriceInCents())
                .build();
    }
}
