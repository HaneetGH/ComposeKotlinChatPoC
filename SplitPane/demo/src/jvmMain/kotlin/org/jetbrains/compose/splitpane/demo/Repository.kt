package org.jetbrains.compose.splitpane.demo

import ResponseModel
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*


class Repository(
    private val client: HttpClient = defaultHttpClient,
) {


    suspend fun getRandomUser(): ResponseModel = client.get(
        "https://randomuser.me/api/"
    )


    companion object {
        val defaultHttpClient = HttpClient(CIO) {
            install(JsonFeature) {
                serializer = KotlinxSerializer()
            }
        }
    }
}
