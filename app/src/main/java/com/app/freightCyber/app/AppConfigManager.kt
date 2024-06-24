package com.app.freightCyber.app

import com.app.freightCyber.BuildConfig


enum class Environment {
    Dev, Staging, Prod
}

val Environment.isProd: Boolean
    get() = this == Environment.Prod

val Environment.isDev: Boolean
    get() = this == Environment.Dev

val Environment.isStaging: Boolean
    get() = this == Environment.Staging


data class AppConfig(val baseUrl: String, val googleMapKey: String)
object AppConfigManager {

    //This getter will return the current environment
    val environment: Environment = when (BuildConfig.FLAVOR) {
        "dev" -> Environment.Dev
        "staging" -> Environment.Staging
        "prod" -> Environment.Prod
        else -> throw Exception("Invalid flavor")
    }


    //This getter will return the current AppConfig
    val config: AppConfig = getConfigByEnvironment(environment)


    /**
     * This functions can be used to get config by environment
     *
     * @param env Pass the environment i.e. Environment.Dev
     * @return AppConfig
     */
    fun getConfigByEnvironment(env: Environment): AppConfig {
        return when (env) {
            Environment.Dev -> {
                AppConfig("https://dev.freightcyber.net/", "googleMapKey")
            }

            Environment.Staging -> {
                AppConfig("https://dev.freightcyber.net/", "googleMapKey")
            }

            Environment.Prod -> {
                AppConfig("https://dev.freightcyber.net/", "googleMapKey")
            }
        }
    }

}