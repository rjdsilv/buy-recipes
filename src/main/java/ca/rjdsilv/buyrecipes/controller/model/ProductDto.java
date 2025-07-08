package ca.rjdsilv.buyrecipes.controller.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDto {
    private Integer id;

    @NotEmpty
    private String name;

    private int priceInCents;

    private BigDecimal quantity;

    private String unit;
}
