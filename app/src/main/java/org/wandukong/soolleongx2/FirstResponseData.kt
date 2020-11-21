package org.wandukong.soolleongx2

import android.media.session.MediaSession

data class FirstResponseData (
        val `data`: Data,
        val message: String,
        val status: Int,
        val success: Boolean
) {
    data class Data(
            val isGoal: Boolean,
            val token: String,
            val uuid: String
    )
}