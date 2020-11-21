package org.wandukong.soolleongx2

data class GoalResponseData(
    val data: Data,
    val message: String,
    val status: Int,
    val success: Boolean
) {
    data class Data(
        val bottle: Int
    )
}