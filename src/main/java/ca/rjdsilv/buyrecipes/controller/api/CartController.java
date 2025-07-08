package ca.rjdsilv.buyrecipes.controller.api;

import ca.rjdsilv.buyrecipes.component.CartComponent;
import ca.rjdsilv.buyrecipes.controller.model.CartAddRecipeRequestDto;
import ca.rjdsilv.buyrecipes.controller.model.CartDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
public class CartController {
    private final CartComponent cartComponent;

    /**
     * Helper endpoint to create an empty cart and make testing easier.
     *
     * @return The empty cart created.
     */
    @PostMapping
    public CartDto createEmptyCart() {
        return cartComponent.createEmptyCart();
    }

    @GetMapping(path = "{cartId}")
    public CartDto fetchCart(@PathVariable int cartId) {
        return cartComponent.fetchCartById(cartId);
    }

    @PostMapping(path = "/{cartId}/add_recipe")
    public CartDto addRecipeToCart(@PathVariable int cartId,
                                   @Valid @RequestBody CartAddRecipeRequestDto addRecipeRequestDto) {
        return cartComponent.addRecipeToCart(cartId, addRecipeRequestDto);
    }

    @DeleteMapping(path = "{cartId}/recipes/{recipeId}")
    public CartDto removeRecipeFromCart(@PathVariable int cartId, @PathVariable int recipeId) {
        return cartComponent.removeRecipeFromCart(cartId, recipeId);
    }
}
