package ph.cadet.cabote.talan.attendance.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ph.cadet.cabote.talan.attendance.databinding.AttendanceViewBinding
import ph.cadet.cabote.talan.attendance.databinding.ClassViewBinding
import ph.cadet.cabote.talan.attendance.model.Attendance
import ph.cadet.cabote.talan.attendance.model.Classes

class AttendanceAdapter (
    private val context: Context,
    private var attendance: ArrayList<Attendance>
) : RecyclerView.Adapter<AttendanceAdapter.AttendanceViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttendanceViewHolder {
        val attendanceBinding = AttendanceViewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return AttendanceViewHolder(attendanceBinding)
    }

    inner class AttendanceViewHolder(private val attendanceBinding: AttendanceViewBinding) :
        RecyclerView.ViewHolder(attendanceBinding.root) {
        fun bindAttendance(data: Attendance) {

            attendanceBinding.textViewStudentName.text = data.firstName + " " + data.lastName
            attendanceBinding.textViewStudentNumber.text = data.studentNumber
            attendanceBinding.textViewStudentEmail.text = data.studentEmail
            }
        }

    override fun onBindViewHolder(holder: AttendanceViewHolder, position: Int) {
        holder.bindAttendance(attendance[position])
    }

    override fun getItemCount()= attendance.size
}