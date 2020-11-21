package org.wandukong.soolleongx2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_report.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReportFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_record, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val getGoalCall: Call<GoalResponseData> = ReportServiceImpl.service.getGoal(
            ReceiveRequestAlcoholData()
        )
        val getRecordWeekCall: Call<RecordWeekResponseData> = ReportServiceImpl.service.getRecordWeek(
            ReceiveRequestAlcoholData()
        )
        getGoalCall.enqueue(object: Callback<GoalResponseData>{
            override fun onFailure(call: Call<GoalResponseData>, t: Throwable) {
                // 통신 실패 로직
            }
            override fun onResponse(
                call: Call<GoalResponseData>,
                response: Response<GoalResponseData>
            ) {
                response.takeIf { it.isSuccessful }
                    ?.body()
                    ?.let{

                    }
            }
        })
        getRecordWeekCall.enqueue(object: Callback<RecordWeekResponseData>{
            override fun onFailure(call: Call<RecordWeekResponseData>, t: Throwable) {
                // 통신 실패 로직
            }
            override fun onResponse(
                call: Call<RecordWeekResponseData>,
                response: Response<RecordWeekResponseData>
            ) {
                response.takeIf { it.isSuccessful }
                    ?.body()
                    ?.let{

                    }
            }
        })


        txt_goal_bottle.text = "병"+"잔"   // 이번 주 목표
        txt_today_bottle.text = "이번주는 "+"병"+"잔 마심." // 이번 주 오늘까지 마신 기록

        txt_day1_bottle.text = "병"+"잔"
        txt_day2_bottle.text = "병"+"잔"
        txt_day3_bottle.text = "병"+"잔"
        txt_day4_bottle.text = "병"+"잔"
        txt_day5_bottle.text = "병"+"잔"
        txt_day6_bottle.text = "병"+"잔"
        txt_day7_bottle.text = "병"+"잔"

        txt_day1_date.text = "월"+"일"
        txt_day2_date.text = "월"+"일"
        txt_day3_date.text = "월"+"일"
        txt_day4_date.text = "월"+"일"
        txt_day5_date.text = "월"+"일"
        txt_day6_date.text = "월"+"일"
        txt_day7_date.text = "월"+"일"


    }
}