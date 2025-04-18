package com.example.community_management.config

import com.zaxxer.hikari.HikariDataSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
class DatabaseConfig {

    private val env = EnvLoader.loadEnv() // Load environment variables

    @Bean
    fun dataSource(): DataSource {
        val dataSource = HikariDataSource()
        dataSource.driverClassName = "org.postgresql.Driver"
        dataSource.jdbcUrl = env.getProperty("DB_URL")
        dataSource.username = env.getProperty("DB_USERNAME")
        dataSource.password = env.getProperty("DB_PASSWORD")

        // Ensure the database exists
        ensureDatabaseExists(dataSource)

        return dataSource
    }

    private fun ensureDatabaseExists(dataSource: HikariDataSource) {
        val adminDataSource = HikariDataSource().apply {
            driverClassName = "org.postgresql.Driver"
            jdbcUrl = "jdbc:postgresql://localhost:5432/postgres" // Default PostgreSQL database
            username = env.getProperty("DB_USERNAME")
            password = env.getProperty("DB_PASSWORD")
        }

        val dbName = extractDatabaseNameFromUrl(dataSource.jdbcUrl)
        val sql = "SELECT 1 FROM pg_database WHERE datname = '$dbName'"
        val resultSet = adminDataSource.connection.createStatement().executeQuery(sql)

        if (!resultSet.next()) {
            adminDataSource.connection.createStatement().execute("CREATE DATABASE $dbName")
        }

        adminDataSource.close()
    }

    private fun extractDatabaseNameFromUrl(url: String?): String {
        return url?.substringAfterLast("/") ?: throw IllegalArgumentException("Invalid DB_URL")
    }
}