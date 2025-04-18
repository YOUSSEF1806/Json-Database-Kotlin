package com.youyou.server

import com.youyou.MessageOutput

private const val DATA_MAX_SIZE = 1000

object JsonDB {
    private val data = MutableList(DATA_MAX_SIZE) { "" }

    fun deleteRecord(numRecord: Int): String {
        if (numRecord in 1..DATA_MAX_SIZE) {
            data[numRecord - 1] = ""
            return MessageOutput.OK_MSG
        }
        return MessageOutput.ERROR_MSG
    }

    fun getRecord(numRecord: Int): String {
        if (numRecord in 1..DATA_MAX_SIZE) {
            if (data[numRecord - 1].isNotEmpty()) {
                return data[numRecord - 1]
            }
        }
        return MessageOutput.ERROR_MSG
    }

    fun setRecord(numRecord: Int, recordText: String): String {
        if (numRecord in 1..DATA_MAX_SIZE) {
            data[numRecord - 1] = recordText
            return MessageOutput.OK_MSG
        }
        return MessageOutput.ERROR_MSG
    }
}