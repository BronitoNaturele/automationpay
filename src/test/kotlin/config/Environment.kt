//Перечисления для окружений (LOCAL, STAGING, PROD).
//Логика переключения конфигураций.
package config

import TestConfig

//enum class Environment — перечисление возможных сред (стендов).
enum class Environment {
    TEST, // локальный стенд
    UAT, // стейдж-стенд
    PROD  // продакшн-стенд
}

object EnvironmentConfig {
    private val baseUrls = mapOf(
        Environment.TEST to "https://test-pay.av.ru",
        Environment.UAT to "https://uat-pay.av.ru",
        Environment.PROD to "https://pay.av.ru"
    )

    fun getConfig(env: Environment): TestConfig {
        val baseUrl = System.getenv("API_BASE_URL") ?: baseUrls[env]
        val authToken = System.getenv("API_TOKEN") ?: "e26ABDDy9HTV0gFoX1uCwdld9uSSjEYlrV7v0qrs2OfZOONm223XLMLK9GyPDMJFpmMIQLSPkG9XfCzT"
        return TestConfig(baseUrl!!, authToken)
    }
}

fun getConfigFromEnvVar(): TestConfig {
    val envStr = System.getenv("ENV") ?: "LOCAL"
    val env = Environment.valueOf(envStr)
    return getConfig(env)
}

//getConfig(env: Environment) — функция, которая:
//сначала проверяет переменную окружения API_BASE_URL;
//если её нет — берёт URL из карты baseUrls по переданному env;
//создаёт и возвращает объект TestConfig с нужным URL и токеном.
fun getConfigFromEnvVar(): TestConfig {
    val envStr = System.getenv("ENV") ?: "TEST"
    val env = Environment.valueOf(envStr)
    return getConfig(env)
}