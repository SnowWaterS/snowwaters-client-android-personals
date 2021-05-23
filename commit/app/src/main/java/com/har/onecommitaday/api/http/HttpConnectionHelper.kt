package com.har.onecommitaday.api.http

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

object HttpConnectionHelper {

    fun requestHttpGet(url: String): String {
        try {
            val requestUrl = URL(url)
            val conn = requestUrl.openConnection() as? HttpURLConnection
            var result = ""
            conn?.apply {
                connectTimeout = 10000
                requestMethod = "GET"

                val resCode = this.responseCode
                if (resCode == HttpURLConnection.HTTP_OK) {
                    val buffer = BufferedReader(InputStreamReader(this.inputStream))
                    result = buffer.readLines().joinToString()
                    buffer.close()
                }
                disconnect()
            }

            return result

        } catch (e: Exception) {
            e.printStackTrace()
            return ""
        }
    }
}