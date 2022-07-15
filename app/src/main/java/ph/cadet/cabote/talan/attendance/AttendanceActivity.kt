package ph.cadet.cabote.talan.attendance

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import ph.cadet.cabote.talan.attendance.adapter.AttendanceAdapter
import ph.cadet.cabote.talan.attendance.adapter.ClassAdapter
import ph.cadet.cabote.talan.attendance.databinding.ActivityAddClassBinding
import ph.cadet.cabote.talan.attendance.databinding.ActivityAttendanceBinding
import ph.cadet.cabote.talan.attendance.model.Attendance
import ph.cadet.cabote.talan.attendance.model.Classes
import ph.cadet.cabote.talan.attendance.model.Course

class AttendanceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAttendanceBinding
    private lateinit var recyclerViewAdapter: AttendanceAdapter
    private lateinit var attendance : ArrayList<Attendance>

    private lateinit var classObject : Classes
    private lateinit var course : Course

    private val id = FirebaseAuth.getInstance().currentUser?.uid.toString()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAttendanceBinding.inflate((layoutInflater))
        setContentView(binding.root)

        classObject = intent.getParcelableExtra<Classes>("class")!!

        binding.textViewClassBlock.text = classObject.classBlock
        binding.textViewClassDate.text = classObject.date
        binding.textViewTotal.text = classObject.totalAttendees.toString()

        classObject = intent.getParcelableExtra<Classes>("class")!!
        course = intent.getParcelableExtra<Course>("course")!!

        getAttendance()

        //ADD-ATTENDANCE-FUNCTION
        binding.buttonScanQR.setOnClickListener{
            val intent = Intent(this, QRScannerActivity::class.java)
            intent.putExtra("class", classObject)
            intent.putExtra("course", course)
            startActivity(intent)
        }

        //swipe refresh
        binding.refresh.setOnRefreshListener {
            if (binding.refresh.isRefreshing) {
                binding.refresh.isRefreshing = false
            }
            getAttendance()
        }
    }

    fun getAttendance(){
        attendance = ArrayList()

        db.collection("attendance").whereEqualTo("userID", id).get()
            .addOnSuccessListener { attendanceList ->
                for (item in attendanceList) {
                    if (item.data["courseID"].toString().equals(course.courseID)) {
                        if(item.data["date"].toString().equals(classObject.date))
                        attendance.add(Attendance(item.data["firstName"].toString(),
                            item.data["lastName"].toString(),
                            item.data["studentEmail"].toString(),
                            item.data["studentNumber"].toString(),
                            item.data["date"].toString()
                        ))
                    }
                }
                recyclerViewAdapter = AttendanceAdapter(applicationContext, attendance)
                binding.recyclerviewClasses.adapter = recyclerViewAdapter
                binding.recyclerviewClasses.layoutManager =
                    LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)

            }
    }
}