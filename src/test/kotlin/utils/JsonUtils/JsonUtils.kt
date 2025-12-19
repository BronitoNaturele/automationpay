//Утилитные классы
//utils.JsonUtils.JsonUtils, DateUtils и т. п.
//Помогают сериализовать/десериализовать данные, генерировать тестовые значения и т. д.

package utils.JsonUtils

object JsonUtils {
    fun toJson(obj: Any): String = // реализация через Gson/Moshi
    fun <T> fromJson(json: String, clazz: Class<T>): T = // ...
}