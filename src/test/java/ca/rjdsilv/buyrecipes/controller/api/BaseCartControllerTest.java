package ca.rjdsilv.buyrecipes.controller.api;

import ca.rjdsilv.buyrecipes.TestUtils;
import ca.rjdsilv.buyrecipes.controller.model.CartDto;
import ca.rjdsilv.buyrecipes.model.Recipe;
import lombok.SneakyThrows;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

abstract class BaseCartControllerTest extends BaseControllerTest {
    @SneakyThrows
    protected CartDto createEmptyCart() {
        var jsonResponse = mockMvc.perform(post("/carts"))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        return TestUtils.fromJson(jsonResponse, CartDto.class);
    }

    protected Recipe findRecipeWithName(String recipeName) {
        return recipeRepository.findAll()
                .stream()
                .filter(recipe -> recipeName.equals(recipe.getName()))
                .findFirst()
                .orElseThrow();
    }
}
