package com.singhastudios.momento.services

import android.util.Log
import com.singhastudios.momento.data.LoginRequest
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

suspend fun loginUser(email: String, password: String): String {
    val client = HttpClient {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }

    val url = "https://2db89ec3-d86a-48ea-b7e3-2fd0eab84602-dev.e1-us-east-azure.choreoapis.dev/memento/itemservice/login-d43/v1.0"

    try {
        val response: HttpResponse = client.post(url) {
            contentType(ContentType.Application.Json)
            setBody(LoginRequest(email, password)) // Send the login request body
        }
        return response.bodyAsText()
    } catch (e: Exception) {
        Log.e("LoginError", "Error logging in: ${e.message}")
        return "Error: ${e.message}"
    } finally {
        client.close()
    }
}
