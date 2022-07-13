package ph.cadet.cabote.talan.attendance.model
import com.google.gson.annotations.SerializedName
import java.time.LocalTime

class Course {
    @SerializedName("courseID")
    var courseID = ""

    @SerializedName("courseName")
    var courseName = ""

    @SerializedName("courseStartTime")
    var courseStartTime : LocalTime

    @SerializedName("courseEndTime")
    var courseEndTime : LocalTime

    @SerializedName("courseClass")
    var courseClass = ""

    constructor(courseID: String, courseName: String, courseStartTime: LocalTime, courseEndTime: LocalTime, courseClass: String){
        this.courseID = courseID
        this.courseName = courseName
        this.courseStartTime = courseStartTime
        this.courseEndTime = courseEndTime
        this.courseClass = courseClass
    }
}