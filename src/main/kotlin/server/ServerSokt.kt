package com.youyou.server

import com.youyou.MessageOutput
import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.InetAddress
import java.net.ServerSocket
import java.net.Socket
import java.net.SocketTimeoutException

class ServerSokt(val address: String, val port: Int) {

    fun launch() {
        ServerSocket(port, 50, InetAddress.getByName(address)).use { srv ->
            srv.soTimeout = 8000
            println("Server started!")

            try {
                while (true) {
                    val session = Session(srv.accept())
                    session.start()
                }
            } catch (e: SocketTimeoutException) {
                srv.close()
            }
        }
    }
}

class Session(private val newSocket: Socket): Thread(), InOutProcessor {
    override fun run() {
        socketCommunication(newSocket)
    }

    private fun socketCommunication(socket: Socket) {
        DataInputStream(socket.getInputStream()).use { inputStream ->
            val input = inputListener(inputStream)
            val recordNum = input.split(" ").last().toInt()
            val outputMessage = "A record # $recordNum was sent!"
            DataOutputStream(socket.getOutputStream()).use { outputStream ->
                outputListener(outputStream, outputMessage)
            }
        }
    }
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