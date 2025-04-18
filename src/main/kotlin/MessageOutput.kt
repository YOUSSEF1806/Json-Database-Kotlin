package com.youyou

import java.io.DataInputStream
import java.io.DataOutputStream

object MessageOutput {
    const val ERROR_MSG = "ERROR"
    const val OK_MSG = "OK"

    fun printMsg(msg: String) = println(msg)

    fun printInMsg(msg: String) = println("Received: $msg")
    fun printOutMsg(msg: String) = println("Sent: $msg")
}

interface InOutProcessor {
    fun inputListener(inputStream: DataInputStream): String {
        val input = inputStream.readUTF()
        MessageOutput.printInMsg(input)
        return input
    }

    fun outputListener(outputStream: DataOutputStream, msg: String) {
        outputStream.writeUTF(msg)
        MessageOutput.printOutMsg(msg)
    }
}