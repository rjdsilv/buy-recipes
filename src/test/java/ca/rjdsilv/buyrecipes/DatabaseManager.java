package ca.rjdsilv.buyrecipes;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

@Component
public class DatabaseManager {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void cleanDb() {
        entityManager.createNativeQuery("TRUNCATE recipe_products CASCADE").executeUpdate();
        entityManager.createNativeQuery("TRUNCATE carts CASCADE").executeUpdate();
        entityManager.createNativeQuery("TRUNCATE cart_items CASCADE").executeUpdate();
        entityManager.createNativeQuery("TRUNCATE recipes CASCADE").executeUpdate();
        entityManager.createNativeQuery("TRUNCATE products CASCADE").executeUpdate();
    }
}
