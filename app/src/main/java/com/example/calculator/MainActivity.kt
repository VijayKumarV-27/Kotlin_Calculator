package com.example.calculator

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var display: TextView

    private var firstNumber = 0.0
    private var operator = ""
    private var isNewInput = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        display = findViewById(R.id.display)

        // Number buttons
        val numberButtons = listOf(
            R.id.btn0,
            R.id.btn1,
            R.id.btn2,
            R.id.btn3,
            R.id.btn4,
            R.id.btn5,
            R.id.btn6,
            R.id.btn7,
            R.id.btn8,
            R.id.btn9
        )

        numberButtons.forEach { id ->
            findViewById<Button>(id).setOnClickListener {
                enterNumber((it as Button).text.toString())
            }
        }

        // Decimal button
        findViewById<Button>(R.id.btnDecimal).setOnClickListener {
            enterDecimal()
        }

        // Operators
        findViewById<Button>(R.id.btnAdd).setOnClickListener {
            selectOperator("+")
        }

        findViewById<Button>(R.id.btnSubtract).setOnClickListener {
            selectOperator("-")
        }

        findViewById<Button>(R.id.btnMultiply).setOnClickListener {
            selectOperator("*")
        }

        findViewById<Button>(R.id.btnDivide).setOnClickListener {
            selectOperator("/")
        }

        // Equals
        findViewById<Button>(R.id.btnEquals).setOnClickListener {
            calculateResult()
        }

        // Clear
        findViewById<Button>(R.id.btnClear).setOnClickListener {
            clearCalculator()
        }

        // Delete
        findViewById<Button>(R.id.btnDelete).setOnClickListener {
            deleteLastDigit()
        }
    }

    private fun enterNumber(number: String) {

        if (isNewInput || display.text == "Error") {
            display.text = number
            isNewInput = false
        } else {
            display.append(number)
        }
    }

    private fun enterDecimal() {

        if (isNewInput || display.text == "Error") {
            display.text = "0."
            isNewInput = false
        } else if (!display.text.contains(".")) {
            display.append(".")
        }
    }

    private fun selectOperator(selectedOperator: String) {

        if (display.text == "Error") return

        firstNumber = display.text.toString().toDoubleOrNull() ?: 0.0

        operator = selectedOperator

        isNewInput = true
    }

    private fun calculateResult() {

        if (operator.isEmpty() || isNewInput) {
            return
        }

        val secondNumber =
            display.text.toString().toDoubleOrNull() ?: return

        val result = when (operator) {

            "+" -> firstNumber + secondNumber

            "-" -> firstNumber - secondNumber

            "*" -> firstNumber * secondNumber

            "/" -> {

                if (secondNumber == 0.0) {
                    display.text = "Error"
                    operator = ""
                    isNewInput = true
                    return
                }

                firstNumber / secondNumber
            }

            else -> return
        }

        display.text = formatResult(result)

        operator = ""
        isNewInput = true
    }

    private fun formatResult(result: Double): String {

        return if (result % 1.0 == 0.0) {

            result.toLong().toString()

        } else {

            String.format(
                Locale.US,
                "%.8f",
                result
            )
                .trimEnd('0')
                .trimEnd('.')
        }
    }

    private fun clearCalculator() {

        display.text = "0"

        firstNumber = 0.0

        operator = ""

        isNewInput = true
    }

    private fun deleteLastDigit() {

        if (isNewInput || display.text == "Error") {
            return
        }

        val currentText = display.text.toString()

        if (currentText.length > 1) {

            display.text = currentText.dropLast(1)

        } else {

            display.text = "0"
            isNewInput = true
        }
    }
}