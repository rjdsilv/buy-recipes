package ca.rjdsilv.buyrecipes.controller.model.mapper;

import ca.rjdsilv.buyrecipes.controller.model.ProductDto;
import ca.rjdsilv.buyrecipes.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public ProductDto toDto(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .priceInCents(product.getPriceInCents())
                .build();
    }

    public Product toEntity(ProductDto productDto) {
        return Product.builder()
                .name(productDto.getName())
                .priceInCents(productDto.getPriceInCents())
                .build();
    }

    public Product update(Product product, ProductDto productDto) {
        product.setName(productDto.getName());
        product.setPriceInCents(productDto.getPriceInCents());

        return product;
    }
}
