//Утилитные классы
//utils.JsonUtils.JsonUtils, DateUtils и т. п.
//Помогают сериализовать/десериализовать данные, генерировать тестовые значения и т. д.

package utils.JsonUtils

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule

/**
 * Утилиты для работы с JSON (сериализация/десериализация).
 */
object JsonUtils {
    private val mapper = ObjectMapper()
        .registerKotlinModule()
        .findAndRegisterModules()

    /**
     * Десериализует JSON‑строку в объект заданного типа.
     */
    fun <T : Any> fromJson(json: String, clazz: Class<T>): T {
        return mapper.readValue(json, clazz)
    }

    /**
     * Сериализует объект в JSON‑строку.
     */
    fun toJson(obj: Any): String {
        return mapper.writeValueAsString(obj)
    }
}