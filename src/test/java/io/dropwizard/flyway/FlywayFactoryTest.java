package io.dropwizard.flyway;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

@ExtendWith(MockitoExtension.class)
public class FlywayFactoryTest {

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
