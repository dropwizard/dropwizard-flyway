package io.dropwizard.flyway;

import com.google.common.collect.ImmutableList;
import org.flywaydb.core.Flyway;
import org.junit.Test;
import org.mockito.InOrder;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class FlywayCommandsTests {

    private static final String EMPTY_STRING = "";

    private static final String COMMANDS_STRING = "clean, baseline, migrate";
    private static final ImmutableList<FlywayCommand> COMMANDS = ImmutableList.<FlywayCommand>builder()
            .add(FlywayCommand.CLEAN)
            .add(FlywayCommand.BASELINE)
            .add(FlywayCommand.MIGRATE)
            .build();

    private final Flyway flyway = mock(Flyway.class);

    @Test
    public final void testFrom() {
        ImmutableList<FlywayCommand> parsed = FlywayCommands.from(COMMANDS_STRING);
        assertThat(COMMANDS, is(equalTo(parsed)));
    }

    @Test
    public final void testEmptyFrom() {
        ImmutableList<FlywayCommand> parsed = FlywayCommands.from(EMPTY_STRING);
        assertThat(parsed.size(), is(equalTo(0)));
    }

    @Test
    public final void testTo() {
        String parsed = FlywayCommands.to(COMMANDS);
        assertThat(COMMANDS_STRING, is(equalTo(parsed)));
    }

    @Test
    public final void testEmptyTo() {
        String parsed = FlywayCommands.to();
        assertThat(parsed, is(equalTo(EMPTY_STRING)));
    }

    @Test
    public final void testExecuteOne() {
        verify(flyway, times(0)).baseline();
        FlywayCommands.execute(flyway, FlywayCommand.BASELINE);
        verify(flyway, times(1)).baseline();
    }

    @Test
    public final void testExecuteMany() {
        InOrder flywayExecutionOrder = inOrder(flyway);
        verify(flyway, times(0)).clean();
        verify(flyway, times(0)).baseline();
        verify(flyway, times(0)).migrate();
        FlywayCommands.execute(flyway,
                FlywayCommand.CLEAN,
                FlywayCommand.BASELINE,
                FlywayCommand.MIGRATE);
        flywayExecutionOrder.verify(flyway, times(1)).clean();
        flywayExecutionOrder.verify(flyway, times(1)).baseline();
        flywayExecutionOrder.verify(flyway, times(1)).migrate();
    }

}
