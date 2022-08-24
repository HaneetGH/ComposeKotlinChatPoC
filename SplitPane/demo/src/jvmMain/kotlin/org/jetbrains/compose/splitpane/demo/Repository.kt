package org.jetbrains.compose.splitpane.demo

import ResponseModel
import io.github.matrixkt.client.MatrixConfig
import io.github.matrixkt.client.rpc
import io.github.matrixkt.clientserver.api.RedactEvent
import io.github.matrixkt.clientserver.api.SendMessage
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.apache.*
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
        val client = HttpClient(Apache) {
            MatrixConfig(baseUrl = Url("matrix.org").toString())
        }
        val accessToken = "syt_aGFuZWV0_tcXOIBcgjxiMoETpkSGH_1j59WU"

        val roomId = "!qQtQBwAAEIjFizNvBF:matrix.org"
        var rpc = SendMessage(SendMessage.Url(roomId, "m.room.message", "nonce"), buildJsonObject {
            put("msgtype", "m.text")
            put("body", "Hello World!")
        })
        val response = client.rpc(rpc, accessToken)
        val eventId = response.eventId
        var rpc1 = RedactEvent(
            RedactEvent.Url(roomId, eventId, "nonce2"), RedactEvent.Body(reason = "Was a bot!")
        )
        client.rpc(
            rpc1, accessToken
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

