package ca.rjdsilv.buyrecipes.model;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "recipe_products")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecipeProduct {
    @EmbeddedId
    private RecipeProductId id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("recipeId")
    private Recipe recipe;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productId")
    private Product product;

    @NotNull
    @Column(name = "quantity", nullable = false)
    private BigDecimal quantity;

    @Column(name = "unit")
    private String unit;
}
