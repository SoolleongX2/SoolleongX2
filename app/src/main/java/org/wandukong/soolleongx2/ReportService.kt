import org.wandukong.soolleongx2.GoalResponseData
import org.wandukong.soolleongx2.RecordWeekResponseData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
interface ReportService {
    @Headers("Content-Type:application/json")
    @GET("/goals")
    fun getGoal(
            @Header("jwt") token : String
    ): Call<GoalResponseData>

    @GET("/record/week")
    fun getRecordWeek(
            @Header("jwt") token : String
    ): Call<RecordWeekResponseData>
}