package com.singhastudios.momento.services

import android.util.Log
import com.singhastudios.momento.data.RegisterRequest
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

suspend fun registerUser(username: String, password: String): String {
    val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }
    }

    val url = "https://2db89ec3-d86a-48ea-b7e3-2fd0eab84602-dev.e1-us-east-azure.choreoapis.dev/memento/itemservice/register-561/v1.0"

    val request = RegisterRequest(username = username, password = password)
    var res = ""

    withContext(Dispatchers.IO) {
        client.use {
            try {
                val response = it.post(url) {
                    contentType(ContentType.Application.Json)
                    setBody(request)
                }
                res = response.bodyAsText() // Get the response text
                Log.d("RegisterUser", "Response: $res")
            } catch (e: Exception) {
                Log.e("RegisterUser", "Error: ${e.message}")
                res = "Error: ${e.message}"
            }
        }
    }

    return res
}
