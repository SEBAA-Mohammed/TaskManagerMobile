package com.ms.taskmanagermobile.data.api

import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit

// Configuration Retrofit pour les appels API REST
object RetrofitClient {
    private const val BASE_URL = "http://192.168.1.105:3000/"

    private val localDateTimeAdapter = object : JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {
        override fun serialize(src: LocalDateTime, typeOfSrc: Type, context: JsonSerializationContext) =
            JsonPrimitive(src.format(DateTimeFormatter.ISO_DATE_TIME))

        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext) =
            LocalDateTime.parse(json.asString, DateTimeFormatter.ISO_DATE_TIME)
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    val taskApi: TaskApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(
            GsonBuilder()
                .registerTypeAdapter(LocalDateTime::class.java, localDateTimeAdapter)
                .create()
        ))
        .build()
        .create(TaskApi::class.java)
} 