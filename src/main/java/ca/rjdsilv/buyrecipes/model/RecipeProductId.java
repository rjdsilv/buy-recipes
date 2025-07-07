package ca.rjdsilv.buyrecipes.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class RecipeProductId implements Serializable {
    @Column(name = "recipe_id", nullable = false)
    private int recipeId;

    @Column(name = "product_id", nullable = false)
    private int productId;
}
