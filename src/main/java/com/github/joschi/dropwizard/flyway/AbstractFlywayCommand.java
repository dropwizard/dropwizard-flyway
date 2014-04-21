package com.github.joschi.dropwizard.flyway;

import io.dropwizard.Configuration;
import io.dropwizard.cli.ConfiguredCommand;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.db.DatabaseConfiguration;
import io.dropwizard.setup.Bootstrap;
import net.sourceforge.argparse4j.inf.Namespace;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.FlywayException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

abstract class AbstractFlywayCommand<T extends Configuration> extends ConfiguredCommand<T> {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractFlywayCommand.class);
    private final DatabaseConfiguration<T> databaseConfiguration;
    private final Class<T> configurationClass;

    AbstractFlywayCommand(final String name,
                          final String description,
                          final DatabaseConfiguration<T> databaseConfiguration,
                          final Class<T> configurationClass) {
        super(name, description);
        this.databaseConfiguration = databaseConfiguration;
        this.configurationClass = configurationClass;
    }

    @Override
    protected Class<T> getConfigurationClass() {
        return configurationClass;
    }

    @Override
    protected void run(final Bootstrap<T> bootstrap, final Namespace namespace, final T configuration) throws Exception {
        final DataSourceFactory factory = databaseConfiguration.getDataSourceFactory(configuration);
        final Flyway flyway = new Flyway();
        flyway.setDataSource(factory.build(bootstrap.getMetricRegistry(), "Flyway"));

        try {
            run(namespace, flyway);
        } catch (FlywayException e) {
            LOG.error("Error while running database command", e);
            throw e;
        }
    }

    protected abstract void run(final Namespace namespace, final Flyway flyway) throws Exception;
}
