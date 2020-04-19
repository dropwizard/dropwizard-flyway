package io.dropwizard.flyway.cli;

import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class DbValidateCommandTest extends AbstractCommandTest {

    @Test
    public void testDefaultArguments() {
        cli.run("db", "validate");

        verify(mockFlywayFactory, never()).setOutOfOrder(anyBoolean());
        verify(mockFlywayFactory, never()).setCleanOnValidationError(anyBoolean());
        verify(mockFlyway).validate();
    }

    @Test
    public void testOutOfOrder() {
        cli.run("db", "validate", "--outOfOrder");

        verify(mockFlywayFactory).setOutOfOrder(true);
        verify(mockFlyway).validate();
    }

    @Test
    public void testCleanOnValidationError() {
        cli.run("db", "validate", "--cleanOnValidationError");

        verify(mockFlywayFactory).setCleanOnValidationError(true);
        verify(mockFlyway).validate();
    }
}
