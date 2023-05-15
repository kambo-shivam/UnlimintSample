package com.app.unlimint.database

import androidx.room.*
import com.app.unlimint.data.model.JokesModel

@Dao
interface JokesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertJoke(user: JokesModel)

    @Query("SELECT * FROM jokes_table")
    fun getAllJokes(): List<JokesModel>

    @Delete
    fun deleteJoke(entity: JokesModel?)

}