package io.dropwizard.flyway.cli;

import io.dropwizard.core.Configuration;
import io.dropwizard.db.DatabaseConfiguration;
import io.dropwizard.flyway.FlywayConfiguration;
import io.dropwizard.flyway.FlywayFactory;
import net.sourceforge.argparse4j.impl.Arguments;
import net.sourceforge.argparse4j.inf.Namespace;
import net.sourceforge.argparse4j.inf.Subparser;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;

import static net.sourceforge.argparse4j.impl.Arguments.storeTrue;

public class DbValidateCommand<T extends Configuration> extends AbstractFlywayCommand<T> {

    private static final String OUT_OF_ORDER = "outOfOrder";
    private static final String CLEAN_ON_VALIDATION_ERROR = "cleanOnValidationError";

    public DbValidateCommand(final DatabaseConfiguration<T> databaseConfiguration,
                             final FlywayConfiguration<T> flywayConfiguration,
                             final Class<T> configurationClass) {
        super("validate", "Validates the applied migrations against the ones available on the classpath.",
                databaseConfiguration, flywayConfiguration, configurationClass);
    }

    @Override
    public void configure(Subparser subparser) {
        super.configure(subparser);

        subparser.addArgument("--" + OUT_OF_ORDER)
                .action(Arguments.storeConst()).setConst(Boolean.TRUE)
                .dest(OUT_OF_ORDER)
                .help("Allows migrations to be run \"out of order\". " +
                        "If you already have versions 1 and 3 applied, and now a version 2 is found, it will be applied too instead of being ignored.");

        subparser.addArgument("--" + CLEAN_ON_VALIDATION_ERROR)
                .action(Arguments.storeConst()).setConst(Boolean.TRUE)
                .dest(CLEAN_ON_VALIDATION_ERROR)
                .help("Whether to automatically call clean or not when a validation error occurs. " +
                        "This is exclusively intended as a convenience for development. " +
                        "Even tough we strongly recommend not to change migration scripts once they have been checked into SCM and run, this provides a way of dealing with this case in a smooth manner. " +
                        "The database will be wiped clean automatically, ensuring that the next migration will bring you back to the state checked into SCM. " +
                        "Warning! Do not enable in production !");
    }

    @Override
    protected void setAdditionalOptions(FlywayFactory flywayFactory, Namespace namespace) {
        final Boolean outOfOrder = namespace.getBoolean(OUT_OF_ORDER);
        final Boolean cleanOnValidationError = namespace.getBoolean(CLEAN_ON_VALIDATION_ERROR);
        
        if (outOfOrder != null) {
            flywayFactory.setOutOfOrder(outOfOrder);
        }
    
        if (cleanOnValidationError != null) {
            flywayFactory.setCleanOnValidationError(cleanOnValidationError);
        }
    
    }

    @Override
    protected void run(final Namespace namespace, final Flyway flyway) throws Exception {
        flyway.validate();
    }
}
