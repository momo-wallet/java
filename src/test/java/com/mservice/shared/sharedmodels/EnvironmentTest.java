package com.mservice.shared.sharedmodels;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EnvironmentTest {

    @Test
    @DisplayName("Development Environment")
    public void devEnv() {
        Environment env = Environment.selectEnv("dev");
        assertEquals(env.getTarget(), "development", "Should be dev environment");
        assertEquals("https://test-payment.momo.vn", env.getMomoEndpoint(), "Incorrect URL for Dev Env");
    }

    @Test
    @DisplayName("Production Environment")
    public void prodEnv() {
        Environment env = Environment.selectEnv("prod");
        assertEquals("production", env.getTarget(), "Should be prod environment");
        assertEquals("https://payment.momo.vn", env.getMomoEndpoint(), "Incorrect URL for Prod Env");

    }

    @ParameterizedTest
    @ValueSource(strings = {"", "radar", "tee hee", "123456", "  ", "/t"})
    @DisplayName("Exception Environment")
    void exceptionEnvTesting() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> Environment.selectEnv(""));
        assertEquals("MoMo doesnt provide other environment: dev and prod", exception.getMessage());
    }

}