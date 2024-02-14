package com.vicky7230.simple_networking.core

import com.vicky7230.simple_networking.async.RequestTask
import com.vicky7230.simple_networking.async.ThreadExecutor
import com.vicky7230.simple_networking.listener.JSONObjectListener
import org.json.JSONObject

object Http {

    const val GET = "GET"
    const val POST = "POST"
    const val DELETE = "DELETE"
    const val PUT = "PUT"

    class Request(internal val method: String) {

        internal val header: MutableMap<String, String> = HashMap()
        internal var url: String? = null
        internal var body: ByteArray? = null
        private var jsonObjReqListener: JSONObjectListener? = null

        fun url(url: String?): Request {
            this.url = url
            return this
        }

        fun body(bodyJson: JSONObject?): Request {
            val textBody = bodyJson?.toString()
            body = textBody?.toByteArray(charset(Charsets.UTF_8.toString()))
            this.header["Content-Type"] = "application/json"
            return this
        }

        fun header(header: Map<String, String>?): Request {
            if (header.isNullOrEmpty()) return this
            this.header.putAll(header)
            return this
        }

        fun makeRequest(jsonObjectListener: JSONObjectListener?): Request {
            this.jsonObjReqListener = jsonObjectListener
            ThreadExecutor.execute(RequestTask(this))
            return this
        }

        internal fun sendResponse(resp: Response?, e: Exception?) {
            if (jsonObjReqListener != null) {
                if (e != null) jsonObjReqListener?.onFailure(e)
                else jsonObjReqListener?.onResponse(resp?.asJSONObject())
            }
        }
    }

}