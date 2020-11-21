package org.wandukong.soolleongx2

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
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


    private var cntGlass = 0
    private var cntBottle = 0
    private var goal = 7*7
    private var cur = 0
    private var bottle = 7
    private var glass = 0
    private var token = ""
    private var IsStartRecord = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_record, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //receiveAlcoholData()
        textView_date.text = formatted

        //receiveGoalAlcoholData()

        buttonClick(view)
    }

    private fun receiveGoalAlcoholData(){
        val call : Call<GoalResponseData> = ReportServiceImpl.service.getGoal(
            "asdasdad"
        )
        call.enqueue(object : Callback<GoalResponseData> {
            override fun onFailure(call: Call<GoalResponseData>, t: Throwable) {
                TODO("Not yet implemented")
            }
            override fun onResponse(
                call: Call<GoalResponseData>,
                response: Response<GoalResponseData>
            ) {
                response.takeIf { it.isSuccessful }
                    ?.body()
                    ?.let { data ->
                        goal = data.bottle * 7


                    }
                    ?: showError(response.errorBody())
            }
        })
    }

    private fun receiveAlcoholData(){
        val call : Call<ReceiveResponseAlcoholData> = ReceiveServiceImpl.service.receiveAlcohol(
            "asdasdad"
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

                        cur = (goal - ( bottle * 7 + glass)) / 7

                    }
                    ?: showError(response.errorBody())
            }
        })
    }

    private fun sendAlcoholData(){
        val call : Call<RecordResponseAlcoholData> = RecordServiceImpl.service.recordAlcohol(
            "asdasd",
            RecordRequestAlcoholData(bottle = bottle, glass = glass)
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

    private fun buttonClick(view : View){

        val notosansBold = ResourcesCompat.getFont(view.context, R.font.notosans_bold)
        val notosansRegular = ResourcesCompat.getFont(view.context, R.font.notosans_medium)

        button_start_end.setOnClickListener {       // 기록 시작/종료 버튼 

            if(IsStartRecord == 0){
                textView_remain.setTextColor(Color.parseColor("#FF0096"))
                button_start_end.setTextColor(Color.parseColor("#FF0096"))
                button_start_end.setBackgroundResource(R.drawable.stop_drawable)
                button_bottle.isEnabled = true
                button_glass.isEnabled = true
                textView_count.typeface = notosansBold
                textView_remain.text = bottle.toString() + "병" + glass.toString() + "잔"
                textView.text = "이 한계에요!"

                button_bottle.setCompoundDrawables(null,null,null,null)
                button_bottle.setBackgroundResource(R.drawable.btn_bottle_selected)
                button_glass.setBackgroundResource(R.drawable.btn_glass_selected)
                button_glass.setCompoundDrawables(null,null,null,null)
                button_start_end.text = "기록 중단하기"
                IsStartRecord = 1
            }else{
                //sendAlcoholData()
                button_start_end.setTextColor(Color.parseColor("#ffffff"))
                button_start_end.setBackgroundResource(R.drawable.start_drawable)
                textView_remain.setTextColor(Color.parseColor("#0D2480"))
                textView_remain.text = "기록을 시작"
                textView_count.typeface = notosansRegular
                textView.text = "해 볼까요?"
                button_bottle.isEnabled = false
                button_glass.isEnabled = false
                button_start_end.text = "기록 시작하기"
                IsStartRecord = 0
            }
        }

        button_bottle.setOnClickListener {   // 병 버튼
            cntGlass++
            button_bottle.text = cntBottle.toString() + "병"
            if(bottle == 0){
                textView_remain.text = "목표"
                textView.text = "에 실패했어요. :("
            }else{
                bottle--
                textView_remain.text = bottle.toString() + "병" + glass.toString() + "잔"
            }
            cur = (goal - ( bottle * 7 + glass)) / 7

            when(cur) {
                0 -> imageView_sl.setImageResource(R.drawable.img_sull_01)
                1 -> imageView_sl.setImageResource(R.drawable.img_sull_02)
                2 -> imageView_sl.setImageResource(R.drawable.img_sull_03)
                3 -> imageView_sl.setImageResource(R.drawable.img_sull_04)
                4 -> imageView_sl.setImageResource(R.drawable.img_sull_05)
                5 -> imageView_sl.setImageResource(R.drawable.img_sull_06)
                6 -> imageView_sl.setImageResource(R.drawable.img_sull_07)
                7 -> imageView_sl.setImageResource(R.drawable.img_sull_08)
            }
        }
        
        button_glass.setOnClickListener {  // 잔 버튼
            cntGlass++
            if(cntGlass == 7) {
                cntBottle++
                cntGlass = 0
                button_bottle.text = cntBottle.toString() + "병"
            }
            button_glass.text = cntGlass.toString() + "잔"
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
            cur = (goal - ( bottle * 7 + glass)) / 7
            when(cur) {
                0 -> imageView_sl.setImageResource(R.drawable.img_sull_01)
                1 -> imageView_sl.setImageResource(R.drawable.img_sull_02)
                2 -> imageView_sl.setImageResource(R.drawable.img_sull_03)
                3 -> imageView_sl.setImageResource(R.drawable.img_sull_04)
                4 -> imageView_sl.setImageResource(R.drawable.img_sull_05)
                5 -> imageView_sl.setImageResource(R.drawable.img_sull_06)
                6 -> imageView_sl.setImageResource(R.drawable.img_sull_07)
                7 -> imageView_sl.setImageResource(R.drawable.img_sull_08)
            }
        }
    }

    private fun showError(error : ResponseBody?){
        val e = error ?: return
        val ob = JSONObject(e.string())
        Log.e("RecordFragment", ob.getString("message"))
    }
}