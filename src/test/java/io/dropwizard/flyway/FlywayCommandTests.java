package io.dropwizard.flyway;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class FlywayCommandTests {

    private static final String MIGRATE_STRING = "migrate";
    private static final String MIGRATE_DESCRIPTION = "Migrates the database.";

    private static final String REPAIR_STRING = "repair";
    private static final String REPAIR_DESCRIPTION = "Repairs the metadata table.";

    private static final String CLEAN_STRING = "clean";
    private static final String CLEAN_DESCRIPTION = "Drops all objects in the configured schemas.";

    private static final String INFO_STRING = "info";
    private static final String INFO_DESCRIPTION = "Prints the details and status information about all the migrations.";

    private static final String VALIDATE_STRING = "validate";
    private static final String VALIDATE_DESCRIPTION = "Validates the applied migrations against the ones available on the classpath.";

    private static final String BASELINE_STRING = "baseline";
    private static final String BASELINE_DESCRIPTION = "Creates and initializes the metadata table (existing database).";

    @Test
    public final void testMigrateValues() {
        assertThat(FlywayCommand.MIGRATE.toString(), is(equalTo(MIGRATE_STRING)));
        assertThat(FlywayCommand.MIGRATE.description(), is(equalTo(MIGRATE_DESCRIPTION)));
    }

    @Test
    public final void testRepairValues() {
        assertThat(FlywayCommand.REPAIR.toString(), is(equalTo(REPAIR_STRING)));
        assertThat(FlywayCommand.REPAIR.description(), is(equalTo(REPAIR_DESCRIPTION)));
    }

    @Test
    public final void testCleanValues() {
        assertThat(FlywayCommand.CLEAN.toString(), is(equalTo(CLEAN_STRING)));
        assertThat(FlywayCommand.CLEAN.description(), is(equalTo(CLEAN_DESCRIPTION)));
    }

    @Test
    public final void testInfoValues() {
        assertThat(FlywayCommand.INFO.toString(), is(equalTo(INFO_STRING)));
        assertThat(FlywayCommand.INFO.description(), is(equalTo(INFO_DESCRIPTION)));
    }

    @Test
    public final void testValidateValues() {
        assertThat(FlywayCommand.VALIDATE.toString(), is(equalTo(VALIDATE_STRING)));
        assertThat(FlywayCommand.VALIDATE.description(), is(equalTo(VALIDATE_DESCRIPTION)));
    }

    @Test
    public final void testBaselineValues() {
        assertThat(FlywayCommand.BASELINE.toString(), is(equalTo(BASELINE_STRING)));
        assertThat(FlywayCommand.BASELINE.description(), is(equalTo(BASELINE_DESCRIPTION)));
    }

}
