package com.example.calculator

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

/**
 * MainActivity represents the main activity of the calculator app.
 */
class MainActivity : AppCompatActivity() {
    private lateinit var resultTextView: TextView
    private var currentInput: String = ""
    private var currentOperator: String = ""
    private var operand1: Double = 0.0

    /**
     * onSaveInstanceState is called to save the current state of the app when it is paused.
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("currentInput", currentInput)
        outState.putString("currentOperator", currentOperator)
        outState.putDouble("operand1", operand1)
    }

    /**
     * onRestoreInstanceState is called to restore the saved state when the app is resumed.
     */
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        currentInput = savedInstanceState.getString("currentInput", "")
        currentOperator = savedInstanceState.getString("currentOperator", "")
        operand1 = savedInstanceState.getDouble("operand1", 0.0)
        updateResult()
    }

    /**
     * onCreate is called when the activity is first created.
     */
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize the resultTextView by finding the corresponding view in the layout.
        resultTextView = findViewById(R.id.resultTextView)
    }

    /**
     * onDigitClick is called when a digit button is clicked.
     *
     * @param view The clicked button view.
     */
    fun onDigitClick(view: View) {
        val digit = (view as Button).text.toString()
        currentInput += digit
        updateResult()
    }

    /**
     * onOperatorClick is called when an operator button is clicked.
     *
     * @param view The clicked button view.
     */
    fun onOperatorClick(view: View) {
        val operator = (view as Button).text.toString()
        when (operator) {
            "-" -> {
                // Toggle between subtraction and making a number negative
                if (currentInput.isEmpty()) {
                    currentInput += "-"
                    updateResult()
                } else {
                    performOperation(operator)
                }
            }
            else -> performOperation(operator)
        }
    }

    /**
     * onEqualClick is called when the equal button is clicked.
     *
     * @param view The clicked button view.
     */
    fun onEqualClick(view: View) {
        if (currentInput.isNotEmpty() && currentOperator.isNotEmpty()) {
            val operand2 = currentInput.toDouble()
            val result = when (currentOperator) {
                "+" -> operand1 + operand2
                "-" -> operand1 - operand2
                "*" -> operand1 * operand2
                "/" -> operand1 / operand2
                "%" -> operand1 % operand2
                else -> 0.0
            }
            currentInput = result.toString()
            currentOperator = ""
            updateResult()
        }
    }

    /**
     * onClearClick is called when the clear button is clicked.
     *
     * @param view The clicked button view.
     */
    fun onClearClick(view: View) {
        currentInput = ""
        currentOperator = ""
        operand1 = 0.0
        updateResult()
    }

    /**
     * onPrmClick is called when the prm (positive/negative) button is clicked.
     *
     * @param view The clicked button view.
     */
    fun onPrmClick(view: View) {
        // Change the sign of the current input
        if (currentInput.isNotEmpty()) {
            currentInput = if (currentInput.startsWith("-")) {
                currentInput.substring(1)
            } else {
                "-$currentInput"
            }
            updateResult()
        }
    }

    /**
     * onModClick is called when the mod (%) button is clicked.
     *
     * @param view The clicked button view.
     */
    fun onModClick(view: View) {
        // Divide the number by a hundred and display the result
        if (currentInput.isNotEmpty()) {
            val result = currentInput.toDouble() / 100
            currentInput = result.toString()
            updateResult()
        }
    }

    /**
     * performOperation performs the specified operation on the current input and operand1.
     *
     * @param operator The operator to be performed.
     */
    private fun performOperation(operator: String) {
        if (currentInput.isNotEmpty()) {
            currentOperator = operator
            operand1 = currentInput.toDouble()
            currentInput = ""
        }
    }

    /**
     * updateResult updates the resultTextView with the current input.
     */
    private fun updateResult() {
        resultTextView.text = currentInput
    }
}


