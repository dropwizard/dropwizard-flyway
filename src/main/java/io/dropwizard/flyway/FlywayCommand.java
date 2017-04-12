package io.dropwizard.flyway;

/**
 * An enumeration of {@code Flyway} commands.
 * @see <a href="http://flywaydb.org/documentation/">Flyway Documentation</a>
 */
public enum FlywayCommand {
    MIGRATE("Migrates the database."),
    REPAIR("Repairs the metadata table."),
    CLEAN("Drops all objects in the configured schemas."),
    INFO("Prints the details and status information about all the migrations."),
    VALIDATE("Validates the applied migrations against the ones available on the classpath."),
    BASELINE("Creates and initializes the metadata table (existing database).");

    private final String description;

    FlywayCommand(String description) {
        this.description = description;
    }

    @Override
    public final String toString() {
        return name().toLowerCase();
    }

    public final String description() {
        return description;
    }
}
