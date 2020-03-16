package com.example.rps

import android.content.Context

class GameRepo(context: Context) {
    private val gameDao : GameDAO

    init {
        val database =
            RpsRoomDatabase.getDatabase(
                context
            )
        gameDao = database!!.gameDao()
    }

    suspend fun getAllGames():List<Game>{
        return gameDao.getAllGames()
    }

    suspend fun insertGame(game: Game){
        gameDao.insertGame(game)
    }

    suspend fun deleteAllGames(){
        gameDao.deleteAllGames()
    }
}