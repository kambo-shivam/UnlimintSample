package com.app.unlimint.data.api

import com.app.unlimint.data.model.JokesModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("api")
    suspend fun getUsers(@Query("format") format: String): Response<JokesModel>

}