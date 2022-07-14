package ph.cadet.cabote.talan.attendance.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import ph.cadet.cabote.talan.attendance.databinding.CourseViewBinding
import ph.cadet.cabote.talan.attendance.model.Course

class CourseAdapter(
    private val context: Context,
    private var courses: ArrayList<Course>
) : RecyclerView.Adapter<CourseAdapter.CourseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val courseBinding = CourseViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        return CourseViewHolder(courseBinding)
    }

    fun setCourses(newCourses:ArrayList<Course>){
        courses.clear()
        courses.addAll(newCourses)
        notifyDataSetChanged()
    }

    inner class CourseViewHolder(private val courseBinding: CourseViewBinding) :
        RecyclerView.ViewHolder(courseBinding.root) {
        fun bindCourses(data: Course) {
            courseBinding.textViewCourseID.text = data.courseID
            courseBinding.textViewCourseName.text = data.courseName
            courseBinding.textViewCourseStartTime.text = data.courseStartTime
            courseBinding.textViewCourseEndTime.text = data.courseEndTime
            courseBinding.textViewCourseClass.text = data.courseClass
        }
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        holder.bindCourses(courses[position])
    }

    override fun getItemCount()= courses.size
}