package com.example.caculator

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var tvResult: TextView
    private var currentInput = ""
    private var operator = ""
    private var firstNumber = 0
    private lateinit var tvExpression: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvResult = findViewById(R.id.tvResult)
        tvExpression = findViewById(R.id.tvExpression)

        val buttons = listOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5,
            R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9
        )

        for (id in buttons) {
            findViewById<Button>(id).setOnClickListener { onDigit(it) }
        }

        val operators = listOf(R.id.btnAdd, R.id.btnSub, R.id.btnMul, R.id.btnDiv)
        for (id in operators) {
            findViewById<Button>(id).setOnClickListener { onOperator(it) }
        }

        findViewById<Button>(R.id.btnEqual).setOnClickListener { onEqual(it) }
        findViewById<Button>(R.id.btnC).setOnClickListener { onClear(it) }
        findViewById<Button>(R.id.btnCE).setOnClickListener { onClearEntry(it) }
        findViewById<Button>(R.id.btnBS).setOnClickListener { onBackspace(it) }
        findViewById<Button>(R.id.btnSign).setOnClickListener { onToggleSign(it) }
    }

    private fun onDigit(view: View) {
        val button = view as Button
        currentInput += button.text.toString()
        tvResult.text = currentInput
    }

    private fun onOperator(view: View) {
        if (currentInput.isNotEmpty()) {
            firstNumber = currentInput.toInt()
            operator = (view as Button).text.toString()

            // Cập nhật biểu thức hiển thị
            tvExpression.text = "$firstNumber $operator"
            currentInput = ""
        }
    }


    private fun onEqual(view: View) {
        if (currentInput.isNotEmpty() && operator.isNotEmpty()) {
            val secondNumber = currentInput.toInt()
            val result = when (operator) {
                "+" -> firstNumber + secondNumber
                "-" -> firstNumber - secondNumber
                "x" -> firstNumber * secondNumber
                "/" -> if (secondNumber != 0) firstNumber / secondNumber else {
                    tvResult.text = "Cannot divide by zero"
                    return
                }
                else -> 0
            }
            // Cập nhật biểu thức và kết quả
            tvExpression.text = "$firstNumber $operator $secondNumber ="
            tvResult.text = result.toString()

            // Reset để nhập phép tính mới
            currentInput = result.toString()
            operator = ""
        }
    }


    private fun onClear(view: View) {
        currentInput = ""
        firstNumber = 0
        operator = ""
        tvResult.text = "0"
    }

    private fun onClearEntry(view: View) {
        currentInput = ""
        tvResult.text = "0"
    }

    private fun onBackspace(view: View) {
        currentInput = currentInput.dropLast(1)
        tvResult.text = if (currentInput.isEmpty()) "0" else currentInput
    }

    private fun onToggleSign(view: View) {
        currentInput = (-currentInput.toInt()).toString()
        tvResult.text = currentInput

    }
}