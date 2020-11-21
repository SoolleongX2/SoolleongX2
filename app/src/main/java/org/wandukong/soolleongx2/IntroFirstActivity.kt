package org.wandukong.soolleongx2

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import kotlinx.android.synthetic.main.activity_intro_first.*
import kotlinx.android.synthetic.main.activity_intro_third.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class IntroFirstActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro_first)

        val sharedPref: SharedPreferences = getSharedPreferences("pref", Context.MODE_PRIVATE)
        val sharedEdit= sharedPref.edit()

       // var uuid : UUID = UUID.randomUUID()
       //Log.e("asd",uuid.toString())

        var uuid = Settings.Secure.getString(baseContext?.contentResolver, Settings.Secure.ANDROID_ID)

        Log.d("uuid",uuid)


        button_yes.setOnClickListener {
            val call : Call<FirstResponseData> = FirstServiceimpl.service.postLogin(
                FirstRequestData(uuid = uuid)
            )
            call.enqueue(object : Callback<FirstResponseData>{
                override fun onFailure(call: Call<FirstResponseData>, t: Throwable) {
                    Log.e("tt",t.message.toString())
                }

                override fun onResponse(
                    call: Call<FirstResponseData>,
                    response: Response<FirstResponseData>
                ) {
                    response.takeIf { it.isSuccessful }
                        ?.body()
                        ?.let {
                                data->
                            sharedEdit.putString("token",data.token)
                            sharedEdit.apply()
                        }
                }
            })
            val intent = Intent(this,IntroSecondActivity::class.java)
            startActivity(intent)
        }
    }
}