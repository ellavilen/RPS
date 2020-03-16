package com.example.rps

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface GameDAO {
    @Query("SELECT * FROM game_table")
    suspend fun getAllGames(): List <Game>

    @Insert
    suspend fun insertGame(game:Game)

    @Query("DELETE FROM game_table")
    suspend fun deleteAllGames()
}