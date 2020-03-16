package com.example.rps

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Game::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)

abstract class RpsRoomDatabase : RoomDatabase() {
    abstract fun gameDao(): GameDAO

    companion object {
        private const val DATABASE_NAME = "RPS_LIST_DATABASE"

        @Volatile
        private var rpsRoomDatabaseInstance: RpsRoomDatabase? = null

        fun getDatabase(context: Context) : RpsRoomDatabase? {
            if (rpsRoomDatabaseInstance == null){
                synchronized(RpsRoomDatabase::class.java)
                {

                    if(rpsRoomDatabaseInstance == null) {
                        rpsRoomDatabaseInstance = Room.databaseBuilder(context.applicationContext,
                            RpsRoomDatabase::class.java,
                            DATABASE_NAME
                        ).build()
                    }
                }
            }
            return rpsRoomDatabaseInstance
        }
    }
}