package io.dropwizard.flyway;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.flywaydb.core.Flyway;
import org.hibernate.validator.constraints.NotEmpty;

import javax.sql.DataSource;
import javax.validation.constraints.NotNull;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class FlywayFactory {
    @JsonProperty
    @NotEmpty
    private String encoding = StandardCharsets.UTF_8.name();
    @JsonProperty
    @NotNull
    private List<String> schemas = ImmutableList.of();
    @JsonProperty
    @NotNull
    private List<String> callbacks = ImmutableList.of();
    @JsonProperty
    @NotEmpty
    private String metaDataTableName = "schema_version";
    @JsonProperty
    @NotEmpty
    private String sqlMigrationPrefix = "V";
    @JsonProperty
    @NotEmpty
    private String sqlMigrationSeparator = "__";
    @JsonProperty
    @NotNull
    private String sqlMigrationSuffix = ".sql";
    @JsonProperty
    @NotEmpty
    private String placeholderPrefix = "${";
    @JsonProperty
    @NotEmpty
    private String placeholderSuffix = "}";
    @JsonProperty
    @NotNull
    private Map<String, String> placeholders = ImmutableMap.of();
    @JsonProperty
    @NotEmpty
    private List<String> locations = ImmutableList.of("db/migration");
    @JsonProperty
    @NotNull
    private List<String> resolvers = ImmutableList.of();
    @JsonIgnore
    private ClassLoader classLoader = null;
    @JsonProperty
    @NotNull
    private boolean outOfOrder = false;
    @JsonProperty
    @NotNull
    private String baselineDescription = "<< Flyway Baseline >>";
    @JsonProperty
    @NotNull
    private boolean baselineOnMigrate = false;
    @JsonProperty
    @NotNull
    private boolean validateOnMigrate = true;

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(final String encoding) {
        this.encoding = encoding;
    }

    public List<String> getSchemas() {
        return schemas;
    }

    public void setSchemas(final List<String> schemas) {
        this.schemas = schemas;
    }

    public List<String> getCallbacks() {
        return callbacks;
    }

    public void setCallbacks(final List<String> callbacks) {
        this.callbacks = callbacks;
    }

    public String getMetaDataTableName() {
        return metaDataTableName;
    }

    public void setMetaDataTableName(final String metaDataTableName) {
        this.metaDataTableName = metaDataTableName;
    }

    public String getSqlMigrationPrefix() {
        return sqlMigrationPrefix;
    }

    public void setSqlMigrationPrefix(final String sqlMigrationPrefix) {
        this.sqlMigrationPrefix = sqlMigrationPrefix;
    }

    public String getSqlMigrationSuffix() {
        return sqlMigrationSuffix;
    }

    public void setSqlMigrationSuffix(final String sqlMigrationSuffix) {
        this.sqlMigrationSuffix = sqlMigrationSuffix;
    }

    public String getSqlMigrationSeparator() {
        return sqlMigrationSeparator;
    }

    public void setSqlMigrationSeparator(final String sqlMigrationSeparator) {
        this.sqlMigrationSeparator = sqlMigrationSeparator;
    }

    public String getPlaceholderPrefix() {
        return placeholderPrefix;
    }

    public void setPlaceholderPrefix(final String placeholderPrefix) {
        this.placeholderPrefix = placeholderPrefix;
    }

    public String getPlaceholderSuffix() {
        return placeholderSuffix;
    }

    public void setPlaceholderSuffix(final String placeholderSuffix) {
        this.placeholderSuffix = placeholderSuffix;
    }

    public Map<String, String> getPlaceholders() {
        return placeholders;
    }

    public void setPlaceholders(final Map<String, String> placeholders) {
        this.placeholders = placeholders;
    }

    public List<String> getLocations() {
        return locations;
    }

    public void setLocations(final List<String> locations) {
        this.locations = locations;
    }

    public List<String> getResolvers() {
        return resolvers;
    }

    public void setResolvers(final List<String> resolvers) {
        this.resolvers = resolvers;
    }

    public ClassLoader getClassLoader() {
        return classLoader;
    }

    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public boolean getOutOfOrder() {
        return outOfOrder;
    }

    public void setOutOfOrder(boolean outOfOrder) {
        this.outOfOrder = outOfOrder;
    }

    public String getBaselineDescription() {
        return baselineDescription;
    }

    public void setBaselineDescription(String baselineDescription) {
        this.baselineDescription = baselineDescription;
    }

    public boolean getBaselineOnMigrate() {
        return baselineOnMigrate;
    }

    public void setBaselineOnMigrate(boolean baselineOnMigrate) {
        this.baselineOnMigrate = baselineOnMigrate;
    }

    public boolean getValidateOnMigrate() {
        return validateOnMigrate;
    }

    public void setValidateOnMigrate(boolean validateOnMigrate) {
        this.validateOnMigrate = validateOnMigrate;
    }

    public Flyway build(final DataSource dataSource) {
        final String[] fwSchemas = new String[schemas.size()];
        final String[] fwCallbacks = new String[callbacks.size()];
        final String[] fwLocations = new String[locations.size()];
        final String[] fwResolvers = new String[resolvers.size()];

        final Flyway flyway = new Flyway();

        if (null != classLoader) {
            flyway.setClassLoader(classLoader);
        }

        flyway.setDataSource(dataSource);
        flyway.setEncoding(encoding);
        flyway.setTable(metaDataTableName);
        flyway.setSqlMigrationPrefix(sqlMigrationPrefix);
        flyway.setSqlMigrationSeparator(sqlMigrationSeparator);
        flyway.setSqlMigrationSuffix(sqlMigrationSuffix);
        flyway.setSchemas(schemas.toArray(fwSchemas));
        flyway.setCallbacksAsClassNames(callbacks.toArray(fwCallbacks));
        flyway.setPlaceholderPrefix(placeholderPrefix);
        flyway.setPlaceholderSuffix(placeholderSuffix);
        flyway.setPlaceholders(placeholders);
        flyway.setLocations(locations.toArray(fwLocations));
        flyway.setResolversAsClassNames(resolvers.toArray(fwResolvers));
        flyway.setOutOfOrder(outOfOrder);
        flyway.setBaselineDescription(baselineDescription);
        flyway.setBaselineOnMigrate(baselineOnMigrate);
        flyway.setValidateOnMigrate(validateOnMigrate);

        return flyway;
    }
}
