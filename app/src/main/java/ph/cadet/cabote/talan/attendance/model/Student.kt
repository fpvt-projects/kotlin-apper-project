package ph.cadet.cabote.talan.attendance.model

class Student {
    data class StudentInfo(
        val firstName : String,
        val lastName: String,
        val studentEmail: String,
        val studentNumber: String
    )
}