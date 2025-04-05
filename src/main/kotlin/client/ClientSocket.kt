package com.youyou.client

import com.youyou.server.InOutProcessor
import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.InetAddress
import java.net.Socket
import kotlin.random.Random

class ClientSokt(val address: String, val port: Int) : InOutProcessor {

    fun launch() {
        Socket(InetAddress.getByName(address), port).use { socket ->
            println("Client started!")

            DataOutputStream(socket.getOutputStream()).use { outputStream ->
                val msg = "Give me a record # ${Random.nextInt(0, 100)}"
                outputListener(outputStream, msg)
                DataInputStream(socket.getInputStream()).use { inputStream ->
                    inputListener(inputStream)
                }
            }

        }
    }
}
