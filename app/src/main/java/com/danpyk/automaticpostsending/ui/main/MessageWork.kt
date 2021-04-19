package com.danpyk.automaticpostsending.ui.main

import android.app.NotificationManager
import android.content.Context
import android.preference.PreferenceManager
import androidx.core.content.ContextCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection


class MessageWork(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {
    var text: String = ""

    override fun doWork(): Result {

        return try {
            sendMSG()
            return Result.success()
        } catch (throwable: Throwable) {

            // Error.e(throwable, "Error failure")
            Result.failure()
        }
    }

    companion object {
        //pass msg content
        const val MESSAGE = "message"
    }


    private  fun sendMSG(){
        val url = URL("https://fcm.googleapis.com/fcm/send")
        val con: HttpsURLConnection = url.openConnection() as HttpsURLConnection
        con.setRequestMethod("POST")
        con.setRequestProperty("Content-Type", "application/json")
        con.setRequestProperty("Authorization",
            "key=AAAAJBZUzuM:APA91bFa8-P-VwqWMI4kMWkX6jwxLNCSUbLpIgyGtSmmXlEHCdJbnigfK0-kLo-SO3BEoMsQ1HW5RqP6VjV_ok9RNGC80myDQRzDbSC1kvnM3KSRTWrfZEh1hove8KtVsYqX8Mtqw4L6")

/* Payload support */

/* Payload support */con.setDoOutput(true)
        val out = DataOutputStream(con.getOutputStream())
        out.writeBytes("{\n")
        out.writeBytes("  \"to\": \"/topics/kanyepushh\",\n")
        out.writeBytes("  \"data\":{\n")
        out.writeBytes("    \"key\" \"d2.\",\n")
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
    }

}

