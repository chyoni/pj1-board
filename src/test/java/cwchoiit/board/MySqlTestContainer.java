package cwchoiit.board;

import org.testcontainers.containers.MySQLContainer;

@SuppressWarnings("resource")
public class MySqlTestContainer {
    private static final MySQLContainer<?> container;

    static {
        container = new MySQLContainer<>("mysql:8.0.38")
                .withDatabaseName("test_db")
                .withUsername("test")
                .withPassword("test");
        container.start();
    }

    public static MySQLContainer<?> getInstance() {
        return container;
    }

}
