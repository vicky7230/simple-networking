package com.vicky7230.simple_networking.async

import com.vicky7230.simple_networking.core.Http
import com.vicky7230.simple_networking.core.Response
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

internal class RequestTask(private val req: Http.Request) : Runnable {

    override fun run() {
        try {
            val conn = getHttpURLConn()
            val parsedResponse = parseResponse(conn)
            req.sendResponse(parsedResponse, null)
        } catch (e: MalformedURLException) {
            req.sendResponse(null, e)
        } catch (e: IOException) {
            req.sendResponse(null, e)
        } catch (e: Exception) {
            req.sendResponse(null, e)
        }
    }

    private fun getHttpURLConn(): HttpURLConnection {
        val url = URL(req.url)
        val conn = url.openConnection() as HttpURLConnection
        val method = req.method
        conn.requestMethod = method
        for ((key, value) in req.header) {
            conn.setRequestProperty(key, value)
        }
        if (req.body != null) {
            val outputStream = conn.outputStream
            outputStream.write(req.body)
        }
        conn.connect()
        return conn
    }

    private fun parseResponse(conn: HttpURLConnection): Response {
        try {
            val bos = ByteArrayOutputStream()
            val status = conn.responseCode
            val validStatus = status in 200..299
            val inpStream = if (validStatus) conn.inputStream else conn.errorStream
            var read: Int
            var totalRead = 0
            val buf = ByteArray(512)
            while (inpStream.read(buf).also { read = it } != -1) {
                bos.write(buf, 0, read)
                totalRead += read
            }
            return Response(bos.toByteArray())
        } finally {
            conn.disconnect()
        }
    }
}
