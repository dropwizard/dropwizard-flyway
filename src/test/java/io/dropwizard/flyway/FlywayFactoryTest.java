package io.dropwizard.flyway;

import com.google.common.collect.ImmutableMap;
import jakarta.validation.constraints.NotNull;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.internal.database.postgresql.PostgreSQLConfigurationExtension;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

public class FlywayFactoryTest {

    private JdbcDataSource dataSource;

    @BeforeEach
    void setUp() throws SQLException {
        dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:mem:testdb");
        dataSource.setUser("sa");
    }

    @Test
    public void defaultConfigurationShouldBeValid() {
        final FlywayFactory factory = new FlywayFactory();

        final Flyway flyway = factory.build(dataSource);

        assertNotNull(flyway);
        assertSame(dataSource, flyway.getConfiguration().getDataSource());
        assertEquals(StandardCharsets.UTF_8, flyway.getConfiguration().getEncoding());
        assertEquals("flyway_schema_history", flyway.getConfiguration().getTable());
        assertEquals(0, flyway.getConfiguration().getSchemas().length);
    }

    @Test
    public void checkConfigurationWithOverridedSetting() {
        Flyway flyway = new FlywayFactory().build(dataSource);
        assertNotNull(flyway);

        boolean transactionalLockDefault = getPostgresTransactionLockSettings(flyway);
        boolean transactionalLockOverrided = !transactionalLockDefault;

        final FlywayFactory factory = new FlywayFactory();
        Map<String, String> configuration = ImmutableMap.of("flyway.postgresql.transactional.lock", String.valueOf(transactionalLockOverrided));
        factory.setConfiguration(configuration);
        flyway =  factory.build(dataSource);

        assertNotNull(flyway);
        assertEquals(StandardCharsets.UTF_8, flyway.getConfiguration().getEncoding());          // default value
        assertEquals(0, flyway.getConfiguration().getSchemas().length);                 // default value
        assertEquals("flyway_schema_history", flyway.getConfiguration().getTable());    // default value
        assertEquals(transactionalLockOverrided, getPostgresTransactionLockSettings(flyway));   // overrided value
    }

    private static boolean getPostgresTransactionLockSettings(@NotNull final Flyway flyway) {
        return flyway.getConfiguration().getPluginRegister().getPlugin(PostgreSQLConfigurationExtension.class).isTransactionalLock();
    }
}
