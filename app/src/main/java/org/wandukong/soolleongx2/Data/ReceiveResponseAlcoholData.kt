package org.wandukong.soolleongx2.Data

class ReceiveResponseAlcoholData (
        val `data`: Data,
        val message: String,
        val status: Int,
        val success: Boolean
) {
    data class Data(
            val bottle: Int,
            val day: Int,
            val glass: Int
    )
}