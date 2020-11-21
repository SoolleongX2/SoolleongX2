package org.wandukong.soolleongx2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_intro_third.*
import kotlinx.android.synthetic.main.item_spinner.*
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class IntroThirdActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro_third)

        val items = resources.getStringArray(R.array.spinner_entries)
        val sp_adapter = ArrayAdapter(this,R.layout.item_spinner,items)
        spinner.adapter = sp_adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {

            }
        }

        var bottle = spinner.selectedItem.toString().toInt()
        var drink = intent.getIntExtra("drink",0)

        val call : Call<SecondResponseData> = SecondServiceimpl.service.postBottle(
            SecondRequestData(drink = drink , bottle = bottle)
        )
        call.enqueue(object : Callback<SecondResponseData>{
            override fun onFailure(call: Call<SecondResponseData>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<SecondResponseData>,
                response: Response<SecondResponseData>
            ) {
                response.takeIf { it.isSuccessful }
                    ?.body()
                    ?.let {

                    }
            }

        })

        button_success.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

    }
}