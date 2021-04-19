package com.danpyk.automaticpostsending.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.URL
import java.util.concurrent.TimeUnit
import javax.net.ssl.HttpsURLConnection

class MainViewModel : ViewModel() {
    // TODO: Implement the ViewModel


      fun sendMSG(){
        viewModelScope.launch( Dispatchers.IO) {
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
            out.writeBytes("    \"key\" \"ddddd you try.\",\n")
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

}