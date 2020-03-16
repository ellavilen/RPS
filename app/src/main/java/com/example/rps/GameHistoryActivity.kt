package com.example.rps

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_game_history.*
import kotlinx.android.synthetic.main.content_game_history.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GameHistoryActivity : AppCompatActivity() {

    private val games = arrayListOf<Game>()
    private val gameAdapter = GameAdapter(games)

    private lateinit var gameRepo: GameRepo
    private val mainScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_history)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        gameRepo = GameRepo(this)

        initView()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_history, menu)
        return true
    }

    override fun onOptionsItemSelected(item : MenuItem): Boolean {
        return when (item.itemId){
            R.id.action_clear -> {
                clearGameHistory()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initView(){
        rvGameHistory.layoutManager = LinearLayoutManager(this@GameHistoryActivity, RecyclerView.VERTICAL, false)
        rvGameHistory.adapter = gameAdapter
        rvGameHistory.addItemDecoration(DividerItemDecoration(this@GameHistoryActivity, DividerItemDecoration.VERTICAL))
        getGameHistory()
    }

    private fun getGameHistory(){
        mainScope.launch {
            val gameList = withContext(Dispatchers.IO) {
                gameRepo.getAllGames()
            }
            this@GameHistoryActivity.games.clear()
            this@GameHistoryActivity.games.addAll(gameList)
            this@GameHistoryActivity.games.reverse()
            this@GameHistoryActivity.gameAdapter.notifyDataSetChanged()
        }
    }

    private fun clearGameHistory(){
        mainScope.launch {
            withContext(Dispatchers.IO){
                gameRepo.deleteAllGames()
            }
            getGameHistory()
            Toast.makeText(this@GameHistoryActivity, "Game history cleared", Toast.LENGTH_SHORT).show()
        }
    }

}
