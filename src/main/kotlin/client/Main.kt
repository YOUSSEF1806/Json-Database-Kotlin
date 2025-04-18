package com.youyou.client

fun main(args: Array<String>) {
    val input = readln().split(" ").toTypedArray()
    ClientSokt("127.0.0.1", 23456).launch(input)
}