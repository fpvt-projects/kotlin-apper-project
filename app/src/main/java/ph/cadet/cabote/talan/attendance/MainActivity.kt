package ph.cadet.cabote.talan.attendance

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.widget.Toast

import androidx.recyclerview.widget.LinearLayoutManager

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import ph.cadet.cabote.talan.attendance.adapter.CourseAdapter
import ph.cadet.cabote.talan.attendance.databinding.ActivityMainBinding
import ph.cadet.cabote.talan.attendance.model.Course
import java.time.LocalTime

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerViewAdapter: CourseAdapter
    private var courses = ArrayList<Course>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate((layoutInflater))
        setContentView(binding.root)

        init()
        recyclerViewAdapter = CourseAdapter(applicationContext, courses)
        binding.recyclerviewCourses.adapter = recyclerViewAdapter

        binding.recyclerviewCourses.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)

        val id = FirebaseAuth.getInstance().currentUser?.uid.toString()
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()

        db.collection("users").whereEqualTo("UserID", id).get()
            .addOnSuccessListener { users ->
                for (user in users) {
                    Toast.makeText(this, user.data.get("Name").toString(), Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {

            }

    }

    private fun init() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            courses.add(Course("CourseID", "CourseName", LocalTime.now(), LocalTime.now(), "CourseClass"))
            courses.add(Course("CourseID2", "CourseName2", LocalTime.now(), LocalTime.now(), "CourseClass2"))
        }
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