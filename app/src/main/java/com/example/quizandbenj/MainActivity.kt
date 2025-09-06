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
        "What is the primary function of a CPU?",
        "What does \"API\" stand for?",
        "Name a Data structure that uses the FIFO principle.",
        "What is the main purpose of an operating system?",
        "What does \"HTML\" stand for?",
        "Which programming language is primarily used for web browsers?",
        "Explain what \"Big O notation\" is used for.",
        "Define a \"boolean\" data type.",
        "What does 'CSS' stand for?",
        "Explain the difference between \"null\" and \"undefined\" in JavaScript.",
        "Define what a \"compiler\" is.",
        "Explain what \"object-oriented programming\" (OOP) is.",
        "Define what a \"database\" is.",
        "What does \"URL\" stand for?",
        "Define \"agile\" methodology in software development.",
        "Explain what \"JSON\" is.",
        "Describe the purpose of \"comments\" in code.",
        "Define what \"debugging\" is.",
        "Explain what a \"framework\" is in software development.",
        "What is the \"FIFO\" principle?",
        "The \"Break\" statement in a loop has what purpose?",
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
        arrayOf("Store data", "Execute instructions", "Render graphics"),
        arrayOf("Application Programming Interface", "Advanced Programming Interface", "Automated Programming Interface"),
        arrayOf("Stack", "Queue", "Array"), 
        arrayOf("Manage hardware and software resources", "Create documents", "Browse the internet"),
        arrayOf("HyperText Markup Language", "HighText Markup Language", "Hyperlink and Text Markup Language"),
        arrayOf("Python", "Java", "JavaScript"),
        arrayOf("Describe algorithm efficiency", "Define variable types", "Style web pages"),
        arrayOf("A type with true or false values", "A type for numbers", "A type for text"),
        arrayOf("Cascading Style Sheets", "Creative Style Sheets", "Computer Style Sheets"),
        arrayOf("'Null' is an assigned value, 'undefined' means a variable has not been declared or assigned", "'Undefined' is an assigned value, 'null' means a variable has not been declared or assigned", "They are the same"),
        arrayOf("Translates source code to machine code", "Executes machine code", "Optimizes database queries"),
        arrayOf("A paradigm based on objects and data", "A paradigm based on functions", "A paradigm based on sequential instructions"),
        arrayOf("An organized collection of data", "A type of programming language", "A web server software"),
        arrayOf("Uniform Resource Locator", "Universal Resource Locator", "Uniform Resource Link"),
        arrayOf("An iterative and incremental approach", "A linear and sequential approach", "A random and chaotic approach"),
        arrayOf("JavaScript Object Notation", "Java Standard Object Notation", "JavaScript Oriented Notation"),
        arrayOf("Explain code to humans", "Execute specific functions", "Define variable types"),
        arrayOf("Finding and fixing errors in code", "Writing new code", "Testing code performance"),
        arrayOf("A pre-written code base for developers to build upon", "A type of programming language", "A database management system"),
        arrayOf("The First element added is the first to be removed", "The Last element added is the first to be removed", "The First element added is the Last to be Removed"),
        arrayOf("To skip to the next iteration of the loop", "To exit the loop entirely", "To Delete the loop"),
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
        "Execute instructions",
        "Application Programming Interface",
        "Queue", 
        "Manage hardware and software resources",
        "HyperText Markup Language",
        "JavaScript",
        "Describe algorithm efficiency",
        "A type with true or false values",
        "Cascading Style Sheets",
        "'Null' is an assigned value, 'undefined' means a variable has not been declared or assigned",
        "Translates source code to machine code",
        "A paradigm based on objects and data",
        "An organized collection of data",
        "Uniform Resource Locator",
        "An iterative and incremental approach",
        "JavaScript Object Notation",
        "Explain code to humans",
        "Finding and fixing errors in code",
        "A pre-written code base for developers to build upon",
        "The First element added is the first to be removed",
        "To exit the loop entirely"
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
        binding.congrats.isEnabled = true
        displayCongrats()
        binding.question.isEnabled = false
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
            // Show the correct answer
            when (correctAnswerString) {
                binding.button1.text.toString() -> correctButtonColors(0)
                binding.button2.text.toString() -> correctButtonColors(1)
                binding.button3.text.toString() -> correctButtonColors(2)
            }
        }
        binding.button1.isEnabled = false
        binding.button2.isEnabled = false
        binding.button3.isEnabled = false
        if (currentQuestion < questions.size - 1){
            currentQuestion++
            binding.question.postDelayed({displayQuestion()
                binding.button1.isEnabled = true
                binding.button2.isEnabled = true
                binding.button3.isEnabled = true},1000)
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
        questionOrder = (questions.indices).toList().shuffled()
        displayQuestion()
        binding.Restart.isEnabled = true
        binding.button2.isEnabled = true
        binding.button1.isEnabled = true
        binding.button3.isEnabled = true
        binding.congrats.isEnabled = false
        Toast.makeText(this,"Quiz Restarted!!", Toast.LENGTH_SHORT).show()
    }
    private fun displayCongrats() {
        val percentage = if (questions.isNotEmpty()) (score * 100) / questions.size else 0
        val congratulatoryMessage = when {
            percentage == 100 -> "Flawless Victory! You're a Quiz Master!"
            percentage >= 80 -> "Excellent Work! You really know your stuff!"
            percentage >= 60 -> "Great Job! Solid performance!"
            percentage >= 40 -> "Good Effort! Keep learning!"
            else -> "Keep practicing! You'll get there!"
        }
        binding.congrats.text = congratulatoryMessage

    }
}
