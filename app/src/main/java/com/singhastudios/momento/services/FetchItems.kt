package com.singhastudios.momento.services

import android.util.Log
import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.*
import io.ktor.client.statement.bodyAsText
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

suspend fun fetchCollectibles(): String {
    val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }
    }

    val url = "https://2db89ec3-d86a-48ea-b7e3-2fd0eab84602-dev.e1-us-east-azure.choreoapis.dev/memento/itemservice/v1.0"
    var res = ""

    withContext(Dispatchers.IO) {
        client.use {
            val rawResponse: String = it.get(url).bodyAsText()
            res = rawResponse
            Log.d("FetchCollectibles", "Raw Response: $rawResponse")
        }
    }

    return res
}