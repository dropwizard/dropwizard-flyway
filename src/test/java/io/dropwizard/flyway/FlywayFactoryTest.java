package io.dropwizard.flyway;

import org.flywaydb.core.Flyway;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

public class FlywayFactoryTest {
    @Rule
    public final MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private DataSource mockDataSource;

    @Test
    public void defaultConfigurationShouldBeValid() {
        final FlywayFactory factory = new FlywayFactory();
        final Flyway flyway = factory.build(mockDataSource);

        assertNotNull(flyway);
        assertSame(mockDataSource, flyway.getConfiguration().getDataSource());
        assertEquals(StandardCharsets.UTF_8, flyway.getConfiguration().getEncoding());
        assertEquals("flyway_schema_history", flyway.getConfiguration().getTable());
        assertEquals(0, flyway.getConfiguration().getSchemas().length);
    }
}
