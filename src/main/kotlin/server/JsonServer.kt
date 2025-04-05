package com.youyou.server

import com.youyou.MessageOutput

private const val DATA_MAX_SIZE = 100

object JsonServer {
    private val data = MutableList(DATA_MAX_SIZE) { "" }
    private var isServerUp = false

    fun launch() {
        isServerUp = true
        do {
            val commandString = readln()
            if (commandString.lowercase() != "exit")
                execute(commandString)
            else
                isServerUp = false
        } while (isServerUp)
    }

    private fun execute(commandString: String) {
        val (command, args) = commandString.split(" ").let { it.first() to it.drop(1) }
        when(command.lowercase()) {
            "set" -> setCommand(args)
            "get" -> getCommand(args)
            "delete" -> deleteCommand(args)
            else -> MessageOutput.printMsg("ERROR!! Unknown command")
        }
    }

    private fun deleteCommand(args: List<String>): Boolean {
        if (args.size == 1) {
            val index = args.first().toInt()
            if (index in 1 ..DATA_MAX_SIZE) {
                data[index - 1] = ""
                MessageOutput.okMsg()
                return true
            }
        }
        MessageOutput.errorMsg()
        return false
    }

    private fun getCommand(args: List<String>): Boolean {
        if (args.size == 1) {
            val index = args.first().toInt()
            if (index in 1 ..DATA_MAX_SIZE) {
                if (data[index - 1].isNotEmpty()) {
                    MessageOutput.printMsg(data[index - 1])
                    return true
                }
            }
        }
        MessageOutput.errorMsg()
        return false
    }

    private fun setCommand(args: List<String>): Boolean {
        if (args.size >= 2) {
            val index = args.first().toInt()
            if (index in 1 ..DATA_MAX_SIZE) {
                data[index - 1] = args.drop(1).joinToString(" ")
                MessageOutput.okMsg()
                return true
            }
        }
        MessageOutput.errorMsg()
        return false
    }
}