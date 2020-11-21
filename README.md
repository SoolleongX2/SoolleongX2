## 💻서비스 이름 및 한 줄 소개

- 서비스 이름 : 술렁술렁
- 한 줄 소개 : 이번엔 진짜 금주! 나의 7일 음주 습관 트래커

## 💻팀원 별 개발 담당 

- 손연주
  - font 설정
  - 앱 아이콘 이미지 설정
  - 술렁 레포트 화면
    - 음주량 기록화면에서 기록종료 클릭할 때 저장된 값 서버에서 값가져오기
      - 이때 서버에 있는 기록 수 만큼 기록 저장되도록 함.
  - 각 위젯 background에 적용할 .xml파일 생성
- 양승완
  - 뷰페이저 구현
  - 음주량 기록 화면
    - 현재 날짜 받아오기
    - 기록 시작 버튼 클릭 시 -> 음주 기록 버튼 활성화 및 기록에 따른 남은 주량 계산
    - 기록 종료 버튼 클릭 시 -> 음주 기록 버튼 비활성화 및 서버에 남은 주량 기록, 술렁 레포트 화면으로 이동
- 이형준
  - SharedPreferences  구현
  - ImageButton 클릭 시 이미지 변경
  - 주종 선택, 목표 설정 화면
  - uuid 생성
- 공동
  - 각자 필요한 서버 연결 구현
  
  
  
## Retrofit Code

사용자 식별을 위한 UUID 생성 후, 서버에 저장
```kotlin
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
					body ->
				sharedEdit.putString("token",body.data.token)
				sharedEdit.apply()
			}
	}
})
```

사용자가 기록 종료 후, 서버에 남은 주량 데이터 저장.
```kotlin
val call : Call<RecordResponseAlcoholData> = RecordServiceImpl.service.recordAlcohol(
            token = token,
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
```

이번주 목표 주량을 받는다.  
그래서, 목표 주량과 남은 주량을 계산하여, 뷰에 표시한다.   
```kotlin
val call : Call<ReceiveResponseAlcoholData> = ReceiveServiceImpl.service.receiveAlcohol(
	token
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
				bottle = data.data.bottle
				glass = data.data.glass

				cur = (goal - ( bottle * 7 + glass)) / 7

			}
			?: showError(response.errorBody())
	}
})
```

일주일 동안 기록된 주량 리스트 표시.  
```kotlin
val getRecordWeekCall: Call<RecordWeekResponseData> = ReportServiceImpl.service.getRecordWeek(
 token)
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
```


```kotlin

```