//package com.weather.bradare2
//
//import com.chaquo.python.Python
//import com.chaquo.python.android.AndroidPlatform
//import android.content.Context
//import android.widget.Toast
//
//class BlutterRun(private val context: Context) {
//
//    fun executeScript(inputPath: String, outputPath: String) {
//        if (!Python.isStarted()) {
//            Python.start(AndroidPlatform(context))
//        }
//
//        val py = Python.getInstance()
//        val pyModule = py.getModule("blutter") // Name of your Python script (without .py)
//
//        try {
//            // Call the Python function with arguments
//            val result = pyModule.callAttr("main", inputPath, outputPath)
//            Toast.makeText(context, "Execution success: $result", Toast.LENGTH_SHORT).show()
//        } catch (e: Exception) {
//            e.printStackTrace()
//            Toast.makeText(context, "Error executing script: ${e.message}", Toast.LENGTH_LONG).show()
//        }
//    }
//}
