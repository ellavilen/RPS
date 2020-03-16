package com.example.rps

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "game_table")
data class Game (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null,

    @ColumnInfo(name = "gameDate")
    var gameDate: Date,

    @ColumnInfo(name = "computerMove")
    var computerMove: Int,

    @ColumnInfo(name = "yourMove")
    var yourMove: Int,

    @ColumnInfo(name = "gameResult")
    var gameResult: Int

    ) : Parcelable {
    companion object {
        //val GAME_OPTIONS = arrayOf("rock", "paper", "scissors")
        val GAME_RES_DRAWABLES_IDS = arrayOf(R.drawable.rock, R.drawable.paper, R.drawable.scissors)
    }
}
