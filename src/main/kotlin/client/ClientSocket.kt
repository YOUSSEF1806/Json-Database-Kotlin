package com.youyou.client

import com.youyou.InOutProcessor
import com.youyou.MessageOutput
import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.InetAddress
import java.net.Socket

class ClientSokt(val address: String, val port: Int) : InOutProcessor {

    fun launch(args: Array<String>) {
        Socket(InetAddress.getByName(address), port).use { socket ->
            println("Client started!")

            DataOutputStream(socket.getOutputStream()).use { outputStream ->
                val command = args.joinToString(" ")
                try {
                    val commandOutput = parse(command)
                    outputListener(outputStream, commandOutput)
                    DataInputStream(socket.getInputStream()).use { inputStream ->
                        inputListener(inputStream)
                    }
                } catch (e: CommandException) {
                    println("ERROR!! Bad Arguments")
                }

            }
        }
    }

    private fun parse(inputCommand: String): String {
        val splitCommand = inputCommand.split(" ")
        if (splitCommand.size >= 2 && splitCommand.first() == "-t") {
            val commandName = splitCommand[1]
            when (commandName.lowercase()) {
                "set" -> {
                    if (splitCommand.size >= 6 && splitCommand[2] == "-i" && splitCommand[4] == "-m") {
                        return "$commandName ${splitCommand[3]} ${
                            splitCommand.subList(5, splitCommand.size).joinToString(" ")
                        }"
                    }
                }

                "get" -> {
                    if (splitCommand.size == 4 && splitCommand[2] == "-i") {
                        return "$commandName ${splitCommand[3]}"
                    }
                }

                "delete" -> {
                    if (splitCommand.size == 4 && splitCommand[2] == "-i") {
                        return "$commandName ${splitCommand[3]}"
                    }
                }

                "exit" -> {
                    if (splitCommand.size == 2) return commandName
                }
            }
        }
        throw CommandException()
    }
}

class CommandException() : Exception(MessageOutput.ERROR_MSG)