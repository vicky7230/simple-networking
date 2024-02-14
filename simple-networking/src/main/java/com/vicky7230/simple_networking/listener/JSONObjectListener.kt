package com.vicky7230.simple_networking.listener

import org.json.JSONObject

interface JSONObjectListener {
 fun onResponse(res: JSONObject?)
 fun onFailure(e: Exception?)
}