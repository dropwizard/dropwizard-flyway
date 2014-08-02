package io.dropwizard.flyway.cli;

import io.dropwizard.flyway.FlywayConfiguration;
import io.dropwizard.Configuration;
import io.dropwizard.db.DatabaseConfiguration;
import net.sourceforge.argparse4j.inf.Namespace;
import net.sourceforge.argparse4j.inf.Subparser;
import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.sourceforge.argparse4j.impl.Arguments.storeTrue;

public class DbMigrateCommand<T extends Configuration> extends AbstractFlywayCommand<T> {

    private static final Logger LOG = LoggerFactory.getLogger(DbMigrateCommand.class);
    private static final String OUT_OF_ORDER = "outOfOrder";
    private static final String VALIDATE_ON_MIGRATE = "validateOnMigrate";
    private static final String CLEAN_ON_VALIDATION_ERROR = "cleanOnValidationError";
    private static final String INIT_ON_MIGRATE = "initOnMigrate";

    public DbMigrateCommand(final DatabaseConfiguration<T> databaseConfiguration,
                            final FlywayConfiguration<T> flywayConfiguration,
                            final Class<T> configurationClass) {
        super("migrate", "Migrates the database.",
                databaseConfiguration, flywayConfiguration, configurationClass);
    }

    @Override
    public void configure(Subparser subparser) {
        super.configure(subparser);

        subparser.addArgument("--" + OUT_OF_ORDER)
                .action(storeTrue())
                .setDefault(Boolean.FALSE)
                .dest(OUT_OF_ORDER)
                .help("Allows migrations to be run \"out of order\". " +
                        "If you already have versions 1 and 3 applied, and now a version 2 is found, it will be applied too instead of being ignored.");

        subparser.addArgument("--" + VALIDATE_ON_MIGRATE)
                .action(storeTrue())
                .setDefault(Boolean.FALSE)
                .dest(VALIDATE_ON_MIGRATE)
                .help("Whether to automatically call validate or not when running migrate. " +
                        "For each sql migration a CRC32 checksum is calculated when the sql script is executed. " +
                        "The validate mechanism checks if the sql migration in the classpath still has the same checksum as the sql migration already executed in the database.");

        subparser.addArgument("--" + CLEAN_ON_VALIDATION_ERROR)
                .action(storeTrue())
                .setDefault(Boolean.FALSE)
                .dest(CLEAN_ON_VALIDATION_ERROR)
                .help("Whether to automatically call clean or not when a validation error occurs. " +
                        "This is exclusively intended as a convenience for development. " +
                        "Even tough we strongly recommend not to change migration scripts once they have been checked into SCM and run, this provides a way of dealing with this case in a smooth manner. " +
                        "The database will be wiped clean automatically, ensuring that the next migration will bring you back to the state checked into SCM. " +
                        "Warning! Do not enable in production !");

        subparser.addArgument("--" + INIT_ON_MIGRATE)
                .action(storeTrue())
                .setDefault(Boolean.FALSE)
                .dest(INIT_ON_MIGRATE)
                .help("Whether to automatically call init when migrate is executed against a non-empty schema with no metadata table. " +
                        "This schema will then be initialized with the initVersion before executing the migrations. " +
                        "Only migrations above initVersion will then be applied. " +
                        "This is useful for initial Flyway production deployments on projects with an existing DB. " +
                        "Be careful when enabling this as it removes the safety net that ensures Flyway does not migrate the wrong database in case of a configuration mistake!");
    }

    @Override
    public void run(final Namespace namespace, final Flyway flyway) throws Exception {
        flyway.setOutOfOrder(namespace.getBoolean(OUT_OF_ORDER));
        flyway.setValidateOnMigrate(namespace.getBoolean(VALIDATE_ON_MIGRATE));
        flyway.setCleanOnValidationError(namespace.getBoolean(CLEAN_ON_VALIDATION_ERROR));
        flyway.setInitOnMigrate(namespace.getBoolean(INIT_ON_MIGRATE));

        final int successfulMigrations = flyway.migrate();
        LOG.debug("{} successful migrations applied", successfulMigrations);
    }
}