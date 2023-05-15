package com.app.unlimint.data.api

import com.app.unlimint.data.model.JokesModel
import retrofit2.Response

interface ApiHelper {

    suspend fun getUsers(): Response<JokesModel>
}