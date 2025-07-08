package ca.rjdsilv.buyrecipes.controller.model.mapper;

import ca.rjdsilv.buyrecipes.factory.ProductTestFactory;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProductMapperTest {
    private final ProductMapper productMapper = new ProductMapper();

    @Test
    void shouldMapToDtoWhenNull() {
        // given
        // when
        // then
        assertThat(productMapper.toDto(null)).isNull();
    }

    @Test
    void shouldMapToDtoWhenNotNull() {
        // given
        var product = ProductTestFactory.buildProduct();

        // when
        var productDto = productMapper.toDto(product);

        // then
        assertThat(productDto)
                .usingRecursiveComparison()
                .isEqualTo(product);
    }
}
