package ca.rjdsilv.buyrecipes.component;

import ca.rjdsilv.buyrecipes.controller.model.mapper.ProductMapper;
import ca.rjdsilv.buyrecipes.factory.ProductTestFactory;
import ca.rjdsilv.buyrecipes.model.Product;
import ca.rjdsilv.buyrecipes.service.ProductService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductComponentTest {
    @Mock
    private ProductService productService;
    @Spy
    private ProductMapper productMapper;
    @InjectMocks
    private ProductComponent productComponent;

    @Test
    void shouldCreateOrUpdateProduct() {
        // given
        var productDto = ProductTestFactory.buildProductDto();
        var product = ProductTestFactory.buildProductFromDto(productDto);
        when(productService.saveProduct(any(Product.class)))
                .thenReturn(product);

        // when
        var createdProductDto = productComponent.createOrUpdateProduct(productDto);

        // then
        assertAll(
                () -> assertThat(createdProductDto)
                        .usingRecursiveComparison()
                        .isEqualTo(productDto),
                () -> verify(productService).saveProduct(argThat(productArg ->
                        Objects.isNull(productArg.getId())
                                && productArg.getName().equals(product.getName())
                                && productArg.getPriceInCents() == product.getPriceInCents())));

    }

    @Test
    void shouldFetchProductsPaged() {
        // given
        var pageable = PageRequest.of(0, 10);
        var productPage = new PageImpl<>(List.of(ProductTestFactory.buildProduct()), pageable, 1);
        when(productService.fetchProducts(pageable))
                .thenReturn(productPage);

        // when
        var productDtoPage = productComponent.fetchProductsPaged(pageable);

        // then
        assertAll(
                () -> assertThat(productDtoPage).isNotNull(),
                () -> assertThat(productDtoPage.getTotalPages()).isEqualTo(1),
                () -> assertThat(productDtoPage.getTotalElements()).isEqualTo(1L),
                () -> assertThat(productDtoPage.getContent())
                        .usingRecursiveComparison()
                        .isEqualTo(productPage.getContent())
        );
    }

    @Test
    void shouldFetchProductById() {
        // given
        var product = ProductTestFactory.buildProduct();
        var productId = product.getId();
        when(productService.fetchProductById(productId))
                .thenReturn(Optional.of(product));

        // when
        var productDto = productComponent.fetchProductById(productId);

        // then
        assertThat(productDto)
                .usingRecursiveComparison()
                .isEqualTo(product);
    }

    @Test
    @SneakyThrows
    void shouldThrowNotFoundWhenProductWithGivenIdDoesntExist() {
        // given
        var productId = 5;
        when(productService.fetchProductById(productId))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with id=%s not found".formatted(productId)));

        // when
        var exception = assertThrows(ResponseStatusException.class, () -> productComponent.fetchProductById(productId));

        assertAll(
                () -> assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND),
                () -> assertThat(exception.getReason()).isEqualTo("Product with id=5 not found")
        );
    }
}
