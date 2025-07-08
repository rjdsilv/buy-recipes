package ca.rjdsilv.buyrecipes;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class BaseIntegrationTest {
    @Autowired
    protected DatabaseManager databaseManager;

    @BeforeEach
    void setUp() {
        databaseManager.createTestData();
    }

    @AfterEach
    void tearDown() {
        databaseManager.cleanDb();
    }
}
