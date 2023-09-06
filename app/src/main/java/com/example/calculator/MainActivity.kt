package com.example.calculator

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.calculator.databinding.ActivityMainBinding
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var lastNumeric = false
    var stateError = false
    var lastDot = false
    var count = 0 // Int




    private lateinit var expression: Expression



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun onPrmClick(view: View) {}


    fun onClearClick(view: View) {
        binding.dataTv.text = "0"
        stateError = false
        lastDot = false
        lastNumeric =false
        binding.dataTv.visibility = View.VISIBLE
        count = 0

    }


    fun onOperatorClick(view: View) {

        if(!stateError && lastNumeric){
            //binding.dataTv.visibility = View.GONE
            binding.dataTv.append((view as Button).text)
            lastDot = false
            lastNumeric = false
            val txt = binding.dataTv.text.toString()
            binding.dataTv.text = binding.dataTv.text.toString().drop(1)
            //binding.dataTv.visibility = View.GONE

//            val txtt = binding.dataTv.text.toString()
        }
    }


    fun onDigitClick(view: View) {

        if(stateError){
            binding.dataTv.text = (view as Button).text
            stateError = false
        }
        else{
                count++
                if(count == 1) {
                    binding.dataTv.text = ""
                    binding.dataTv.append((view as Button).text)
                }
                else {

                    //binding.dataTv.text.toString().drop(1)
                    //binding.dataTv.visibility = View.VISIBLE
                    binding.dataTv.append((view as Button).text)
                    //var real = binding.dataTv.text.toString()
                    //binding.dataTv.text = binding.dataTv.text.toString().drop(2)
                    //binding.dataTv.text = real

                    binding.dataTv.visibility = View.VISIBLE

                    //binding.dataTv.text.toString().drop(count)
                }




        }
        lastNumeric = true


    }

    fun onEqualClick(view: View) {
        onEqual()
        binding.dataTv.visibility = View.VISIBLE
        binding.dataTv.text = binding.dataTv.text.toString()
    }

    fun onEqual(){
        if(lastNumeric && !stateError){
            val txt = binding.dataTv.text.toString()

            expression = ExpressionBuilder(txt).build()

            try{
                val result = expression.evaluate()
                binding.dataTv.text = result.toString()
            }catch (ex : ArithmeticException){
                Log.e("evaluate error",ex.toString())
                binding.dataTv.text = "Error"
                stateError = true
                lastNumeric = false
            }
        }
    }

    fun onmodClick(view: View) {
        if(!stateError && lastNumeric){

        }
    }
}