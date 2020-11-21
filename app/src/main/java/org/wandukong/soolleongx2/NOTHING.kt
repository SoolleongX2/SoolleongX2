package org.wandukong.soolleongx2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_n_o_t_h_i_n_g.*

class NOTHING : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_n_o_t_h_i_n_g)

        var bottle = intent.getStringExtra("bottle").toString()
        tv_no.text = bottle
    }
}