package com.app.unlimint.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "jokes_table")
data class JokesModel(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var joke: String? = null
)