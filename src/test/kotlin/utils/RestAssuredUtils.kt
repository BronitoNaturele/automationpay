object RestAssuredUtils {
    init {
        RestAssured.baseURI = System.getProperty("base_url") // из env.properties
        RestAssured.contentType = ContentType.JSON
        RestAssured.accept = ContentType.JSON
    }

    fun setupAuthHeader(token: String) {
        RestAssured.headers("Authorization" to "Bearer