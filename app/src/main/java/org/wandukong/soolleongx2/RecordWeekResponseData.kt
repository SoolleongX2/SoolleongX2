package org.wandukong.soolleongx2

data class RecordWeekResponseData(
    val data: Data,
    val message: String,
    val status: Int,
    val success: Boolean
) {
    data class Data(
        val bottle: Int,
        val day: Int,
        val glass: Int,
        val records: List<Record>
    ) {
        data class Record(
            val bottle: Int,
            val day: Int,
            val dayCount: Int,
            val glass: Int,
            val month: Int
        )
    }
}