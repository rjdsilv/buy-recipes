package ca.rjdsilv.buyrecipes.controller.api;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CartControllerFetchCartTest extends BaseCartControllerTest {
    @Test
    @SneakyThrows
    void shouldFetchCart() {
        // given
        var createdCartDto = createEmptyCart();

        // when
        mockMvc.perform(get("/carts/%s".formatted(createdCartDto.getId())))
                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(createdCartDto.getId()))
                .andExpect(jsonPath("$.totalInCents").value(0))
                .andExpect(jsonPath("$.cartItems", hasSize(0)));
    }

    @Test
    @SneakyThrows
    void shouldReturnNotFoundWhenCartDoesNotExist() {
        // given
        // when
        mockMvc.perform(get("/carts/1001"))
                // then
                .andExpect(status().isNotFound());

    }
}
