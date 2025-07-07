package ca.rjdsilv.buyrecipes.component;

import ca.rjdsilv.buyrecipes.controller.model.ProductDto;
import ca.rjdsilv.buyrecipes.controller.model.mapper.ProductMapper;
import ca.rjdsilv.buyrecipes.model.Product;
import ca.rjdsilv.buyrecipes.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Log
@Component
@RequiredArgsConstructor
public class ProductComponent {
    private final ProductService productService;
    private final ProductMapper productMapper;

    @Transactional
    public ProductDto createOrUpdateProduct(ProductDto productDto) {
        log.info("Creating or updating a product with name=%s".formatted(productDto.getName()));

        Product product;
        if (productDto.getId() == null) {
            product = productMapper.toEntity(productDto);
        }
        else {
            product = productService.fetchProductById(productDto.getId())
                    .map(p -> productMapper.update(p, productDto))
                    .orElseGet(() -> productMapper.toEntity(productDto));
        }

        return productMapper.toDto(productService.saveProduct(product));
    }

    public Page<ProductDto> fetchProductsPaged(Pageable pageable) {
        log.info("Fetching products with pageNumber=%s and pageSize=%s".formatted(pageable.getPageNumber(), pageable.getPageSize()));

        return productService.fetchProducts(pageable)
                .map(productMapper::toDto);
    }

    public ProductDto fetchProductById(int id) {
        log.info("Fetching product with id=%d".formatted(id));

        var fetchedProduct = productService.fetchProductById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with id=%s not found".formatted(id)));
        return productMapper.toDto(fetchedProduct);
    }
}
