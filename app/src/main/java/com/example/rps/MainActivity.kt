package com.example.rps

import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var gameRepo: GameRepo
    private val mainScope = CoroutineScope(Dispatchers.Main)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gameRepo = GameRepo(this)

        //create variables of buttons and give onclick functions
        val rock: ImageButton = findViewById(R.id.ibRock)
        val paper: ImageButton = findViewById(R.id.ibPaper)
        val scissors: ImageButton = findViewById(R.id.ibScissors)

        rock.setOnClickListener{playGame(0)}
        paper.setOnClickListener{playGame(1)}
        scissors.setOnClickListener{playGame(2)}

        //  ROCK = 0, PAPER = 1, SCISSORS = 2
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_history -> {
                startHistoryActivity()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun playGame(yourChoice:Int){
        //RESULTS: 0 = WIN, 1 = LOSE, 2 = DRAW

        val cpuChoice = (0..2).random() //chooce random option for cpu
        var result = -1

        ivComputer.setImageResource(Game.GAME_RES_DRAWABLES_IDS[cpuChoice])
        ivYou.setImageResource(Game.GAME_RES_DRAWABLES_IDS[yourChoice])
        //sets the images to match choices

        if(yourChoice == 0 && cpuChoice == 1){
            result = 1
        } else if (yourChoice == 1 && cpuChoice == 0){
            result = 0
        }else if(yourChoice == 1 && cpuChoice == 2){
            result = 1
        }else if(yourChoice == 2 && cpuChoice == 1){
            result = 0
        }else if(yourChoice == 0 && cpuChoice == 2){
            result = 0
        }else if(yourChoice == 2 && cpuChoice == 0){
            result = 1
        }else if(yourChoice == cpuChoice){
            result = 2
        }

        when(result){
            0 -> tvResult.text = "You win!"
            1 -> tvResult.text = "Computer wins!"
            2 -> tvResult.text = "Draw!"
        }

        mainScope.launch {
            val game = Game(gameDate = Date(), computerMove = cpuChoice, yourMove = yourChoice, gameResult = result)
            withContext(Dispatchers.IO){
                gameRepo.insertGame(game)
            }
        }
    }

    private fun startHistoryActivity() {
        val intent = Intent(this, GameHistoryActivity::class.java)
        startActivity(intent)
    }
}
