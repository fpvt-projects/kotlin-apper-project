package ph.cadet.cabote.talan.attendance

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import ph.cadet.cabote.talan.attendance.adapter.ClassAdapter
import ph.cadet.cabote.talan.attendance.adapter.CourseAdapter
import ph.cadet.cabote.talan.attendance.databinding.ActivityCourseBinding
import ph.cadet.cabote.talan.attendance.databinding.ActivityMainBinding
import ph.cadet.cabote.talan.attendance.databinding.CourseViewBinding
import ph.cadet.cabote.talan.attendance.model.Attendance
import ph.cadet.cabote.talan.attendance.model.Classes
import ph.cadet.cabote.talan.attendance.model.Course

class CourseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCourseBinding
    private lateinit var recyclerViewAdapter: ClassAdapter
    private lateinit var classList: ArrayList<Classes>

    private lateinit var course: Course

    private val id = FirebaseAuth.getInstance().currentUser?.uid.toString()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCourseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        course = intent.getParcelableExtra<Course>("course")!!
        if (course != null) {
            binding.textViewCourseID.text = course.courseID.toString()
            binding.textViewCourseName.text = course.courseName.toString()
            binding.textViewCourseStartTime.text = course.courseStartTime.toString()
            binding.textViewCourseEndTime.text = course.courseEndTime.toString()
            binding.textViewCourseClass.text = course.courseClass.toString()
        }

        getClasses()

        //ADD-COURSE-FUNCTION
        binding.buttonAddClass.setOnClickListener {
            val intent = Intent(this, AddClassActivity::class.java)
            intent.putExtra("course", course)
            startActivity(intent)
        }

        //swipe refresh
        binding.refresh.setOnRefreshListener {
            if (binding.refresh.isRefreshing) {
                binding.refresh.isRefreshing = false
            }
            getClasses()
        }

    }

    private fun getClasses() {
        classList = ArrayList()


        db.collection("classes").whereEqualTo("userID", id).get()
            .addOnSuccessListener { classes ->
                for (classItem in classes) {
                    if (classItem.data["courseID"].toString().equals(course.courseID)) {

                        var totalAttendees = 0
                            db.collection("attendance").whereEqualTo("courseID", course.courseID).get()
                            .addOnSuccessListener { attendanceList ->
                            var attendees = 0
                                for (item in attendanceList) {
                                    if (item.data["date"].toString().equals(classItem.data["classDate"])) {
                                        attendees += 1
                                    }
                                }
                                totalAttendees = attendees
                            }
                        classList.add(Classes(classItem.data["classBlock"].toString(),
                            classItem.data["classDate"].toString(), totalAttendees
                        ))
                    }
                }
                recyclerViewAdapter = ClassAdapter(applicationContext, classList)
                binding.recyclerviewClasses.adapter = recyclerViewAdapter
                binding.recyclerviewClasses.layoutManager =
                    LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)

                recyclerViewAdapter.onItemClick = {
                    val intent = Intent(this, AttendanceActivity::class.java)
                    intent.putExtra("class", it)
                    intent.putExtra("course", course)
                    startActivity(intent)
                }
            }
    }
}