package io.dropwizard.flyway;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.CheckForNull;
import java.util.Arrays;
import java.util.List;

import static org.flywaydb.core.internal.info.MigrationInfoDumper.dumpToAsciiTable;

/**
 * Utility class for reading and writing {@code Flyway} commands as a sequence of strings as well as
 * executing arbitrary commands based on given enumerated {@code FlywayCommand} objects
 * using an instance of {@code Flyway}.
 */
public final class FlywayCommands {

    private static final Logger LOG = LoggerFactory.getLogger(FlywayCommands.class);

    private static final String DEFAULT_DELIM = ",";
    private static final String DEFAULT_JOIN_STRING = DEFAULT_DELIM + " ";

    private FlywayCommands() {
        // Intentionally left blank to prevent instantiation
    }

    /**
     * Convert from a {@code String} sequence of delimited flyway commands to the validated enumerations.<br/>
     * For example, we can take the string: <br/>
     * "clean, migrate, validate"<br/>
     * and get the resulting commands from it after validation:<br/>
     * {@link FlywayCommand#CLEAN}, {@link FlywayCommand#MIGRATE}, {@link FlywayCommand#VALIDATE}
     *
     * @param string The {@code String} containing the delimited commands
     * @return The {@code ImmutableList} of {@code FlywayCommand} enumerations
     */
    public static ImmutableList<FlywayCommand> from(@CheckForNull String string) {
        List<FlywayCommand> parsedCommands = Lists.newArrayList();
        if (Strings.isNullOrEmpty(string)) {
            return ImmutableList.copyOf(parsedCommands);
        }
        // Iterate through each string from split result
        for (String splitString : string.split(DEFAULT_DELIM)) {
            String trimmed = splitString.trim();
            try {
                parsedCommands.add(FlywayCommand.valueOf(trimmed.toUpperCase()));
            } catch (IllegalArgumentException iae) {
                // Log exception if un-parsable
                if (LOG.isWarnEnabled()) {
                    LOG.warn("", iae);
                }
            }
        }
        return ImmutableList.copyOf(parsedCommands);
    }

    /**
     * Convert from an array of {@code FlywayCommand} enumerations to a {@code String} representation.
     *
     * @param flywayCommands The array of {@code FlywayCommand} objects to convert to a {@code String}
     * @return The resulting string
     */
    public static String to(FlywayCommand...flywayCommands) {
        return to(Arrays.asList(flywayCommands));
    }

    /**
     * Convert from an array of {@code FlywayCommand} enumerations to a {@code String} representation.
     *
     * @param flywayCommands The {@code List} of {@code FlywayCommand} objects to convert to a {@code String}
     * @return The resulting string
     */
    public static String to(List<FlywayCommand> flywayCommands) {
        return Joiner.on(DEFAULT_JOIN_STRING).skipNulls().join(flywayCommands);
    }

    /**
     * Execute an arbitrary {@code FlywayCommand} using a given {@code Flyway} instance and log the results of execution.
     *
     * @param flyway The flyway instance to use
     * @param command The command to run
     */
    public static void execute(Flyway flyway, FlywayCommand command) {
        switch (command) {
            case MIGRATE:
                int successfulMigrations = flyway.migrate();
                if (LOG.isDebugEnabled()) {
                    LOG.debug("{} successful migrations applied", successfulMigrations);
                }
                break;
            case REPAIR:
                flyway.repair();
                break;
            case CLEAN:
                flyway.clean();
                break;
            case INFO:
                if (LOG.isInfoEnabled()) {
                    LOG.info(dumpToAsciiTable(flyway.info().all()));
                } else {
                    LOG.warn("Flyway Info throttled due to log level not being at \'INFO\' or below");
                }
                break;
            case VALIDATE:
                flyway.validate();
                break;
            case BASELINE:
                flyway.baseline();
                break;
            default:
                break;
        }
    }

    /**
     * Execute a {@code List} of {@code FlywayCommand} objects in order.
     *
     * @param flyway The flyway instance to use
     * @param commandList The commands to execute
     */
    public static void execute(Flyway flyway, List<FlywayCommand> commandList) {
        for (FlywayCommand command : commandList) {
            execute(flyway, command);
        }
    }

    /**
     * Executes an array of {@code FlywayCommand} objects in order.
     *
     * @param flywayCommands The commands to execute
     */
    public static void execute(Flyway flyway, FlywayCommand...flywayCommands) {
        execute(flyway, Arrays.asList(flywayCommands));
    }

}
