package com.app.unlimint.data.api

import com.app.unlimint.data.model.JokesModel
import retrofit2.Response

class ApiHelperImpl(private val apiService: ApiService) : ApiHelper {

    override suspend fun getUsers(): Response<JokesModel> = apiService.getUsers("json")

}