package org.wandukong.soolleongx2

import android.os.Bundle
import android.util.Log
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
        return inflater.inflate(R.layout.fragment_report, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var dayCount: Int = 0 //기록 수
        val getGoalCall: Call<GoalResponseData> = ReportServiceImpl.service.getGoal(
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6OSwiaWF0IjoxNjA1OTk1MzczLCJleHAiOjE2MDYwMjA1NzMsImlzcyI6InNvb2xsZXVuZyJ9.49bF4XhHsJu56Ebi6JltHfIE2LUFqJXVnC7_5hJWhRg"
        )
        val getRecordWeekCall: Call<RecordWeekResponseData> = ReportServiceImpl.service.getRecordWeek(
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6OSwiaWF0IjoxNjA1OTk1MzczLCJleHAiOjE2MDYwMjA1NzMsImlzcyI6InNvb2xsZXVuZyJ9.49bF4XhHsJu56Ebi6JltHfIE2LUFqJXVnC7_5hJWhRg"
        )
        getGoalCall.enqueue(object: Callback<GoalResponseData>{
            override fun onFailure(call: Call<GoalResponseData>, t: Throwable) {
                Log.d("onFailure", "통신 실패: ${t.message}")
            }
            override fun onResponse(
                call: Call<GoalResponseData>,
                response: Response<GoalResponseData>
            ) {
                response.takeIf { it.isSuccessful }
                    ?.body()
                    ?.let{ data->
                        Log.d("tag","실행")
                        txt_goal_bottle.text = data.data.bottle.toString() + "병"  // 이번 주 목표
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
                    ?.let{ data->
                        dayCount=data.data.records.size
                        when(dayCount){
                            1 -> {
                                txt_day1_bottle.text = data.data.records[dayCount].bottle.toString()+"병 "+
                                        data.data.records[dayCount].glass.toString()+"잔"

                                txt_day1_date.text = data.data.records[dayCount].month.toString()+"/"+data.data.records[dayCount].day.toString()
                            }
                            2 -> {
                                constraint_day1.setBackgroundResource(R.drawable.bg_repo_last)
                                constraint_day2.setBackgroundResource(R.drawable.bg_repo_today)

                                txt_day1_bottle.text = data.data.records[dayCount-1].bottle.toString()+"병 "+
                                        data.data.records[dayCount].glass.toString()+"잔"
                                txt_day2_bottle.text = data.data.records[dayCount].bottle.toString()+"병 "+
                                        data.data.records[dayCount].glass.toString()+"잔"

                                txt_day1_date.text = data.data.records[dayCount-1].month.toString()+"/"+data.data.records[dayCount-1].day.toString()
                                txt_day2_date.text = data.data.records[dayCount].month.toString()+"/"+data.data.records[dayCount].day.toString()
                            }
                            3 -> {
                                constraint_day1.setBackgroundResource(R.drawable.bg_repo_last)
                                constraint_day2.setBackgroundResource(R.drawable.bg_repo_last)
                                constraint_day3.setBackgroundResource(R.drawable.bg_repo_today)

                                txt_day1_bottle.text = data.data.records[dayCount-2].bottle.toString()+"병 "+
                                        data.data.records[dayCount].glass.toString()+"잔"
                                txt_day2_bottle.text = data.data.records[dayCount-1].bottle.toString()+"병 "+
                                        data.data.records[dayCount].glass.toString()+"잔"
                                txt_day3_bottle.text = data.data.records[dayCount].bottle.toString()+"병 "+
                                        data.data.records[dayCount].glass.toString()+"잔"

                                txt_day1_date.text = data.data.records[dayCount-1].month.toString()+"/"+data.data.records[dayCount-2].day.toString()
                                txt_day2_date.text = data.data.records[dayCount-1].month.toString()+"/"+data.data.records[dayCount-1].day.toString()
                                txt_day3_date.text = data.data.records[dayCount].month.toString()+"/"+data.data.records[dayCount].day.toString()
                            }
                            4 -> {
                                constraint_day1.setBackgroundResource(R.drawable.bg_repo_last)
                                constraint_day2.setBackgroundResource(R.drawable.bg_repo_last)
                                constraint_day3.setBackgroundResource(R.drawable.bg_repo_last)
                                constraint_day4.setBackgroundResource(R.drawable.bg_repo_today)

                                txt_day1_bottle.text = data.data.records[dayCount-3].bottle.toString()+"병 "+
                                        data.data.records[dayCount].glass.toString()+"잔"
                                txt_day2_bottle.text = data.data.records[dayCount-2].bottle.toString()+"병 "+
                                        data.data.records[dayCount].glass.toString()+"잔"
                                txt_day3_bottle.text = data.data.records[dayCount-1].bottle.toString()+"병 "+
                                        data.data.records[dayCount].glass.toString()+"잔"
                                txt_day4_bottle.text = data.data.records[dayCount].bottle.toString()+"병 "+
                                        data.data.records[dayCount].glass.toString()+"잔"

                                txt_day1_date.text = data.data.records[dayCount-3].month.toString()+"/"+data.data.records[dayCount-2].day.toString()
                                txt_day2_date.text = data.data.records[dayCount-2].month.toString()+"/"+data.data.records[dayCount-1].day.toString()
                                txt_day3_date.text = data.data.records[dayCount-1].month.toString()+"/"+data.data.records[dayCount].day.toString()
                                txt_day4_date.text = data.data.records[dayCount].month.toString()+"/"+data.data.records[dayCount].day.toString()
                            }
                            5 -> {
                                constraint_day1.setBackgroundResource(R.drawable.bg_repo_last)
                                constraint_day2.setBackgroundResource(R.drawable.bg_repo_last)
                                constraint_day3.setBackgroundResource(R.drawable.bg_repo_last)
                                constraint_day4.setBackgroundResource(R.drawable.bg_repo_last)
                                constraint_day5.setBackgroundResource(R.drawable.bg_repo_today)

                                txt_day1_bottle.text = data.data.records[dayCount-4].bottle.toString()+"병 "+
                                        data.data.records[dayCount].glass.toString()+"잔"
                                txt_day2_bottle.text = data.data.records[dayCount-3].bottle.toString()+"병 "+
                                        data.data.records[dayCount].glass.toString()+"잔"
                                txt_day3_bottle.text = data.data.records[dayCount-2].bottle.toString()+"병 "+
                                        data.data.records[dayCount].glass.toString()+"잔"
                                txt_day4_bottle.text = data.data.records[dayCount-1].bottle.toString()+"병 "+
                                        data.data.records[dayCount].glass.toString()+"잔"
                                txt_day5_bottle.text = data.data.records[dayCount].bottle.toString()+"병 "+
                                        data.data.records[dayCount].glass.toString()+"잔"

                                txt_day1_date.text = data.data.records[dayCount-4].month.toString()+"/"+data.data.records[dayCount-2].day.toString()
                                txt_day2_date.text = data.data.records[dayCount-3].month.toString()+"/"+data.data.records[dayCount-1].day.toString()
                                txt_day3_date.text = data.data.records[dayCount-2].month.toString()+"/"+data.data.records[dayCount].day.toString()
                                txt_day4_date.text = data.data.records[dayCount-1].month.toString()+"/"+data.data.records[dayCount].day.toString()
                                txt_day5_date.text = data.data.records[dayCount].month.toString()+"/"+data.data.records[dayCount].day.toString()
                            }
                            6 -> {
                                constraint_day1.setBackgroundResource(R.drawable.bg_repo_last)
                                constraint_day2.setBackgroundResource(R.drawable.bg_repo_last)
                                constraint_day3.setBackgroundResource(R.drawable.bg_repo_last)
                                constraint_day4.setBackgroundResource(R.drawable.bg_repo_last)
                                constraint_day5.setBackgroundResource(R.drawable.bg_repo_last)
                                constraint_day6.setBackgroundResource(R.drawable.bg_repo_today)

                                txt_day1_bottle.text = data.data.records[dayCount-5].bottle.toString()+"병 "+
                                        data.data.records[dayCount].glass.toString()+"잔"
                                txt_day2_bottle.text = data.data.records[dayCount-4].bottle.toString()+"병 "+
                                        data.data.records[dayCount].glass.toString()+"잔"
                                txt_day3_bottle.text = data.data.records[dayCount-3].bottle.toString()+"병 "+
                                        data.data.records[dayCount].glass.toString()+"잔"
                                txt_day4_bottle.text = data.data.records[dayCount-2].bottle.toString()+"병 "+
                                        data.data.records[dayCount].glass.toString()+"잔"
                                txt_day5_bottle.text = data.data.records[dayCount-1].bottle.toString()+"병 "+
                                        data.data.records[dayCount].glass.toString()+"잔"
                                txt_day6_bottle.text = data.data.records[dayCount].bottle.toString()+"병 "+
                                        data.data.records[dayCount].glass.toString()+"잔"

                                txt_day1_date.text = data.data.records[dayCount-5].month.toString()+"/"+data.data.records[dayCount-2].day.toString()
                                txt_day2_date.text = data.data.records[dayCount-4].month.toString()+"/"+data.data.records[dayCount-2].day.toString()
                                txt_day3_date.text = data.data.records[dayCount-3].month.toString()+"/"+data.data.records[dayCount-1].day.toString()
                                txt_day4_date.text = data.data.records[dayCount-2].month.toString()+"/"+data.data.records[dayCount].day.toString()
                                txt_day5_date.text = data.data.records[dayCount-1].month.toString()+"/"+data.data.records[dayCount].day.toString()
                                txt_day6_date.text = data.data.records[dayCount].month.toString()+"/"+data.data.records[dayCount].day.toString()
                            }
                            7 -> {
                                constraint_day1.setBackgroundResource(R.drawable.bg_repo_last)
                                constraint_day2.setBackgroundResource(R.drawable.bg_repo_last)
                                constraint_day3.setBackgroundResource(R.drawable.bg_repo_last)
                                constraint_day4.setBackgroundResource(R.drawable.bg_repo_last)
                                constraint_day5.setBackgroundResource(R.drawable.bg_repo_last)
                                constraint_day6.setBackgroundResource(R.drawable.bg_repo_last)
                                constraint_day7.setBackgroundResource(R.drawable.bg_repo_today)

                                txt_day1_bottle.text = data.data.records[dayCount-6].bottle.toString()+"병 "+
                                        data.data.records[dayCount].glass.toString()+"잔"
                                txt_day2_bottle.text = data.data.records[dayCount-5].bottle.toString()+"병 "+
                                        data.data.records[dayCount].glass.toString()+"잔"
                                txt_day3_bottle.text = data.data.records[dayCount-4].bottle.toString()+"병 "+
                                        data.data.records[dayCount].glass.toString()+"잔"
                                txt_day4_bottle.text = data.data.records[dayCount-3].bottle.toString()+"병 "+
                                        data.data.records[dayCount].glass.toString()+"잔"
                                txt_day5_bottle.text = data.data.records[dayCount-2].bottle.toString()+"병 "+
                                        data.data.records[dayCount].glass.toString()+"잔"
                                txt_day6_bottle.text = data.data.records[dayCount-1].bottle.toString()+"병 "+
                                        data.data.records[dayCount].glass.toString()+"잔"
                                txt_day7_bottle.text = data.data.records[dayCount].bottle.toString()+"병 "+
                                        data.data.records[dayCount].glass.toString()+"잔"

                                txt_day1_date.text = data.data.records[dayCount-6].month.toString()+"/"+data.data.records[dayCount-2].day.toString()
                                txt_day2_date.text = data.data.records[dayCount-5].month.toString()+"/"+data.data.records[dayCount-2].day.toString()
                                txt_day3_date.text = data.data.records[dayCount-4].month.toString()+"/"+data.data.records[dayCount-1].day.toString()
                                txt_day4_date.text = data.data.records[dayCount-3].month.toString()+"/"+data.data.records[dayCount].day.toString()
                                txt_day5_date.text = data.data.records[dayCount-2].month.toString()+"/"+data.data.records[dayCount].day.toString()
                                txt_day6_date.text = data.data.records[dayCount-1].month.toString()+"/"+data.data.records[dayCount].day.toString()
                                txt_day7_date.text = data.data.records[dayCount].month.toString()+"/"+data.data.records[dayCount].day.toString()
                            }
                            else -> {constraint_day1.setBackgroundResource(R.drawable.bg_repo_empty)}
                        }
                        txt_today_bottle.text = data.data.bottle.toString()+"병 "+data.data.glass.toString()+"잔" // 이번 주 오늘까지 마신 기록
                    }
            }
        })








    }
}