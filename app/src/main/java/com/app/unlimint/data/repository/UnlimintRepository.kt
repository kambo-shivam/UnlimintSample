package com.app.unlimint.data.repository

import com.app.unlimint.data.api.ApiHelper
import com.app.unlimint.data.model.JokesModel
import com.app.unlimint.database.JokesDao

class UnlimintRepository(
    private val apiHelper: ApiHelper,
    private val jokesDao: JokesDao? = null
) {

    suspend fun getUsers() = apiHelper.getUsers()

    fun insertJoke(user: JokesModel) {
        jokesDao!!.insertJoke(user)
    }

    fun getAllJokes(): List<JokesModel> {
        return jokesDao!!.getAllJokes()
    }

    fun deleteFirstJoke(joke: JokesModel?) {
        jokesDao?.deleteJoke(joke)
    }
}