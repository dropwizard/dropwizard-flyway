package io.dropwizard.flyway.cli;

import java.util.Optional;

import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.cli.Cli;
import io.dropwizard.db.DatabaseConfiguration;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.flyway.FlywayConfiguration;
import io.dropwizard.flyway.FlywayFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.util.JarLocation;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AbstractCommandTest {
    protected Cli cli;

    protected FlywayFactory mockFlywayFactory;
    protected Flyway mockFlyway;

    private static class TestApplication extends Application<TestConfiguration> {
        @Override
        public void run(TestConfiguration configuration, Environment environment) throws Exception {
        }
    }

    public static class TestConfiguration extends Configuration {
    }

    @BeforeEach
    public void setUp() throws Exception {
        // Setup necessary mock
        final JarLocation location = mock(JarLocation.class);
        when(location.getVersion()).thenReturn(Optional.of("1.0.0"));

        final DatabaseConfiguration dbConfiguration = mock(DatabaseConfiguration.class);
        when(dbConfiguration.getDataSourceFactory(any())).thenReturn(mock(PooledDataSourceFactory.class));

        final FlywayConfiguration flywayConfiguration = mock(FlywayConfiguration.class);
        mockFlywayFactory = mock(FlywayFactory.class);
        when(flywayConfiguration.getFlywayFactory(any())).thenReturn(mockFlywayFactory);

        mockFlyway = mock(Flyway.class);
        when(mockFlywayFactory.build(any())).thenReturn(mockFlyway);

        // Add commands you want to test
        final Bootstrap<TestConfiguration> bootstrap = new Bootstrap<>(new TestApplication());
        bootstrap.addCommand(new DbCommand<TestConfiguration>("db", dbConfiguration, flywayConfiguration, TestConfiguration.class));

        // Build what'll run the command and interpret arguments
        cli = new Cli(location, bootstrap, System.out, System.err);
    }
}
