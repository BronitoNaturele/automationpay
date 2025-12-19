class ApiClient(private val baseUrl: String) {
    fun get(path: String, headers: Map<String, String> = emptyMap()): Response
    fun post(path: String, body: Any, headers: Map<String, String> = emptyMap()): Response
    // и т. д.
}