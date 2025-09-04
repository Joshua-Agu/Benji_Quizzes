package com.example.quizandbenj

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.quizandbenj.databinding.ActivityMainBinding
import kotlin.arrayOf

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private val questions = arrayOf(
        "What does \"DRY\" stand for in software development?",
        "Name a common version control system.",
        "What is the default port for HTTPS?",
        "Which data structure uses LIFO?",
        "What does \"SQL\" stand for?",
        "What is the term for a function calling itself?",
        "What HTTP status code means \"Not Found\"?",
        "Which company developed the Android OS?",
        "What is the binary representation of the decimal number 10?",
        "What does \"IDE\" stand for in programming?",
        "Which keyword is used to define a constant in Kotlin?",
        "What is the primary function of a CPU?"
    )
    private val options = arrayOf(
        arrayOf("Don't Repeat Yourself", "Do Replicate Yourself", "Don't Rely on Yourself"),
        arrayOf("Git", "Docker", "Kubernetes"),
        arrayOf("80", "443", "22"),
        arrayOf("Queue", "Stack", "Array"),
        arrayOf("Structured Query Language", "Simple Query Language", "Sequential Query Language"),
        arrayOf("Iteration", "Recursion", "Redirection"),
        arrayOf("200 OK", "404 Not Found", "500 Internal Server Error"),
        arrayOf("Apple", "Google", "Microsoft"),
        arrayOf("1010", "0011", "1110"),
        arrayOf("Integrated Development Environment", "Internal Development Engine", "Interface Design Environment"),
        arrayOf("final", "val", "let"),
        arrayOf("Store data", "Execute instructions", "Render graphics")
    )
    private val answer = arrayOf(
        "Don't Repeat Yourself",
        "Git",
        "443",
        "Stack",
        "Structured Query Language",
        "Recursion",
        "404 Not Found",
        "Google",
        "1010",
        "Integrated Development Environment",
        "val",
        "Execute instructions"
    )
    private var currentQuestion = 0
    private var score = 0
    private var questionOrder: List<Int> = emptyList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        supportActionBar?.hide()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        questionOrder = (questions.indices).toList().shuffled()
        displayQuestion()
        binding.button1.setOnClickListener {
            checkAnswer(0)
        }
        binding.button2.setOnClickListener {
            checkAnswer(1)
        }
        binding.button3.setOnClickListener {
            checkAnswer(2)
        }
        binding.Restart.setOnClickListener { restartQuiz() }

    }


    private fun correctButtonColors(buttonIndex: Int) {
        when(buttonIndex) {
            0 -> binding.button1.setBackgroundResource(R.drawable.correctclick)
            1 -> binding.button2.setBackgroundResource(R.drawable.correctclick)
            2 -> binding.button3.setBackgroundResource(R.drawable.correctclick)
        }
    }

    private fun wrongButtonColors(buttonIndex: Int) {
        when(buttonIndex) {
            0 -> binding.button1.setBackgroundResource(R.drawable.wrongclick)
            1 -> binding.button2.setBackgroundResource(R.drawable.wrongclick)
            2 -> binding.button3.setBackgroundResource(R.drawable.wrongclick)
        }
    }

    private fun resetButtonColor(){
        binding.button1.setBackgroundResource(R.drawable.button2)
        binding.button2.setBackgroundResource(R.drawable.button2)
        binding.button3.setBackgroundResource(R.drawable.button2)
    }

    private fun showResults(){
        Toast.makeText(this, "You scored $score out of ${questions.size}", Toast.LENGTH_SHORT).show()
        binding.Restart.isEnabled = true
        binding.button1.isEnabled = false
        binding.button2.isEnabled = false
        binding.button3.isEnabled = false
    }

    private fun displayQuestion(){
        val actualQuestionIndex = questionOrder[currentQuestion]
        binding.question.text = questions[actualQuestionIndex]

        val currentShuffledOptions = options[actualQuestionIndex].copyOf()
        currentShuffledOptions.shuffle()

        binding.button1.text = currentShuffledOptions[0]
        binding.button2.text = currentShuffledOptions[1]
        binding.button3.text = currentShuffledOptions[2]
        resetButtonColor()
    }
    private fun checkAnswer(selectedOption: Int){
        val actualQuestionIndex = questionOrder[currentQuestion]
        val correctAnswerString = answer[actualQuestionIndex]

        val userSelectedText = when(selectedOption) {
            0 -> binding.button1.text.toString()
            1 -> binding.button2.text.toString()
            2 -> binding.button3.text.toString()
            else -> ""
        }

        if (userSelectedText == correctAnswerString) {
            score++
            correctButtonColors(selectedOption)
        } else {
            wrongButtonColors(selectedOption)
            if (binding.button1.text.toString() == correctAnswerString) {
                correctButtonColors(0)
            } else if (binding.button2.text.toString() == correctAnswerString) {
                correctButtonColors(1)
            } else if (binding.button3.text.toString() == correctAnswerString) {
                correctButtonColors(2)
            }
        }
        if (currentQuestion < questions.size - 1){
            currentQuestion++
            binding.question.postDelayed({displayQuestion()},1000)
        } else {
            binding.question.postDelayed({
                resetButtonColor()
                showResults()
            }, 1000)
        }
    }
    private fun restartQuiz(){
        currentQuestion = 0
        score = 0
        questionOrder = (questions.indices).toList().shuffled() // Added this line
        displayQuestion()
        binding.Restart.isEnabled = true
        binding.button2.isEnabled = true
        binding.button1.isEnabled = true
        binding.button3.isEnabled = true
        Toast.makeText(this,"Quiz Restarted!!", Toast.LENGTH_SHORT).show()
    }

}
