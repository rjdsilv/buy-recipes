package ca.rjdsilv.buyrecipes.controller.api;

import ca.rjdsilv.buyrecipes.TestUtils;
import ca.rjdsilv.buyrecipes.controller.model.CartAddRecipeRequestDto;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CartControllerRemoveRecipeFromCartTest extends BaseCartControllerTest {
    @Test
    @SneakyThrows
    void shouldRemoveRecipeFromCart() {
        // given
        var createdCartDto = createEmptyCart();
        var recipeId = findRecipeWithName("2 Products Recipe").getId();
        var addRecipeRequestDto = new CartAddRecipeRequestDto(recipeId);
        mockMvc.perform(post("/carts/%s/add_recipe".formatted(createdCartDto.getId()))
                        .content(TestUtils.toJson(addRecipeRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(createdCartDto.getId()))
                .andExpect(jsonPath("$.totalInCents").value(918))
                .andExpect(jsonPath("$.cartItems", hasSize(2)));

        // when
        mockMvc.perform(delete("/carts/%s/recipes/%s".formatted(createdCartDto.getId(), recipeId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(createdCartDto.getId()))
                .andExpect(jsonPath("$.totalInCents").value(0))
                .andExpect(jsonPath("$.cartItems", hasSize(0)));
    }

    @Test
    @SneakyThrows
    void shouldReturnNotFoundWhenCartDoesntExist() {
        // given
        var recipeId = findRecipeWithName("2 Products Recipe").getId();

        // when
        mockMvc.perform(delete("/carts/%s/recipes/%s".formatted(10000001, recipeId)))
                // then
                .andExpect(status().isNotFound());
    }

    @Test
    @SneakyThrows
    void shouldReturnNotFoundWhenRecipeDoesntExist() {
        // given
        var createdCartDto = createEmptyCart();

        // when
        mockMvc.perform(delete("/carts/%s/recipes/%s".formatted(createdCartDto.getId(), 5000001)))
                // then
                .andExpect(status().isNotFound());
    }

    @Test
    @SneakyThrows
    void shouldReturnConflictWhenRemovingARecipeThatDoesntExist() {
        // given
        var createdCartDto = createEmptyCart();
        var recipeId = findRecipeWithName("2 Products Recipe").getId();
        var unexistingRecipeId = findRecipeWithName("5 Products Recipe").getId();
        var addRecipeRequestDto = new CartAddRecipeRequestDto(recipeId);
        mockMvc.perform(post("/carts/%s/add_recipe".formatted(createdCartDto.getId()))
                        .content(TestUtils.toJson(addRecipeRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(createdCartDto.getId()))
                .andExpect(jsonPath("$.totalInCents").value(918))
                .andExpect(jsonPath("$.cartItems", hasSize(2)));

        // when
        mockMvc.perform(delete("/carts/%s/recipes/%s".formatted(createdCartDto.getId(), unexistingRecipeId)))
                .andExpect(status().isConflict());
    }
}
