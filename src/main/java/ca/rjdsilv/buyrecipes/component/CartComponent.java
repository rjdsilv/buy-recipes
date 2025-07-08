package ca.rjdsilv.buyrecipes.component;

import ca.rjdsilv.buyrecipes.controller.model.CartAddRecipeRequestDto;
import ca.rjdsilv.buyrecipes.controller.model.CartDto;
import ca.rjdsilv.buyrecipes.controller.model.mapper.CartMapper;
import ca.rjdsilv.buyrecipes.model.Cart;
import ca.rjdsilv.buyrecipes.model.CartItem;
import ca.rjdsilv.buyrecipes.model.Product;
import ca.rjdsilv.buyrecipes.model.RecipeProduct;
import ca.rjdsilv.buyrecipes.service.CartService;
import ca.rjdsilv.buyrecipes.service.RecipeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CartComponent {
    private final CartService cartService;
    private final CartMapper cartMapper;
    private final RecipeService recipeService;

    public CartDto fetchCartById(int cartId) {
        return cartMapper.toDto(cartService.fetchCartById(cartId));
    }

    @Transactional
    public CartDto createEmptyCart() {
        var emptyCart = cartService.saveOrUpdateCart(Cart.builder().build());

        return cartMapper.toDto(emptyCart);
    }

    @Transactional
    public CartDto addRecipeToCart(int cartId, CartAddRecipeRequestDto addRecipeRequestDto) {
        var cart = cartService.fetchCartById(cartId);
        var recipe = recipeService.fetchRecipeById(addRecipeRequestDto.getRecipeId());

        // Updating Cart before saving it
        addItemsToCart(cart, recipe.getProducts());
        cart.setTotalInCents(calculateCartTotal(cart));
        var updatedCart = cartService.saveOrUpdateCart(cart);

        return cartMapper.toDto(updatedCart);
    }

    @Transactional
    public CartDto removeRecipeFromCart(int cartId, int recipeId) {
        var cart = cartService.fetchCartById(cartId);
        var recipe = recipeService.fetchRecipeById(recipeId);

        // Updating the Cart before saving it.
        removeItemsFromCart(cart, recipe.getProducts());
        cart.setTotalInCents(calculateCartTotal(cart));
        var updatedCart = cartService.saveOrUpdateCart(cart);

        return cartMapper.toDto(updatedCart);
    }

    private int calculateCartTotal(Cart cart) {
        return cart.getCartItems()
                .stream()
                .map(CartItem::getProduct)
                .map(Product::getPriceInCents)
                .reduce(0, Integer::sum);
    }

    private void addItemsToCart(Cart cart, List<RecipeProduct> recipeProducts) {
        recipeProducts.forEach(recipeProduct -> {
            var cartItem = CartItem.builder()
                    .product(recipeProduct.getProduct())
                    .build();
            cart.addCartItem(cartItem);
        });
    }

    private void removeItemsFromCart(Cart cart, List<RecipeProduct> recipeProducts) {
        for (var recipeProduct : recipeProducts) {
            var cartItem = findCartItemMatchingProduct(cart, recipeProduct.getProduct());

            if (cartItem.isPresent()) {
                cart.removeCartItem(cartItem.get());
            }
            else {
                var message = "Trying to remove recipe with recipeId=%s from cart, but the productId=%s was not found in the cart"
                        .formatted(recipeProduct.getRecipe().getId(), recipeProduct.getProduct().getId());
                throw new ResponseStatusException(HttpStatus.CONFLICT, message);
            }
        }
    }

    private Optional<CartItem> findCartItemMatchingProduct(Cart cart, Product product) {
        return cart.getCartItems()
                .stream()
                .filter(cartItem -> product.getName().equals(cartItem.getProduct().getName()))
                .findFirst();
    }
}
