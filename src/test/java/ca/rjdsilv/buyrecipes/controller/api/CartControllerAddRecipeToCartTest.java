package ca.rjdsilv.buyrecipes.controller.api;

import ca.rjdsilv.buyrecipes.TestUtils;
import ca.rjdsilv.buyrecipes.controller.model.CartAddRecipeRequestDto;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CartControllerAddRecipeToCartTest extends BaseCartControllerTest {
    @Test
    @SneakyThrows
    void shouldAddRecipeToCart() {
        // given
        var createdCartDto = createEmptyCart();
        var recipeId = findRecipeWithName("2 Products Recipe").getId();
        var addRecipeRequestDto = new CartAddRecipeRequestDto(recipeId);

        // when
        mockMvc.perform(post("/carts/%s/add_recipe".formatted(createdCartDto.getId()))
                        .content(TestUtils.toJson(addRecipeRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(createdCartDto.getId()))
                .andExpect(jsonPath("$.totalInCents").value(918))
                .andExpect(jsonPath("$.cartItems", hasSize(2)));
    }

    @Test
    @SneakyThrows
    void shouldReturnNotFoundWhenCartDoesntExist() {
        // given
        var recipeId = findRecipeWithName("2 Products Recipe").getId();
        var addRecipeRequestDto = new CartAddRecipeRequestDto(recipeId);

        // when
        mockMvc.perform(post("/carts/%s/add_recipe".formatted(10000001))
                        .content(TestUtils.toJson(addRecipeRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                // then
                .andExpect(status().isNotFound());
    }

    @Test
    @SneakyThrows
    void shouldReturnNotFoundWhenRecipeDoesntExist() {
        // given
        var createdCartDto = createEmptyCart();
        var addRecipeRequestDto = new CartAddRecipeRequestDto(5000001);

        // when
        mockMvc.perform(post("/carts/%s/add_recipe".formatted(createdCartDto.getId()))
                        .content(TestUtils.toJson(addRecipeRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                // then
                .andExpect(status().isNotFound());
    }
}
