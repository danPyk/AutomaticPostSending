package com.danpyk.automaticpostsending.ui.main

import android.app.job.JobParameters
import android.app.job.JobService
import android.content.ContentValues.TAG
import android.util.Log
import com.danpyk.automaticpostsending.ui.main.GlobalVar.Companion.positionOnList
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection


class JobServ : JobService() {


    override  fun onStartJob(params: JobParameters?): Boolean {


        if(positionOnList < 3){
            positionOnList++
            // res = positionOnList.inc()
        }else{
            positionOnList = 0
        }

        GlobalScope.launch {
                   sendMSG(positionOnList, list)

       }
        jobFinished(params, false)
        Log.i(TAG, "onStartJob: called")

        return true
    }
    override fun onStopJob(params: JobParameters?): Boolean {
        Log.i(TAG, "onStopJob: called")
        return true
    }

     private fun sendMSG(n: Int, list: List<String>){

         val currentQuote = list[n]
        val url = URL("https://fcm.googleapis.com/fcm/send")
        val con: HttpsURLConnection = url.openConnection() as HttpsURLConnection
        con.setRequestMethod("POST")
        con.setRequestProperty("Content-Type", "application/json")
        con.setRequestProperty(
                "Authorization",
                "key=AAAAJBZUzuM:APA91bFa8-P-VwqWMI4kMWkX6jwxLNCSUbLpIgyGtSmmXlEHCdJbnigfK0-kLo-SO3BEoMsQ1HW5RqP6VjV_ok9RNGC80myDQRzDbSC1kvnM3KSRTWrfZEh1hove8KtVsYqX8Mtqw4L6"
        )

/* Payload support */con.setDoOutput(true)
        val out = DataOutputStream(con.getOutputStream())
        out.writeBytes("{\n")
        out.writeBytes("  \"to\": \"/topics/kanyepushh\",\n")
        out.writeBytes("  \"data\":{\n")
        out.writeBytes("    \"key\" \"$currentQuote\",\n")
        out.writeBytes("    \"title\":\"kanyepush\",\n")
        out.writeBytes("    \"my_custom_key2\":\"true\"\n")
        out.writeBytes("  }\n")
        out.writeBytes("}")
        out.flush()
        out.close()

        val status: Int = con.getResponseCode()
        val `in` = BufferedReader(InputStreamReader(con.getInputStream()))
        var inputLine: String?
        val content = StringBuffer()
        while (`in`.readLine().also { inputLine = it } != null) {
            content.append(inputLine)
        }
        `in`.close()
        con.disconnect()
        println("Response status: $status")
        println(content.toString())
        Log.i(TAG, "sendMSG: called $currentQuote")


    }


    private val list = listOf(
            "quote 1",
            "quote 2",
            "quote 3",
            "quote 4",
    )

}

