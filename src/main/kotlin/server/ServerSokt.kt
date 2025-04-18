package com.youyou.server

import com.youyou.InOutProcessor
import com.youyou.MessageOutput
import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.InetAddress
import java.net.ServerSocket
import java.net.Socket

object ServerSokt {
    private val address: String = "127.0.0.1"
    private var port: Int = 23456
    private var isServerUp = false

    fun launch(port: Int) {
        this.port = port
        ServerSocket(this.port, 50, InetAddress.getByName(address)).use { srv ->
            println("Server started!")
            isServerUp = true

            while (!srv.isClosed) {
                val session = SocketSession(srv.accept())
                session.start()
                session.join()
                if (!isServerUp) {
                    srv.close()
                }
            }

        }
    }

    //@Synchronized
    fun exit() {
        isServerUp = false
    }

    private fun execute(commandString: String): String {
        val commandName = commandString.split(" ").first()
        return when (commandName.lowercase()) {
            "set" -> setCommand(commandString)
            "get" -> getCommand(commandString)
            "delete" -> deleteCommand(commandString)
            "exit" -> "exit"

            else -> "ERROR!! Unknown command"
        }
    }

    private fun setCommand(stringCommand: String): String {
        val splitCommand = stringCommand.split(" ")
        val recordNum = splitCommand.drop(1).first().toInt()
        val recordText = splitCommand.subList(2, splitCommand.size).joinToString(" ").trim('"')

        return JsonDB.setRecord(recordNum, recordText)
    }

    private fun getCommand(stringCommand: String): String {
        return JsonDB.getRecord(stringCommand.split(" ").last().toInt())
    }

    private fun deleteCommand(stringCommand: String): String {
        return JsonDB.deleteRecord(stringCommand.split(" ").last().toInt())
    }

    class SocketSession(private val newSocket: Socket) : Thread(), InOutProcessor {
        override fun run() {
            socketCommunication(newSocket)
        }

        private fun socketCommunication(socket: Socket) {
            DataInputStream(socket.getInputStream()).use { inputStream ->
                val input = inputListener(inputStream)
                var outputMessage = execute(input)
                if (outputMessage == "exit") {
                    exit()
                    outputMessage = MessageOutput.OK_MSG
                }
                DataOutputStream(socket.getOutputStream()).use { outputStream ->
                    outputListener(outputStream, outputMessage)
                }
            }
        }
    }
}

