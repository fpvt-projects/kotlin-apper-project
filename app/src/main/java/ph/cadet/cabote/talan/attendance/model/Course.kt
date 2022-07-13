package ph.cadet.cabote.talan.attendance.model
import com.google.gson.annotations.SerializedName
import java.time.LocalTime

class Course {
    @SerializedName("courseID")
    var courseID = ""

    @SerializedName("courseName")
    var courseName = ""

    @SerializedName("courseStartTime")
    var courseStartTime = ""

    @SerializedName("courseEndTime")
    var courseEndTime = ""

    @SerializedName("courseClass")
    var courseClass = ""

    constructor(courseID: String, courseName: String, courseStartTime: String, courseEndTime: String, courseClass: String){
        this.courseID = courseID
        this.courseName = courseName
        this.courseStartTime = courseStartTime
        this.courseEndTime = courseEndTime
        this.courseClass = courseClass
    }
}