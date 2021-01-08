package com.snilloc.coroutinesdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private val RESULT_2: String = "Result #2"
    private val RESULT_1 = "Result #1"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            //Initialize the background work
            //CoroutineScope is a way to group coroutines together in a similar category so that you can take action on many of them at once
            //CoroutineScope context Types; IO(Network and local db interactions), Main(Doing stuff on the Main Thread), Default(Heavy Computational Work)
            CoroutineScope(IO).launch {
                fakeApiResult()
            }
        }
    }

    private suspend fun fakeApiResult() {
        val result1 = getResult1FromApi()
        println("debug: $result1")
        setTextOnMainThread(result1)

        //Retrieve Result2
        val result2 = getResult2romApi()
        println("debug: $result2")
        setTextOnMainThread(result2)
    }

    private suspend fun getResult1FromApi(): String {
        //Log the thread
        logThread("getResult1FromApi")
        //Simulate work done in the background
        delay(1000)

        return RESULT_1
    }

    private suspend fun getResult2romApi() : String {
        logThread("getResult2FromApi")
        delay(1000)

        return RESULT_2
    }

    private fun logThread(methodName: String){
        println("debug: $methodName: ${Thread.currentThread().name}")
    }

    private fun setNewText(input: String) {
        //Get the input and append it to the textView
        val newText = textView.text.toString() + "\n$input"
        textView.text = newText
    }

    private suspend fun setTextOnMainThread(input: String) {
        //Launch a coroutine with Main as the context
        withContext(Main){
            //Update the textView
            setNewText(input)
        }
    }

}