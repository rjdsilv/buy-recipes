package ca.rjdsilv.buyrecipes.controller.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CartDto {
    private Integer id;

    private int totalInCents;

    private List<CartItemDto> cartItems;
}
