package io.dropwizard.flyway;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;

import javax.sql.DataSource;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
public class FlywayFactory {
    @JsonProperty
    @NotEmpty
    private String encoding = StandardCharsets.UTF_8.name();
    @JsonProperty
    @PositiveOrZero
    private int connectRetries = 0;
    @JsonProperty
    @PositiveOrZero
    private int connectRetriesInterval = 120;
    @JsonProperty
    @Nullable
    private String defaultSchema = null;
    @JsonProperty
    @NotNull
    private List<String> schemas = Collections.emptyList();
    @JsonProperty
    @NotNull
    private List<String> callbacks = Collections.emptyList();
    @JsonProperty
    @NotEmpty
    private String metaDataTableName = "flyway_schema_history";
    @JsonProperty
    @NotEmpty
    private String sqlMigrationPrefix = "V";
    @JsonProperty
    @NotEmpty
    private String sqlMigrationSeparator = "__";
    @JsonProperty
    @NotNull
    private List<String> sqlMigrationSuffixes = Collections.singletonList(".sql");
    @JsonProperty
    @NotEmpty
    private String placeholderPrefix = "${";
    @JsonProperty
    @NotEmpty
    private String placeholderSuffix = "}";
    @JsonProperty
    @NotNull
    private Map<String, String> placeholders = Collections.emptyMap();
    @JsonProperty
    @NotEmpty
    private List<String> locations = Collections.singletonList("db/migration");
    @JsonProperty
    @NotNull
    private List<String> resolvers = Collections.emptyList();
    @JsonProperty
    private String initSql = null;
    @JsonIgnore
    private ClassLoader classLoader = null;
    @JsonProperty
    private boolean outOfOrder = false;
    @JsonProperty
    @NotNull
    private String baselineDescription = "<< Flyway Baseline >>";
    @JsonProperty
    private boolean baselineOnMigrate = false;
    @JsonProperty
    private boolean validateOnMigrate = true;
    @JsonProperty
    @NotBlank
    private String baseLineVersion = "1";
    @JsonProperty
    private boolean cleanDisabled = false;
    @JsonProperty
    private boolean cleanOnValidationError = false;
    @JsonProperty
    private boolean group = false;
    @JsonProperty
    private List<String> ignoreMigrationPatterns = Collections.singletonList("*:future");
    @JsonProperty
    @NotNull
    private String installedBy = "";
    @JsonProperty
    private boolean mixed = false;
    @JsonProperty
    private boolean placeholderReplacement = true;
    @JsonProperty
    private boolean skipDefaultCallbacks = false;
    @JsonProperty
    private boolean skipDefaultResolvers = false;

    // Commercial Features
    @JsonProperty
    @Nullable
    private Boolean batch;
    @JsonProperty
    @Nullable
    private File dryRunOutputFile;
    @JsonProperty
    @Nullable
    private List<String> errorOverrides;
    @JsonProperty
    @Nullable
    private Boolean stream;
    @JsonProperty
    @Nullable
    private String target;
    @JsonProperty
    @NotNull
    Map<String, String> configuration = Collections.emptyMap();

    /**
     * @see FluentConfiguration#getEncoding()
     */
    public String getEncoding() {
        return encoding;
    }

    /**
     * @see FluentConfiguration#encoding(String)
     */
    public void setEncoding(final String encoding) {
        this.encoding = encoding;
    }

    /**
     * @see FluentConfiguration#getConnectRetries()
     */
    public int getConnectRetries() {
        return connectRetries;
    }

    /**
     * @see FluentConfiguration#connectRetries(int)
     */
    public void setConnectRetries(int connectRetries) {
        this.connectRetries = connectRetries;
    }

    /**
     * @see FluentConfiguration#getConnectRetriesInterval()
     */
    public int getConnectRetriesInterval() {
        return connectRetriesInterval;
    }

    /**
     * @see FluentConfiguration#connectRetriesInterval(int)
     */
    public void setConnectRetriesInterval(int connectRetriesInterval) {
        this.connectRetriesInterval = connectRetriesInterval;
    }

    /**
     * @see FluentConfiguration#getDefaultSchema()
     */
    @Nullable
    public String getDefaultSchema() {
        return defaultSchema;
    }

    /**
     * @see FluentConfiguration#defaultSchema(String)
     */
    public void setDefaultSchema(@Nullable final String defaultSchema) {
        this.defaultSchema = defaultSchema;
    }

    /**
     * @see FluentConfiguration#getSchemas()
     */
    public List<String> getSchemas() {
        return schemas;
    }

    /**
     * @see FluentConfiguration#schemas(String...)
     */
    public void setSchemas(final List<String> schemas) {
        this.schemas = schemas;
    }

    /**
     * @see FluentConfiguration#getCallbacks()
     */
    public List<String> getCallbacks() {
        return callbacks;
    }

    /**
     * @see FluentConfiguration#callbacks(String...)
     */
    public void setCallbacks(final List<String> callbacks) {
        this.callbacks = callbacks;
    }

    /**
     * @see FluentConfiguration#getTable()
     */
    public String getMetaDataTableName() {
        return metaDataTableName;
    }

    /**
     * @see FluentConfiguration#table(String)
     */
    public void setMetaDataTableName(final String metaDataTableName) {
        this.metaDataTableName = metaDataTableName;
    }

    /**
     * @see FluentConfiguration#getSqlMigrationPrefix()
     */
    public String getSqlMigrationPrefix() {
        return sqlMigrationPrefix;
    }

    /**
     * @see FluentConfiguration#sqlMigrationPrefix(String)
     */
    public void setSqlMigrationPrefix(final String sqlMigrationPrefix) {
        this.sqlMigrationPrefix = sqlMigrationPrefix;
    }

    /**
     * @see FluentConfiguration#getSqlMigrationPrefix()
     */
    public List<String> getSqlMigrationSuffixes() {
        return sqlMigrationSuffixes;
    }

    /**
     * @see FluentConfiguration#sqlMigrationSuffixes(String...)
     */
    public void setSqlMigrationSuffixes(final List<String> sqlMigrationSuffixes) {
        this.sqlMigrationSuffixes = sqlMigrationSuffixes;
    }

    /**
     * @see FluentConfiguration#getSqlMigrationSeparator()
     */
    public String getSqlMigrationSeparator() {
        return sqlMigrationSeparator;
    }

    /**
     * @see FluentConfiguration#sqlMigrationSeparator(String)
     */
    public void setSqlMigrationSeparator(final String sqlMigrationSeparator) {
        this.sqlMigrationSeparator = sqlMigrationSeparator;
    }

    /**
     * @see FluentConfiguration#getPlaceholderPrefix()
     */
    public String getPlaceholderPrefix() {
        return placeholderPrefix;
    }

    /**
     * @see FluentConfiguration#placeholderPrefix(String)
     */
    public void setPlaceholderPrefix(final String placeholderPrefix) {
        this.placeholderPrefix = placeholderPrefix;
    }

    /**
     * @see FluentConfiguration#getPlaceholderSuffix()
     */
    public String getPlaceholderSuffix() {
        return placeholderSuffix;
    }

    /**
     * @see FluentConfiguration#placeholderSuffix(String)
     */
    public void setPlaceholderSuffix(final String placeholderSuffix) {
        this.placeholderSuffix = placeholderSuffix;
    }

    /**
     * @see FluentConfiguration#getPlaceholders()
     */
    public Map<String, String> getPlaceholders() {
        return placeholders;
    }

    /**
     * @see FluentConfiguration#placeholders(Map)
     */
    public void setPlaceholders(final Map<String, String> placeholders) {
        this.placeholders = placeholders;
    }

    /**
     * @see FluentConfiguration#getLocations()
     */
    public List<String> getLocations() {
        return locations;
    }

    /**
     * @see FluentConfiguration#locations(String...)
     */
    public void setLocations(final List<String> locations) {
        this.locations = locations;
    }

    /**
     * @see FluentConfiguration#getResolvers()
     */
    public List<String> getResolvers() {
        return resolvers;
    }

    /**
     * @see FluentConfiguration#resolvers(String...)
     */
    public void setResolvers(final List<String> resolvers) {
        this.resolvers = resolvers;
    }

    /**
     * @see FluentConfiguration#getInitSql()
     */
    public String getInitSql() {
        return initSql;
    }

    /**
     * @see FluentConfiguration#initSql(String)
     */
    public void setInitSql(String initSql) {
        this.initSql = initSql;
    }

    /**
     * @see FluentConfiguration#getClassLoader()
     */
    public ClassLoader getClassLoader() {
        return classLoader;
    }

    /**
     * @see FluentConfiguration#FluentConfiguration(ClassLoader)
     */
    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    /**
     * @see FluentConfiguration#isOutOfOrder()
     */
    public boolean isOutOfOrder() {
        return outOfOrder;
    }

    /**
     * @see FluentConfiguration#outOfOrder(boolean)
     */
    public void setOutOfOrder(boolean outOfOrder) {
        this.outOfOrder = outOfOrder;
    }

    /**
     * @see FluentConfiguration#getBaselineDescription()
     */
    public String getBaselineDescription() {
        return baselineDescription;
    }

    /**
     * @see FluentConfiguration#baselineDescription(String)
     */
    public void setBaselineDescription(String baselineDescription) {
        this.baselineDescription = baselineDescription;
    }

    /**
     * @see FluentConfiguration#isBaselineOnMigrate()
     */
    public boolean isBaselineOnMigrate() {
        return baselineOnMigrate;
    }

    /**
     * @see FluentConfiguration#baselineOnMigrate(boolean)
     */
    public void setBaselineOnMigrate(boolean baselineOnMigrate) {
        this.baselineOnMigrate = baselineOnMigrate;
    }

    /**
     * @see FluentConfiguration#isValidateOnMigrate()
     */
    public boolean isValidateOnMigrate() {
        return validateOnMigrate;
    }

    /**
     * @see FluentConfiguration#validateOnMigrate(boolean)
     */
    public void setValidateOnMigrate(boolean validateOnMigrate) {
        this.validateOnMigrate = validateOnMigrate;
    }

    /**
     * @see FluentConfiguration#getBaselineVersion()
     */
    public String getBaseLineVersion() {
        return baseLineVersion;
    }

    /**
     * @see FluentConfiguration#baselineVersion(String)
     */
    public void setBaseLineVersion(String baseLineVersion) {
        this.baseLineVersion = baseLineVersion;
    }

    /**
     * @see FluentConfiguration#isCleanDisabled()
     */
    public boolean isCleanDisabled() {
        return cleanDisabled;
    }

    /**
     * @see FluentConfiguration#cleanDisabled(boolean)
     */
    public void setCleanDisabled(boolean cleanDisabled) {
        this.cleanDisabled = cleanDisabled;
    }

    /**
     * @see FluentConfiguration#isCleanOnValidationError()
     */
    public boolean isCleanOnValidationError() {
        return cleanOnValidationError;
    }

    /**
     * @see FluentConfiguration#cleanOnValidationError(boolean)
     */
    public void setCleanOnValidationError(boolean cleanOnValidationError) {
        this.cleanOnValidationError = cleanOnValidationError;
    }

    /**
     * @see FluentConfiguration#isGroup()
     */
    public boolean isGroup() {
        return group;
    }

    /**
     * @see FluentConfiguration#group(boolean)
     */
    public void setGroup(boolean group) {
        this.group = group;
    }

    /**
     * @see FluentConfiguration#getIgnoreMigrationPatterns()
     */
    public List<String> getIgnoreMigrationPatterns() {
        return ignoreMigrationPatterns;
    }

    /**
     * @see FluentConfiguration#ignoreMigrationPatterns(String...)
     */
    public void setIgnoreMigrationPatterns(final List<String> ignoreMigrationPatterns) {
        this.ignoreMigrationPatterns = ignoreMigrationPatterns;
    }

    /**
     * @see FluentConfiguration#getInstalledBy()
     */
    public String getInstalledBy() {
        return installedBy;
    }

    /**
     * @see FluentConfiguration#installedBy(String)
     */
    public void setInstalledBy(String installedBy) {
        this.installedBy = installedBy;
    }

    /**
     * @see FluentConfiguration#isMixed()
     */
    public boolean isMixed() {
        return mixed;
    }

    /**
     * @see FluentConfiguration#mixed(boolean)
     */
    public void setMixed(boolean mixed) {
        this.mixed = mixed;
    }

    /**
     * @see FluentConfiguration#isPlaceholderReplacement()
     */
    public boolean isPlaceholderReplacement() {
        return placeholderReplacement;
    }

    /**
     * @see FluentConfiguration#placeholderReplacement(boolean)
     */
    public void setPlaceholderReplacement(boolean placeholderReplacement) {
        this.placeholderReplacement = placeholderReplacement;
    }

    /**
     * @see FluentConfiguration#isSkipDefaultCallbacks()
     */
    public boolean isSkipDefaultCallbacks() {
        return skipDefaultCallbacks;
    }

    /**
     * @see FluentConfiguration#skipDefaultCallbacks(boolean)
     */
    public void setSkipDefaultCallbacks(boolean skipDefaultCallbacks) {
        this.skipDefaultCallbacks = skipDefaultCallbacks;
    }

    /**
     * @see FluentConfiguration#isSkipDefaultResolvers()
     */
    public boolean isSkipDefaultResolvers() {
        return skipDefaultResolvers;
    }

    /**
     * @see FluentConfiguration#skipDefaultResolvers(boolean)
     */
    public void setSkipDefaultResolvers(boolean skipDefaultResolvers) {
        this.skipDefaultResolvers = skipDefaultResolvers;
    }

    // Commercial Features

    /**
     * @see FluentConfiguration#isBatch()
     */
    @Nullable
    public Boolean isBatch() {
        return batch;
    }

    /**
     * @see FluentConfiguration#batch(boolean)
     */
    public void setBatch(@Nullable Boolean batch) {
        this.batch = batch;
    }

    /**
     * @see FluentConfiguration#getDryRunOutput()
     */
    @Nullable
    public File getDryRunOutputFile() {
        return dryRunOutputFile;
    }

    /**
     * @see FluentConfiguration#dryRunOutput(File)
     */
    public void setDryRunOutputFile(@Nullable File dryRunOutputFile) {
        this.dryRunOutputFile = dryRunOutputFile;
    }

    /**
     * @see FluentConfiguration#getErrorOverrides()
     */
    @Nullable
    public List<String> getErrorOverrides() {
        return errorOverrides;
    }

    /**
     * @see FluentConfiguration#errorOverrides(String...)
     */
    public void setErrorOverrides(@Nullable List<String> errorOverrides) {
        this.errorOverrides = errorOverrides;
    }

    /**
     * @see FluentConfiguration#isStream()
     */
    @Nullable
    public Boolean isStream() {
        return stream;
    }

    /**
     * @see FluentConfiguration#stream(boolean)
     */
    public void setStream(@Nullable Boolean stream) {
        this.stream = stream;
    }

    /**
     * @see FluentConfiguration#getTarget()
     */
    @Nullable
    public String getTarget() {
        return target;
    }

    /**
     * @see FluentConfiguration#target(String)
     */
    public void setTarget(@Nullable String target) {
        this.target = target;
    }

    /**
     * @see FluentConfiguration#configuration(Map)
     */
    public Map<String, String> getConfiguration() {
        return configuration;
    }

    /**
     * Configures Flyway with these properties.
     * This overwrites any existing configuration.
     *
     * @see <a href="https://documentation.red-gate.com/fd/parameters-224919673.html">Configuration parameters</a>
     */
    public void setConfiguration(Map<String, String> configuration) {
        this.configuration = configuration;
    }

    public Flyway build(final String url, final String user, final String password) {
        return createConfiguration().dataSource(url, user, password).load();
    }

    public Flyway build(final DataSource dataSource) {
        return createConfiguration().dataSource(dataSource).load();
    }

    private FluentConfiguration createConfiguration() {
        final String[] emptyStringArray = new String[0];
        FluentConfiguration flyway = classLoader == null ? Flyway.configure() : Flyway.configure(classLoader);
        flyway = flyway
              .baselineOnMigrate(baselineOnMigrate)
              .baselineVersion(baseLineVersion)
              .callbacks(callbacks.toArray(emptyStringArray))
              .cleanDisabled(cleanDisabled)
              .cleanOnValidationError(cleanOnValidationError)
              .configuration(configuration)
              .connectRetries(connectRetries)
              .connectRetriesInterval(connectRetriesInterval)
              .encoding(encoding)
              .group(group)
              .ignoreMigrationPatterns(ignoreMigrationPatterns.toArray(emptyStringArray))
              .installedBy(installedBy)
              .locations(locations.toArray(emptyStringArray))
              .mixed(mixed)
              .outOfOrder(outOfOrder)
              .placeholderPrefix(placeholderPrefix)
              .placeholderReplacement(placeholderReplacement)
              .placeholderSuffix(placeholderSuffix)
              .placeholders(placeholders)
              .resolvers(resolvers.toArray(emptyStringArray))
              .initSql(initSql)
              .schemas(schemas.toArray(emptyStringArray))
              .skipDefaultCallbacks(skipDefaultCallbacks)
              .skipDefaultResolvers(skipDefaultResolvers)
              .sqlMigrationPrefix(sqlMigrationPrefix)
              .sqlMigrationSeparator(sqlMigrationSeparator)
              .sqlMigrationSuffixes(sqlMigrationSuffixes.toArray(emptyStringArray))
              .table(metaDataTableName)
              .validateOnMigrate(validateOnMigrate);

        if (defaultSchema != null) {
            flyway.defaultSchema(defaultSchema);
        }

        // Commercial features
        if (batch != null) {
            flyway.batch(batch);
        }
        if (dryRunOutputFile != null) {
            flyway.dryRunOutput(dryRunOutputFile);
        }
        if (errorOverrides != null) {
            flyway.errorOverrides(errorOverrides.toArray(emptyStringArray));
        }
        if (stream != null) {
            flyway.stream(stream);
        }
        if (target != null) {
            flyway.target(target);
        }

        return flyway;
    }
}
