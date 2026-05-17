package com.example.dexfold.core.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import com.example.dexfold.data.remote.PokemonApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {

    // 👇 OkHttp es el cliente HTTP que usa Retrofit por debajo
    // Aquí lo configuramos con un interceptor para ver los logs
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(
            HttpLoggingInterceptor().apply {
                // 👇 En debug vemos todo: headers, body, etc.
                // ⚠️ NUNCA uses BODY en producción, expone datos sensibles
                level = HttpLoggingInterceptor.Level.BODY
            }
        )
        .connectTimeout(NetworkConstants.TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .readTimeout(NetworkConstants.TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .build()

    // 👇 Instancia de Retrofit lista para usar
    val instance: PokemonApiService by lazy {
        Retrofit.Builder()
            .baseUrl(NetworkConstants.BASE_URL)
            // 👇 Gson convierte el JSON a nuestros DTOs automáticamente
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(PokemonApiService::class.java)
    }
}