package ph.cadet.cabote.talan.attendance

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import ph.cadet.cabote.talan.attendance.adapter.CourseAdapter
import ph.cadet.cabote.talan.attendance.databinding.ActivityMainBinding
import ph.cadet.cabote.talan.attendance.model.Course


class MainActivity : AppCompatActivity() {

    private var mAuth = FirebaseAuth.getInstance()
    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerViewAdapter: CourseAdapter
    private var courses = ArrayList<Course>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate((layoutInflater))
        setContentView(binding.root)

        getInstructorName()
        getCourses()

        //ADD-COURSE-FUNCTION
        binding.buttonAddCourse.setOnClickListener{
            startActivity(Intent(this, AddCourseActivity::class.java))
        }


        //CHANGE-PASSWORD-FUNCTION
        binding.changePasswordBtn.setOnClickListener {
            val view :  View = layoutInflater.inflate(R.layout.activity_change_password, null)
            val modal = BottomSheetDialog(this)

            modal.setContentView(view)
            modal.show()

        }

        //LOGOUT-FUNCTION
        binding.logoutBtn.setOnClickListener{
            mAuth.signOut()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun getInstructorName(){
        val id = FirebaseAuth.getInstance().currentUser?.uid.toString()
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()

        db.collection("users").whereEqualTo("UserID", id).get()
            .addOnSuccessListener { users ->
                for (user in users) {
                    binding.textviewInstructorName.text = user.data.get("Name").toString()
                }
            }
    }

    private fun getCourses() {
        courses.add(Course("CourseID", "CourseName", "Course Start", "Course End", "CourseClass"))
        courses.add(Course("CourseID2", "CourseName2", "Course Start", "Course End", "CourseClass2"))
        courses.add(Course("CourseID", "CourseName", "Course Start", "Course End", "CourseClass"))
        courses.add(Course("CourseID2", "CourseName2", "Course Start", "Course End", "CourseClass2"))
        courses.add(Course("CourseID", "CourseName", "Course Start", "Course End", "CourseClass"))
        courses.add(Course("CourseID2", "CourseName2", "Course Start", "Course End", "CourseClass2"))
        courses.add(Course("CourseID", "CourseName", "Course Start", "Course End", "CourseClass"))
        courses.add(Course("CourseID2", "CourseName2", "Course Start", "Course End", "CourseClass2"))
        courses.add(Course("CourseID", "CourseName", "Course Start", "Course End", "CourseClass"))
        courses.add(Course("CourseID2", "CourseName2", "Course Start", "Course End", "CourseClass2"))
        courses.add(Course("CourseID", "CourseName", "Course Start", "Course End", "CourseClass"))
        courses.add(Course("CourseID2", "CourseName2", "Course Start", "Course End", "CourseClass2"))

        recyclerViewAdapter = CourseAdapter(applicationContext, courses)
        binding.recyclerviewCourses.adapter = recyclerViewAdapter

        binding.recyclerviewCourses.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
    }
    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = FirebaseAuth.getInstance().currentUser
        if(currentUser == null){
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}