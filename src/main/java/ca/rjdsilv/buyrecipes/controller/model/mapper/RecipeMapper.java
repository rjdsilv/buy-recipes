package ca.rjdsilv.buyrecipes.controller.model.mapper;

import ca.rjdsilv.buyrecipes.controller.model.RecipeDto;
import ca.rjdsilv.buyrecipes.model.Recipe;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RecipeMapper {
    private final ProductMapper productMapper;

    public RecipeDto toDto(Recipe recipe) {
        var productDtoList = recipe.getProducts()
                .stream()
                .map(recipeProduct -> {
                    var productDto = productMapper.toDto(recipeProduct.getProduct());
                    productDto.setQuantity(recipeProduct.getQuantity());
                    productDto.setUnit(recipeProduct.getUnit());

                    return productDto;
                })
                .toList();

        return RecipeDto.builder()
                .id(recipe.getId())
                .name(recipe.getName())
                .products(productDtoList)
                .build();
    }
}
