package ca.rjdsilv.buyrecipes.controller.api;

import ca.rjdsilv.buyrecipes.component.ProductComponent;
import ca.rjdsilv.buyrecipes.controller.model.ProductDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductComponent productComponent;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto createProduct(@RequestBody @Valid ProductDto productDto) {
        return productComponent.createOrUpdateProduct(productDto);
    }

    @GetMapping
    public Page<ProductDto> fetchProducts(@PageableDefault(sort = {"name"}) Pageable pageable) {
        return productComponent.fetchProductsPaged(pageable);
    }

    @GetMapping(path = "/{id}")
    public ProductDto fetchProductById(@PathVariable Integer id) {
        return productComponent.fetchProductById(id);
    }
}
