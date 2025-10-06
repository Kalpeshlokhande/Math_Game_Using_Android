package com.techmaina.math_game

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.random.Random
import java.util.*

class GameActivity : AppCompatActivity() {
    lateinit var textScore: TextView
    lateinit var  textLife: TextView
    lateinit var textTime: TextView

    lateinit var  textQuestion: TextView
    lateinit var editTextAnswer: EditText
    lateinit var  button: Button
    lateinit var buttonNext: Button

    var correctAnswer =0
    var userScore =0
    var userLife = 3
    lateinit var  timer: CountDownTimer
    private val startTimerInMills:Long=60000
    var timeLeftInMills:Long = startTimerInMills

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(R.layout.activity_game)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
        supportActionBar!!.title ="Addition"
        textScore=findViewById(R.id.textViewScore)
        textLife=findViewById(R.id.textViewLife)
        textTime=findViewById(R.id.textViewTime)
        textQuestion=findViewById(R.id.textViewQuestion)
        editTextAnswer=findViewById(R.id.editTextAnswer)
        button=findViewById(R.id.buttonOk)
        buttonNext=findViewById(R.id.buttonNext)


        gameContinue()

        button.setOnClickListener {
            val input = editTextAnswer.text.toString()
            if(input == ""){
                Toast.makeText(applicationContext,"Please write an answer or click the next button",
                Toast.LENGTH_LONG).show()
            }else{

                pauseTimer()
                val userAnswer = input.toInt()
                if(userAnswer == correctAnswer){
                    userScore = userScore +10
                    textQuestion.text ="Congratulations"
                    textScore.text = userScore.toString()

                }else{
                    userLife--
                    textQuestion.text ="Sorry wrong answer"
                    textLife.text =  userLife.toString()
                }

            }

        }
        buttonNext.setOnClickListener {
            pauseTimer()
            resetTimer()
            gameContinue()
            editTextAnswer.setText("")

            if(userLife == 0){
                Toast.makeText(applicationContext,"Game Over",Toast.LENGTH_LONG).show()
                val intent = Intent(this@GameActivity, ResultActivity::class.java)
                intent.putExtra("score",userScore)
                finish()
            }else{
                gameContinue()

            }

        }
    }
    fun gameContinue(){
        val number1 = Random.nextInt(0,100)
        val number= Random.nextInt(0,100)
        textQuestion.text="$number+$number1"

        correctAnswer = number+number1


    }
    fun startTimer()
    {
        timer= object: CountDownTimer(timeLeftInMills,1000){
            override fun onFinish() {
                pauseTimer()
                resetTimer()
                updateTimer()

                userLife--
                textLife.text=userLife.toString()
                textQuestion.text = "Sorry, Time is up"


            }

            override fun onTick(millisUntilFinished: Long) {
                timeLeftInMills = millisUntilFinished
                updateText()
            }

        }.start()
    }

    fun updateTimer(){

    }

    fun updateText(){
        val remainigTime : Int = (timeLeftInMills/1000).toInt()
        textTime.text = String.format(Locale.getDefault(),"%02d%",remainigTime)    }
    fun pauseTimer(){
        timer.cancel()
    }
    fun resetTimer(){
        timeLeftInMills= startTimerInMills
        updateText()
    }
}