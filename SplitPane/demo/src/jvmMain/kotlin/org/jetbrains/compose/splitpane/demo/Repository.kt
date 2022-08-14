package org.jetbrains.compose.splitpane.demo

import ResponseModel
import io.github.matrixkt.client.MatrixConfig
import io.github.matrixkt.client.rpc
import io.github.matrixkt.clientserver.api.RedactEvent
import io.github.matrixkt.clientserver.api.SendMessage
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.gson.*
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put


class Repository(
    private val client: HttpClient = defaultHttpClient,
) {


    suspend fun getRandomUser(): ResponseModel = client.get(
        "https://randomuser.me/api/"
    ).body()

    private val mClient = HttpClient(CIO) {
        MatrixConfig(baseUrl = Url("matrix.org").toString())
    }

    suspend fun checkMyImp() {

        val accessToken = "Super secure Token"

        val roomId = "!QtykxKocfZaZOUrTwp:matrix.org"

        val response = mClient.rpc(SendMessage(SendMessage.Url(roomId, "m.room.message", "nonce"), buildJsonObject {
            put("msgtype", "m.text")
            put("body", "Hello World!")
        }), accessToken)
        val eventId = response.eventId

        mClient.rpc(
            RedactEvent(
                RedactEvent.Url(roomId, eventId, "nonce2"), RedactEvent.Body(reason = "Was a bot!")
            ), accessToken
        )
    }

    companion object {
        val defaultHttpClient = HttpClient(CIO) {
            install(ContentNegotiation) {
                gson()
            }
        }
    }
}

