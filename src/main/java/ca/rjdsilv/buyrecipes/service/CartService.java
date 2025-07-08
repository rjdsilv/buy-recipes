package ca.rjdsilv.buyrecipes.service;

import ca.rjdsilv.buyrecipes.model.Cart;
import ca.rjdsilv.buyrecipes.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;

    public Cart saveOrUpdateCart(Cart cart) {
        return cartRepository.saveAndFlush(cart);
    }

    public Cart fetchCartById(int cartId) {
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart with cartId=%s not found".formatted(cartId)));
    }
}
