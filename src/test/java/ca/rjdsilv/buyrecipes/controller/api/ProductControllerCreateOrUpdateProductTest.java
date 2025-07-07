package ca.rjdsilv.buyrecipes.controller.api;

import ca.rjdsilv.buyrecipes.TestUtils;
import ca.rjdsilv.buyrecipes.controller.model.ProductDto;
import ca.rjdsilv.buyrecipes.factory.ProductTestFactory;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.MediaType;

import java.util.stream.Stream;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProductControllerCreateOrUpdateProductTest extends BaseControllerTest {
    @Test
    @SneakyThrows
    void shouldCreateProductWhenIdIsNull() {
        // given
        var productDto = ProductTestFactory.buildProductDto();
        productDto.setId(null);

        // when
        mockMvc.perform(post("/products")
                        .content(TestUtils.toJson(productDto))
                        .contentType(MediaType.APPLICATION_JSON))
                // then
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.name").value(productDto.getName()))
                .andExpect(jsonPath("$.priceInCents").value(productDto.getPriceInCents()));
    }

    @Test
    @SneakyThrows
    void shouldCreateProductWhenIdIsNotNullButProductDoesntExist() {
        // given
        var productDto = ProductTestFactory.buildProductDto();

        // when
        mockMvc.perform(post("/products")
                        .content(TestUtils.toJson(productDto))
                        .contentType(MediaType.APPLICATION_JSON))
                // then
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.name").value(productDto.getName()))
                .andExpect(jsonPath("$.priceInCents").value(productDto.getPriceInCents()));
    }

    @Test
    @SneakyThrows
    void shouldUpdateProduct() {
        // given
        var product = ProductTestFactory.buildProduct();
        product.setId(null);
        var savedProduct = productRepository.save(product);
        var productDto = ProductTestFactory.buildProductDto();
        productDto.setId(savedProduct.getId());

        // when
        mockMvc.perform(post("/products")
                        .content(TestUtils.toJson(productDto))
                        .contentType(MediaType.APPLICATION_JSON))
                // then
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(productDto.getId()))
                .andExpect(jsonPath("$.name").value(productDto.getName()))
                .andExpect(jsonPath("$.priceInCents").value(productDto.getPriceInCents()));
    }

    @SneakyThrows
    @ParameterizedTest
    @MethodSource("invalidPayloads")
    void shouldReturnBadRequestIfPayloadIsInvalid(ProductDto productDto) {
        // when
        mockMvc.perform(post("/products")
                        .content(TestUtils.toJson(productDto))
                        .contentType(MediaType.APPLICATION_JSON))
                // then
                .andExpect(status().isBadRequest());
    }

    private static Stream<ProductDto> invalidPayloads() {
        return Stream.of(
                ProductDto.builder().priceInCents(100).build(),
                ProductDto.builder().priceInCents(100).name("").build()
        );
    }
}
