package ca.rjdsilv.buyrecipes.service;

import ca.rjdsilv.buyrecipes.factory.CartTestFactory;
import ca.rjdsilv.buyrecipes.repository.CartRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {
    @Mock
    private CartRepository cartRepository;
    @InjectMocks
    private CartService cartService;

    @Test
    void shouldSaveOrUpdateCart() {
        // given
        var cart = CartTestFactory.buildCart();

        // when
        cartService.saveOrUpdateCart(cart);

        // then
        verify(cartRepository).saveAndFlush(cart);
    }

    @Test
    void shouldFetchCartById() {
        // given
        var cart =  CartTestFactory.buildCart();
        var cartId = cart.getId();
        when(cartRepository.findById(cartId))
                .thenReturn(Optional.of(cart));

        // when
        var foundCart =  cartService.fetchCartById(cartId);

        // then
        assertThat(foundCart).isEqualTo(cart);
    }

    @Test
    void shouldThrowExceptionWhenCartNotFound() {
        // given
        var cartId = 15;
        when(cartRepository.findById(cartId))
                .thenReturn(Optional.empty());

        // when
        // then
        var exception = assertThrows(ResponseStatusException.class, () -> cartService.fetchCartById(cartId));
        assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(exception.getReason()).isEqualTo("Cart with cartId=%s not found".formatted(cartId));
    }
}
