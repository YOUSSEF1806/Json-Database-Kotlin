package com.youyou

object MessageOutput {
    fun errorMsg() = println("ERROR")
    fun okMsg() = println("OK")

    fun printMsg(msg: String) = println(msg)

    fun printInMsg(msg: String) = println("Received: $msg")
    fun printOutMsg(msg: String) = println("Sent: $msg")
}