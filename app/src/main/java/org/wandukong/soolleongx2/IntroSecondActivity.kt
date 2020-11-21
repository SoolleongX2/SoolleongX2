package org.wandukong.soolleongx2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_intro_second.*

private var alchol_type : Int = 1
class IntroSecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro_second)


        button_soju.setOnClickListener {
            button_soju.setImageResource(R.drawable.btn_soju_selected)
            alchol_type = 1
        }
        button_beer.setOnClickListener {
            alchol_type = 2
        }
        button_next.setOnClickListener {
            val intent = Intent(this, IntroThirdActivity::class.java)
            intent.putExtra("alchol_type",alchol_type)
            startActivity(intent)
        }
    }
}