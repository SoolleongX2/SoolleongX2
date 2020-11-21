package org.wandukong.soolleongx2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_intro_second.*

private var drink : Int = 0
class IntroSecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro_second)


        button_soju.setOnClickListener {
            //button_soju.setImageResource(R.drawable.ic_launcher_background)
            drink = 0
        }
        button_beer.setOnClickListener {
            drink = 1
        }
        button_next.setOnClickListener {
            val intent = Intent(this, IntroThirdActivity::class.java)
            intent.putExtra("drink",drink)
            startActivity(intent)
        }
    }
}