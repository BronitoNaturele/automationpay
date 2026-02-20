//Утилитные классы (utils.JsonUtils.JsonUtils, DateUtils и т.п.). Помогают сериализовать/десериализовать данные, генерировать тестовые значения и т.д.

package pay.xprojectdata.utils.jsonutils

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule


object JsonUtils {
    private val mapper = ObjectMapper()
        .registerKotlinModule()
        .findAndRegisterModules()

    //Десериализует JSON‑строку в объект заданного типа.
    fun <T : Any> fromJson(json: String, clazz: Class<T>): T {
        try {
            return mapper.readValue(json, clazz)
        } catch (e: Exception) {
            throw IllegalArgumentException("Failed to deserialize JSON: $json", e)
        }
    }

    //Сериализует объект в JSON‑строку.
    fun toJson(obj: Any): String {
        try {
            return mapper.writeValueAsString(obj)
        } catch (e: Exception) {
            throw IllegalArgumentException("Failed to serialize object: $obj", e)
        }
    }

    //Десериализация JSON в объект заданного типа (с поддержкой дженериков)
    fun <T> fromJsonWithTypeReference(json: String, typeRef: TypeReference<T>): T {
        try {
            return mapper.readValue(json, typeRef)
        } catch (e: Exception) {
            throw IllegalArgumentException("Failed to deserialize JSON: $json", e)
        }
    }
}