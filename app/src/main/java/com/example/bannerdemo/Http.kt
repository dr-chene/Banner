package com.example.bannerdemo

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

/**
Created by chene on @date 20-11-14 上午11:24
 **/
class Http {

    companion object {

        fun call(url: String): String {
            val response = StringBuilder()
            val reaUrl = URL(url)
            val connection = reaUrl.openConnection() as HttpURLConnection
            connection.apply {
                requestMethod = "GET"
                connectTimeout = 8000
                readTimeout = 8000
            }
            val reader = BufferedReader(InputStreamReader(connection.inputStream))
            var line: String?
            do {
                line = reader.readLine()?.also {
                    response.append(it)
                }
            } while (line != null)
            reader.close()
            connection.disconnect()
            return response.toString()
        }
    }
}