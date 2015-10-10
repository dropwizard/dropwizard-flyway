package io.dropwizard.flyway.cli;

import io.dropwizard.flyway.FlywayCommand;
import io.dropwizard.flyway.FlywayConfiguration;
import net.sourceforge.argparse4j.inf.Subparser;
import org.flywaydb.core.Flyway;
import io.dropwizard.Configuration;
import io.dropwizard.db.DatabaseConfiguration;
import net.sourceforge.argparse4j.inf.Namespace;

import static net.sourceforge.argparse4j.impl.Arguments.storeTrue;

public class DbValidateCommand<T extends Configuration> extends AbstractFlywayCommand<T> {

    private static final String OUT_OF_ORDER = "outOfOrder";
    private static final String CLEAN_ON_VALIDATION_ERROR = "cleanOnValidationError";

    public DbValidateCommand(final DatabaseConfiguration<T> databaseConfiguration,
                             final FlywayConfiguration<T> flywayConfiguration,
                             final Class<T> configurationClass) {
        super(FlywayCommand.VALIDATE, databaseConfiguration, flywayConfiguration, configurationClass);
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

            subparser.addArgument("--" + CLEAN_ON_VALIDATION_ERROR)
                    .action(storeTrue())
                    .setDefault(Boolean.FALSE)
                    .dest(CLEAN_ON_VALIDATION_ERROR)
                    .help("Whether to automatically call clean or not when a validation error occurs. " +
                            "This is exclusively intended as a convenience for development. " +
                            "Even tough we strongly recommend not to change migration scripts once they have been checked into SCM and run, this provides a way of dealing with this case in a smooth manner. " +
                            "The database will be wiped clean automatically, ensuring that the next migration will bring you back to the state checked into SCM. " +
                            "Warning! Do not enable in production !");
        }

    @Override
    protected void run(final Namespace namespace, final Flyway flyway) throws Exception {
        flyway.setOutOfOrder(namespace.getBoolean(OUT_OF_ORDER));
        flyway.setCleanOnValidationError(namespace.getBoolean(CLEAN_ON_VALIDATION_ERROR));

        flyway.validate();
    }
}
