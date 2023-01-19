package io.dropwizard.flyway.cli;

import io.dropwizard.core.Configuration;
import io.dropwizard.db.DatabaseConfiguration;
import io.dropwizard.flyway.FlywayConfiguration;
import io.dropwizard.flyway.FlywayFactory;
import net.sourceforge.argparse4j.impl.Arguments;
import net.sourceforge.argparse4j.inf.Namespace;
import net.sourceforge.argparse4j.inf.Subparser;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.output.MigrateResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
                .dest(OUT_OF_ORDER)
                .action(Arguments.storeConst()).setConst(Boolean.TRUE)
                .help("Allows migrations to be run \"out of order\". " +
                        "If you already have versions 1 and 3 applied, and now a version 2 is found, it will be applied too instead of being ignored.");

        subparser.addArgument("--" + VALIDATE_ON_MIGRATE)
                .dest(VALIDATE_ON_MIGRATE)
                .action(Arguments.storeConst()).setConst(Boolean.TRUE)
                .help("Whether to automatically call validate or not when running migrate. " +
                        "For each sql migration a CRC32 checksum is calculated when the sql script is executed. " +
                        "The validate mechanism checks if the sql migration in the classpath still has the same checksum as the sql migration already executed in the database.");

        subparser.addArgument("--" + CLEAN_ON_VALIDATION_ERROR)
                .dest(CLEAN_ON_VALIDATION_ERROR)
                .action(Arguments.storeConst()).setConst(Boolean.TRUE)
                .help("Whether to automatically call clean or not when a validation error occurs. " +
                        "This is exclusively intended as a convenience for development. " +
                        "Even tough we strongly recommend not to change migration scripts once they have been checked into SCM and run, this provides a way of dealing with this case in a smooth manner. " +
                        "The database will be wiped clean automatically, ensuring that the next migration will bring you back to the state checked into SCM. " +
                        "Warning! Do not enable in production !");

        subparser.addArgument("--" + INIT_ON_MIGRATE)
                .dest(INIT_ON_MIGRATE)
                .action(Arguments.storeConst()).setConst(Boolean.TRUE)
                .help("Whether to automatically call init when migrate is executed against a non-empty schema with no metadata table. " +
                        "This schema will then be initialized with the initVersion before executing the migrations. " +
                        "Only migrations above initVersion will then be applied. " +
                        "This is useful for initial Flyway production deployments on projects with an existing DB. " +
                        "Be careful when enabling this as it removes the safety net that ensures Flyway does not migrate the wrong database in case of a configuration mistake!");
    }

    @Override
    protected void setAdditionalOptions(FlywayFactory flywayFactory, Namespace namespace) {
        final Boolean outOfOrder = namespace.getBoolean(OUT_OF_ORDER);
        final Boolean validateOnMigrate = namespace.getBoolean(VALIDATE_ON_MIGRATE);
        final Boolean cleanOnValidationError = namespace.getBoolean(CLEAN_ON_VALIDATION_ERROR);
        final Boolean baselineOnMigrate = namespace.getBoolean(INIT_ON_MIGRATE);

        if (outOfOrder != null) {
            flywayFactory.setOutOfOrder(outOfOrder);
        }

        if (validateOnMigrate != null) {
            flywayFactory.setValidateOnMigrate(validateOnMigrate);
        }

        if (cleanOnValidationError != null) {
            flywayFactory.setCleanOnValidationError(cleanOnValidationError);
        }

        if (baselineOnMigrate != null) {
            flywayFactory.setBaselineOnMigrate(baselineOnMigrate);
        }
    }

    @Override
    public void run(final Namespace namespace, final Flyway flyway) throws Exception {
        final MigrateResult migrateResult = flyway.migrate();
        LOG.debug("Executed {} migrations to migrate {} in database {} from schema {} -> {}",
                migrateResult.migrationsExecuted, migrateResult.schemaName, migrateResult.database,
                migrateResult.initialSchemaVersion, migrateResult.targetSchemaVersion);

        if (!migrateResult.warnings.isEmpty()) {
            LOG.warn("{} warnings when migrating {} in database {} from schema {} -> {}: {}",
                    migrateResult.warnings.size(), migrateResult.schemaName, migrateResult.database,
                    migrateResult.initialSchemaVersion, migrateResult.targetSchemaVersion,
                    migrateResult.warnings
            );
        }
    }
}
