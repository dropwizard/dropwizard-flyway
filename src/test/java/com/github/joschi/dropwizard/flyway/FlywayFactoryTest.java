package com.github.joschi.dropwizard.flyway;

import org.flywaydb.core.Flyway;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.sql.DataSource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

@RunWith(MockitoJUnitRunner.class)
public class FlywayFactoryTest {
    @Mock
    private DataSource mockDataSource;

    @Test
    public void defaultConfigurationShouldBeValid() {
        final FlywayFactory factory = new FlywayFactory();
        final Flyway flyway = factory.build(mockDataSource);

        assertNotNull(flyway);
        assertSame(mockDataSource, flyway.getDataSource());
        assertEquals("UTF-8", flyway.getEncoding());
        assertEquals("schema_version", flyway.getTable());
        assertEquals(0, flyway.getSchemas().length);
    }
}
