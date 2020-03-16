package com.example.rps

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.content_main.view.ivComputer
import kotlinx.android.synthetic.main.content_main.view.ivYou
import kotlinx.android.synthetic.main.content_main.view.tvResult
import kotlinx.android.synthetic.main.item_history.view.*

class GameAdapter(private val games: List<Game> ) : RecyclerView.Adapter<GameAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent,false)
        )
    }

    override fun getItemCount(): Int = games.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(games[position])

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(game: Game) {
            when(game.gameResult){
                0 -> itemView.tvResult.text = "You win!"
                1 -> itemView.tvResult.text ="Computer wins!"
                2 -> itemView.tvResult.text = "Draw!"
            }
            itemView.ivComputer.setImageResource(Game.GAME_RES_DRAWABLES_IDS[game.computerMove])
            itemView.ivYou.setImageResource(Game.GAME_RES_DRAWABLES_IDS[game.yourMove])
            itemView.tvDate.text = game.gameDate.toString()
        }
    }
}