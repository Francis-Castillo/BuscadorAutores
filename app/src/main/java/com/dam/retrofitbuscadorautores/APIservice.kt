package com.dam.retrofitbuscadorautores

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url

interface APIservice {
    @GET
    suspend fun getAutores(@Url url:String): Response<AuthorsResponse>
}

object RetrofitClient {
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://openlibrary.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val api:APIservice = retrofit.create(APIservice::class.java)
}
