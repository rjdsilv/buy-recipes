package ca.rjdsilv.buyrecipes.service;

import ca.rjdsilv.buyrecipes.model.Product;
import ca.rjdsilv.buyrecipes.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public Page<Product> fetchProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Optional<Product> fetchProductById(int id) {
        return  productRepository.findById(id);
    }
}
