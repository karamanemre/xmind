package com.xmind.config;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DatabaseHealthIndicator extends AbstractHealthIndicator {

    private final DataSource dataSource;

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        try (Connection conn = dataSource.getConnection()) {
            if (conn.isValid(1000)) {
                builder.up()
                        .withDetail("database", "Available")
                        .withDetail("status", "Connected");
            } else {
                builder.down()
                        .withDetail("database", "Unavailable")
                        .withDetail("error", "Invalid connection");
            }
        } catch (SQLException e) {
            builder.down()
                    .withDetail("database", "Unavailable")
                    .withDetail("error", e.getMessage());
        }
    }
}
