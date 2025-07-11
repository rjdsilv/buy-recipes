package ca.rjdsilv.buyrecipes.repository;

import ca.rjdsilv.buyrecipes.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
}
