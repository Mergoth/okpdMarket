package ru.okpdmarket;

import org.junit.ClassRule;
import org.testcontainers.containers.FixedHostPortGenericContainer;
import org.testcontainers.containers.GenericContainer;

public abstract class IntegrationTest {

    // Set up a redis container
    @ClassRule
    public static GenericContainer mongo =
            new FixedHostPortGenericContainer("mongo:3")
                    .withFixedExposedPort(27010, 27017);
}
