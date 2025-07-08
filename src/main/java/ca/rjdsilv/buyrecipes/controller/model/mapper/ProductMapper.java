package ca.rjdsilv.buyrecipes.controller.model.mapper;

import ca.rjdsilv.buyrecipes.controller.model.ProductDto;
import ca.rjdsilv.buyrecipes.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public ProductDto toDto(Product product) {
        if (product == null) {
            return null;
        }

        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .priceInCents(product.getPriceInCents())
                .build();
    }
}
