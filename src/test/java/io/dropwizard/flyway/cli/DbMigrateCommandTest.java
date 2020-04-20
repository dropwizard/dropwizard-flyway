package io.dropwizard.flyway.cli;

import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class DbMigrateCommandTest extends AbstractCommandTest {

    @Test
    public void testDefaultArguments() {
        cli.run("db", "migrate");

        verify(mockFlywayFactory, never()).setBaselineOnMigrate(anyBoolean());
        verify(mockFlywayFactory, never()).setOutOfOrder(anyBoolean());
        verify(mockFlywayFactory, never()).setValidateOnMigrate(anyBoolean());
        verify(mockFlywayFactory, never()).setCleanOnValidationError(anyBoolean());
        verify(mockFlyway).migrate();
    }

    @Test
    public void testInitOnMigrate() {
        cli.run("db", "migrate", "--initOnMigrate");

        verify(mockFlywayFactory).setBaselineOnMigrate(true);
        verify(mockFlyway).migrate();
    }

    @Test
    public void testValidateOMigrate() {
        cli.run("db", "migrate", "--validateOnMigrate");

        verify(mockFlywayFactory).setValidateOnMigrate(true);
        verify(mockFlyway).migrate();
    }

    @Test
    public void testOutOfOrder() {
        cli.run("db", "migrate", "--outOfOrder");

        verify(mockFlywayFactory).setOutOfOrder(true);
        verify(mockFlyway).migrate();
    }

    @Test
    public void testCleanOnValidationError() {
        cli.run("db", "migrate", "--cleanOnValidationError");

        verify(mockFlywayFactory).setCleanOnValidationError(true);
        verify(mockFlyway).migrate();
    }
}
