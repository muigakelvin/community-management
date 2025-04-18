package com.example.community_management.config

import java.io.File
import java.util.Properties

object EnvLoader {
    fun loadEnv(): Properties {
        val properties = Properties()
        val envFile = File("src/main/resources/.env") // Path to .env file
        if (envFile.exists()) {
            envFile.inputStream().use { inputStream ->
                properties.load(inputStream)
            }
        } else {
            throw IllegalStateException(".env file not found at src/main/resources/.env")
        }
        return properties
    }
}