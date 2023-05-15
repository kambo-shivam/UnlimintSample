package com.app.unlimint.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.unlimint.data.model.JokesModel


@Database(entities = [JokesModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun jokesDao(): JokesDao

}