package tech.pcloud.configure.center.server.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class ReadDbPropertiesPostProcessor implements EnvironmentPostProcessor {
    private static final Logger log = LoggerFactory.getLogger(ReadDbPropertiesPostProcessor.class);

    private static final String GLOBAL_SERVICE_NAME = "global";
    private static final String PROPERTY_SOURCE_NAME = "databaseProperties";
    private static final String PROPERTY_SERVICE_NAME = "configure.center.serviceName";
    private static final String PROTERTY_JDBC_DRIVER = "configure.center.db.driver";
    private static final String PROTERTY_JDBC_URL = "configure.center.db.url";
    private static final String PROTERTY_JDBC_USERNAME = "configure.center.db.username";
    private static final String PROTERTY_JDBC_PASSWORD = "configure.center.db.password";

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication springApplication) {
        Map<String, Object> propertySource = new HashMap<>();
        String serviceName = environment.getProperty(PROPERTY_SERVICE_NAME);
        String dbDriver = environment.getProperty(PROTERTY_JDBC_DRIVER);
        String dbUrl = environment.getProperty(PROTERTY_JDBC_URL);
        String dbUsername = environment.getProperty(PROTERTY_JDBC_USERNAME);
        String dbPassword = environment.getProperty(PROTERTY_JDBC_PASSWORD);
        try {
            Class.forName(dbDriver);
            Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);

            log.info("connect configure database " + environment.getProperty("lms.configure.db.url") + " success");

            Map<String, Long> services = loadServiceId(connection, serviceName);
            Map<String, Long> profiles = loadProfiles(connection);
            for (Long serviceId : services.values()) {
                if (environment.getActiveProfiles() != null) {
                    for (String activeProfile : environment.getActiveProfiles()) {
                        addProperty(connection, serviceId, profiles.get(activeProfile.toLowerCase().trim()), propertySource);
                    }
                }
            }
            connection.close();
            environment.getPropertySources().addFirst(new MapPropertySource(PROPERTY_SOURCE_NAME, propertySource));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    private Map<String, Long> loadServiceId(Connection connection, final String serviceName) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT id,name FROM service order by id");
        ResultSet rs = preparedStatement.executeQuery();
        Map<String, Long> services = new HashMap<>();
        while (rs.next()) {
            long id = rs.getLong("id");
            String name = rs.getString("name").trim();
            if (name.equals(serviceName)
                    || name.equals(GLOBAL_SERVICE_NAME)) {
                services.put(name, id);
            }
        }
        rs.close();
        preparedStatement.close();
        return services;
    }

    private Map<String, Long> loadProfiles(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, name FROM profile");
        ResultSet rs = preparedStatement.executeQuery();
        Map<String, Long> result = new HashMap<>();
        while (rs.next()) {
            result.put(rs.getString("name").trim().toLowerCase(), rs.getLong("id"));
        }
        rs.close();
        preparedStatement.close();
        return result;
    }

    private void addProperty(Connection connection, long serviceId, long profileId, Map<String, Object> propertySource) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT name, value FROM configure WHERE profile_id = ? and service_id=?");
        preparedStatement.setLong(1, profileId);
        preparedStatement.setLong(2, serviceId);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            propertySource.put(rs.getString("name"), rs.getString("value"));
        }
        rs.close();
        preparedStatement.close();
        log.info("load configure with profileId: " + profileId + ", serviceId: " + serviceId + " success");
    }
}
