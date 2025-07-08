package ca.rjdsilv.buyrecipes.service;

import ca.rjdsilv.buyrecipes.factory.RecipeTestFactory;
import ca.rjdsilv.buyrecipes.repository.RecipeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RecipeServiceTest {
    @Mock
    private RecipeRepository recipeRepository;
    @InjectMocks
    private RecipeService recipeService;

    @Test
    void shouldFetchRecipes() {
        // given
        var pageable = PageRequest.of(0, 10);

        // when
        recipeService.fetchRecipes(pageable);

        // then
        verify(recipeRepository).findAll(pageable);
    }

    @Test
    void shouldFetchRecipeById() {
        // given
        var recipe = RecipeTestFactory.buildRecipe();
        var recipeId = recipe.getId();
        when(recipeRepository.findById(recipeId))
                .thenReturn(Optional.of(recipe));

        // when
        var foundRecipe = recipeService.fetchRecipeById(recipeId);

        // then
        assertThat(foundRecipe).isEqualTo(recipe);
    }
    
    @Test
    void shouldThrowExceptionWhenRecipeNotFound() {
        // given
        var recipeId = 15;
        when(recipeRepository.findById(recipeId))
                .thenReturn(Optional.empty());

        // when
        // then
        var exception = assertThrows(ResponseStatusException.class, () -> recipeService.fetchRecipeById(recipeId));
        assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(exception.getReason()).isEqualTo("Recipe with recipeId=%s not found".formatted(recipeId));
    }
}
