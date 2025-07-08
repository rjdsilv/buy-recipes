package ca.rjdsilv.buyrecipes.controller.api;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RecipeControllerListRecipesTest extends BaseControllerTest {
    @Test
    @SneakyThrows
    void shouldListRecipes() {
        // given
        // when
        mockMvc.perform(get("/recipes?page=0&size=1&sort=name,asc"))
                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].id", notNullValue()))
                .andExpect(jsonPath("$.content[0].name").value("2 Products Recipe"))
                .andExpect(jsonPath("$.content[0].products[0].name").value("Product 1"))
                .andExpect(jsonPath("$.content[0].products[0].priceInCents").value(359))
                .andExpect(jsonPath("$.content[0].products[0].quantity").value(new BigDecimal("100.0").toString()))
                .andExpect(jsonPath("$.content[0].products[0].unit").value("grams"))
                .andExpect(jsonPath("$.content[0].products[1].name").value("Product 2"))
                .andExpect(jsonPath("$.content[0].products[1].priceInCents").value(559))
                .andExpect(jsonPath("$.content[0].products[1].quantity").value(new BigDecimal("1.0").toString()))
                .andExpect(jsonPath("$.content[0].products[1].unit").value("liters"))
        ;
    }
}
