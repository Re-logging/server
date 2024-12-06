package com.relogging.server.global.util

object RandomImage {
    fun getUrl(): String {
        val randomNumber = (1..10).random()
        return "https://relogging.s3.ap-northeast-2.amazonaws.com/dev/image/random/r$randomNumber.png"
    }
}
