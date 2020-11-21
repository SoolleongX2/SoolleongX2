package org.wandukong.soolleongx2

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.fragment_record.*
import okhttp3.ResponseBody
import org.json.JSONObject
import org.wandukong.soolleongx2.Data.ReceiveResponseAlcoholData
import org.wandukong.soolleongx2.Data.RecordRequestAlcoholData
import org.wandukong.soolleongx2.Data.RecordResponseAlcoholData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class RecordFragment : Fragment() {

    @RequiresApi(Build.VERSION_CODES.O)
    val current = LocalDateTime.now()
    @RequiresApi(Build.VERSION_CODES.O)
    val formatter = DateTimeFormatter.ofPattern("MM월 dd일")
    @RequiresApi(Build.VERSION_CODES.O)
    val formatted = current.format(formatter)

    private var bottle : Int = 2
    private var glass : Int = 4
    private var token : String = ""
    private var IsStartRecord = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_record, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //receiveAlcoholData()

        textView_date.text = formatted
        textView_remain.text = bottle.toString() + "병" + glass.toString() + "잔"

        buttonClick()

        
    }

    private fun receiveAlcoholData(){
        val call : Call<ReceiveResponseAlcoholData> = ReceiveServiceImpl.service.receiveAlcohol(
//            ReceiveRequestAlcoholData(token = token)
        )
        call.enqueue(object : Callback<ReceiveResponseAlcoholData> {
            override fun onFailure(call: Call<ReceiveResponseAlcoholData>, t: Throwable) {
            }
            override fun onResponse(
                call: Call<ReceiveResponseAlcoholData>,
                response: Response<ReceiveResponseAlcoholData>
            ) {
                response.takeIf { it.isSuccessful }
                    ?.body()
                    ?.let { data ->
                        bottle = data.bottle
                        glass = data.glass


                    }
                    ?: showError(response.errorBody())
            }
        })
    }

    private fun sendAlcoholData(){
        val call : Call<RecordResponseAlcoholData> = RecordServiceImpl.service.recordAlcohol(
            RecordRequestAlcoholData(bottle = bottle, glass = glass, token = token)
        )
        call.enqueue(object : Callback<RecordResponseAlcoholData> {
            override fun onFailure(call: Call<RecordResponseAlcoholData>, t: Throwable) {
            }
            override fun onResponse(
                call: Call<RecordResponseAlcoholData>,
                response: Response<RecordResponseAlcoholData>
            ) {
                response.takeIf { it.isSuccessful }
                    ?.body()
                    ?.let {

                    }
                    ?: showError(response.errorBody())
            }
        })
    }

    private fun buttonClick(){

        button_start_end.setOnClickListener {       // 기록 시작/종료 버튼 

            if(IsStartRecord == 0){
                button_bottle.isEnabled = true
                button_glass.isEnabled = true
                button_start_end.text = "기록 종료"
                IsStartRecord = 1
            }else{
                //sendAlcoholData()
                button_bottle.isEnabled = false
                button_glass.isEnabled = false
                button_start_end.text = "기록 시작"
                IsStartRecord = 0
            }
        }

        button_bottle.setOnClickListener {   // 병 버튼
            if(bottle == 0){
                textView_remain.text = "목표"
                textView.text = "에 실패했어요. :("
            }else{
                bottle--
                textView_remain.text = bottle.toString() + "병" + glass.toString() + "잔"
            }
        }
        
        button_glass.setOnClickListener {  // 잔 버튼
            if(glass == 0){
                if(bottle == 0){
                    textView_remain.text = "목표"
                    textView.text = "에 실패했어요. :("
                }else{
                    bottle--
                    glass = 6
                    textView_remain.text = bottle.toString() + "병" + glass.toString() + "잔"
                }
            }else{
                glass--
                textView_remain.text = bottle.toString() + "병" + glass.toString() + "잔"
            }
        }
    }

    private fun showError(error : ResponseBody?){
        val e = error ?: return
        val ob = JSONObject(e.string())
        Log.e("RecordFragment", ob.getString("message"))
    }
}