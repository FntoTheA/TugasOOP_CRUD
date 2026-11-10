package com.aritmatika.demo.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.jdbc.autoconfigure.DataSourceProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseAutoCreator implements BeanPostProcessor {

    private static final Logger log = LoggerFactory.getLogger(DatabaseAutoCreator.class);
    private static final Pattern PG_URL_PATTERN = Pattern.compile("^(jdbc:postgresql://[^/]+/)([^?]+)(.*)$");

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof DataSourceProperties props) {
            ensureDatabaseExists(props.getUrl(), props.getUsername(), props.getPassword());
        }
        return bean;
    }

    private void ensureDatabaseExists(String url, String username, String password) {
        if (url == null || !url.startsWith("jdbc:postgresql://")) {
            return;
        }
        Matcher matcher = PG_URL_PATTERN.matcher(url);
        if (!matcher.matches()) {
            return;
        }

        String baseUrl = matcher.group(1);
        String targetDb = matcher.group(2);
        String params = matcher.group(3);
        String adminUrl = baseUrl + "postgres" + (params != null ? params : "");

        try (Connection conn = DriverManager.getConnection(adminUrl, username, password)) {
            boolean exists;
            try (PreparedStatement ps = conn.prepareStatement("SELECT 1 FROM pg_database WHERE datname = ?")) {
                ps.setString(1, targetDb);
                try (ResultSet rs = ps.executeQuery()) {
                    exists = rs.next();
                }
            }
            if (!exists && targetDb.matches("^[a-zA-Z0-9_]+$")) {
                log.info("Database '{}' belum ada. Membuat database secara otomatis...", targetDb);
                try (Statement stmt = conn.createStatement()) {
                    stmt.execute("CREATE DATABASE " + targetDb);
                }
                log.info("Database '{}' berhasil dibuat.", targetDb);
            }
        } catch (Exception e) {
            log.debug("Lewati auto-create database '{}': {}", targetDb, e.getMessage());
        }
    }
}

