package io.dropwizard.flyway.cli;

import org.flywaydb.core.api.output.MigrateResult;
import org.flywaydb.core.internal.license.VersionPrinter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DbMigrateCommandTest extends AbstractCommandTest {
    private MigrateResult migrateResult;

    @BeforeEach
    void setUpFlyway() {
        migrateResult = new MigrateResult(VersionPrinter.getVersion(), "db", "schema");
        migrateResult.initialSchemaVersion = "initial";
        migrateResult.targetSchemaVersion = "target";
        migrateResult.migrationsExecuted = 23;

        when(mockFlyway.migrate()).thenReturn(migrateResult);
    }

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
    public void testDefaultArgumentsWithWarnings() {
        migrateResult.warnings.add("warning1");
        migrateResult.warnings.add("warning2");

        cli.run("db", "migrate");

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
