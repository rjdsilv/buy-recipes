package ca.rjdsilv.buyrecipes.controller.api;

import ca.rjdsilv.buyrecipes.BaseIntegrationTest;
import ca.rjdsilv.buyrecipes.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
public abstract class BaseControllerTest extends BaseIntegrationTest {
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected RecipeRepository recipeRepository;
}
