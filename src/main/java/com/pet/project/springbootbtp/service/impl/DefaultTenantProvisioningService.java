package com.pet.project.springbootbtp.service.impl;

import com.pet.project.springbootbtp.exceptions.TenantProvisionException;
import com.pet.project.springbootbtp.service.TenantProvisioningService;
import com.pet.project.springbootbtp.util.TenantUtil;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Pattern;

@Service
@Slf4j
@RequiredArgsConstructor
public class DefaultTenantProvisioningService implements TenantProvisioningService {

    public static final String LIQUIBASE_PATH = "db/changelog/db.changelog-master.yaml";
    private static final Pattern TENANT_PATTERN = Pattern.compile("[-\\w]+");
    private final DataSource dataSource;

    @Override
    public void subscribeTenant(final String tenantId) {
        String defaultSchemaName;
        try {
            Validate.isTrue(isValidTenantId(tenantId), String.format("Invalid tenant id: \"%s\"", tenantId));
            final String schemaName = TenantUtil.createSchemaName(tenantId);

            final Connection connection = dataSource.getConnection();
//            final Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            try (Statement statement = connection.createStatement()) {
                statement.execute(String.format("CREATE SCHEMA IF NOT EXISTS \"%s\"", schemaName));
                connection.close();
//                connection.commit();
//
//                defaultSchemaName = database.getDefaultSchemaName();
//                database.setDefaultSchemaName(schemaName);
//
//                final Liquibase liquibase = new liquibase.Liquibase(LIQUIBASE_PATH,
//                        new ClassLoaderResourceAccessor(), database);
//
//                liquibase.update(new Contexts(), new LabelExpression());
//                database.setDefaultSchemaName(defaultSchemaName);
            }

            runLiquibaseScript(dataSource.getConnection(), schemaName);

        } catch (SQLException | IllegalArgumentException e) {
            log.error("Tenant subscription failed for {}. with error {}", tenantId, e.getMessage(), e);
            throw new TenantProvisionException("Tenant subscription failed for " + tenantId + ". with error " + e.getMessage(), e);
        }
    }

    private void runLiquibaseScript(Connection connection, String schemaName) {
        try {
            connection.setSchema(schemaName);
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            Liquibase liquibase = new Liquibase(LIQUIBASE_PATH, new ClassLoaderResourceAccessor(), database);
            liquibase.update(new Contexts(), new LabelExpression());
            connection.commit();
            connection.close();
            log.info("Initial script for schema: {} was performed successfully", schemaName);
        } catch (Exception e) {
            throw new TenantProvisionException(e);
        }
    }

    @Override
    public void unsubscribeTenant(final String tenantId) {
        try {
            Validate.isTrue(isValidTenantId(tenantId), String.format("Invalid tenant id: \"%s\"", tenantId));
            final String schemaName = TenantUtil.createSchemaName(tenantId);
            final Connection connection = dataSource.getConnection();
            try (Statement statement = connection.createStatement()) {
                statement.execute(String.format("DROP SCHEMA IF EXISTS \"%s\" CASCADE", schemaName));
            }
        } catch (SQLException | IllegalArgumentException e) {
            log.error("Tenant unsubscription failed for {}.", tenantId, e);
        }
    }

    private boolean isValidTenantId(final String tenantId) {
        return tenantId != null && TENANT_PATTERN.matcher(tenantId).matches();
    }
}