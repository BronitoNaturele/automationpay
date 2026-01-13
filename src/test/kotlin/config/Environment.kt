//Перечисления для окружений (LOCAL, STAGING, PROD).
//Логика переключения конфигураций.
package config

//enum class Environment — перечисление возможных сред (стендов).
enum class Environment {
    TEST, // test стенд
    UAT, // uat-стенд
    PROD  // продакшн-стенд
}

object EnvironmentConfig {
    private val baseUrls = mapOf(
        Environment.TEST to "https://test-pay.av.ru",
        Environment.UAT to "https://uat-pay.av.ru",
        Environment.PROD to "https://pay.av.ru"
    )

    fun getConfig(env: Environment): TestConfig {
        //getenv() — метод, который ищет переменную окружения по имени. Код пытается получить baseUrl в три этапа, используя оператор ?: («элвис»):
        //Сначала ищет переменную окружения API_BASE_URL.
        //Если не нашёл — берёт URL из карты baseUrls по ключу env.
        //Если и там нет — выбрасывает исключение с описанием ошибки.
        val baseUrl = System.getenv("API_BASE_URL")
            ?: baseUrls[env]
            ?: throw IllegalArgumentException("Base URL не найден в environment: $env")

        val authToken = System.getenv("API_TOKEN")
            ?: System.getProperty("API_TOKEN")  // добавляем поддержку -DAPI_TOKEN
            ?: throw IllegalArgumentException("API_TOKEN не задан")

        return TestConfig(baseUrl, authToken)
    }
    //getConfig(env: Environment) — функция, которая:
    //сначала проверяет переменную окружения API_BASE_URL;
    //если её нет — берёт URL из карты baseUrls по переданному env;
    //создаёт и возвращает объект TestConfig с нужным URL и токеном.
    fun getConfigFromEnvVar(): TestConfig {
        val envStr = System.getenv("ENV") ?: "TEST"
        val env = Environment.values().find { it.name == envStr }
            ?: throw IllegalArgumentException("Unknown environment: $envStr")
        return getConfig(env)
    }
}