//Утилитные классы
//JsonUtils, DateUtils и т. п.
//Помогают сериализовать/десериализовать данные, генерировать тестовые значения и т. д.

object JsonUtils {
    fun toJson(obj: Any): String = // реализация через Gson/Moshi
    fun <T> fromJson(json: String, clazz: Class<T>): T = // ...
}