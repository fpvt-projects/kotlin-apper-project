package ph.cadet.cabote.talan.attendance

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import ph.cadet.cabote.talan.attendance.databinding.ActivityAddCourseBinding
import ph.cadet.cabote.talan.attendance.model.Course
import java.sql.Time
import java.text.SimpleDateFormat
import java.util.*
import java.util.Calendar.getInstance
import android.widget.Toast

class AddCourseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddCourseBinding
    //private var courseList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddCourseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*
        val courseIDSpinner = binding.spinnerAddCourseID

        getCourseID()
        courseIDSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, courseList)

        courseIDSpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                binding.buttonSubmit.text = courseIDSpinner.selectedItem.toString()
                Toast.makeText(this@AddCourseActivity, courseIDSpinner.selectedItem.toString(), Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                Toast.makeText(this@AddCourseActivity, "Not Working", Toast.LENGTH_SHORT).show()

            }
        }
        */


        binding.textinputAddStartTime.setOnClickListener {
            val calendarTime = Calendar.getInstance()
            val setTimeListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                calendarTime.set(Calendar.HOUR_OF_DAY, hour)
                calendarTime.set(Calendar.MINUTE, minute)

                var time = SimpleDateFormat("HH:mm").format(calendarTime.time).toString()
                binding.textinputAddStartTime.text =
                    Editable.Factory.getInstance().newEditable(time)
            }
            TimePickerDialog(
                this,
                TimePickerDialog.THEME_HOLO_LIGHT,
                setTimeListener,
                calendarTime.get(Calendar.HOUR_OF_DAY),
                calendarTime.get(Calendar.MINUTE),
                true
            ).show()
        }

        binding.textinputAddEndTime.setOnClickListener {
            val calendarTime = Calendar.getInstance()
            val setTimeListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                calendarTime.set(Calendar.HOUR_OF_DAY, hour)
                calendarTime.set(Calendar.MINUTE, minute)

                var time = SimpleDateFormat("HH:mm").format(calendarTime.time).toString()
                binding.textinputAddEndTime.text = Editable.Factory.getInstance().newEditable(time)
            }
            TimePickerDialog(
                this,
                TimePickerDialog.THEME_HOLO_LIGHT,
                setTimeListener,
                calendarTime.get(Calendar.HOUR_OF_DAY),
                calendarTime.get(Calendar.MINUTE),
                true
            ).show()
        }

    }

    /*
    private fun getCourseID() {
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()

        db.collection("courses").get()
            .addOnSuccessListener { courses ->
                for (course in courses) {
                    courseList.add(course.data.get("courseID").toString())
                }
            }
    }
    */

    fun addCourse(v: View?) {
        var auth = FirebaseAuth.getInstance()
        var db: FirebaseFirestore = FirebaseFirestore.getInstance()
        when {
            TextUtils.isEmpty(binding.textinputAddCourseID.text.toString().trim { it <= ' ' }) -> {
                Toast.makeText(
                    this@AddCourseActivity,
                    "Please Enter Course ID",
                    Toast.LENGTH_SHORT
                ).show()
            }

            TextUtils.isEmpty(
                binding.textinputAddCourseName.text.toString().trim { it <= ' ' }) -> {
                Toast.makeText(
                    this@AddCourseActivity,
                    "Please Enter Course Name",
                    Toast.LENGTH_SHORT
                ).show()
            }

            TextUtils.isEmpty(binding.textinputAddStartTime.text.toString().trim { it <= ' ' }) -> {
                Toast.makeText(
                    this@AddCourseActivity,
                    "Please Enter Start Time",
                    Toast.LENGTH_SHORT
                ).show()

            }

            TextUtils.isEmpty(binding.textinputAddEndTime.text.toString().trim { it <= ' ' }) -> {
                Toast.makeText(
                    this@AddCourseActivity,
                    "Please Enter End Time",
                    Toast.LENGTH_SHORT
                ).show()

            }

            TextUtils.isEmpty(binding.textinputAddClass.text.toString().trim { it <= ' ' }) -> {
                Toast.makeText(
                    this@AddCourseActivity,
                    "Please Enter Class/ Block",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else -> {

                val courseEntry = HashMap<String, Any>()
                courseEntry["courseID"] = binding.textinputAddCourseID.text.toString()
                courseEntry["courseName"] = binding.textinputAddCourseName.text.toString()
                courseEntry["courseStartTime"] = binding.textinputAddStartTime.text.toString()
                courseEntry["courseEndTime"] = binding.textinputAddEndTime.text.toString()
                courseEntry["courseClass"] = binding.textinputAddClass.text.toString()
                courseEntry["userID"] = FirebaseAuth.getInstance().currentUser?.uid.toString()

                db.collection("courses").add(courseEntry)
                    .addOnSuccessListener {
                        Toast.makeText(
                            this@AddCourseActivity,
                            "Course ${courseEntry.get("courseID")} successfully added.",
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()
                        startActivity(Intent(this, MainActivity::class.java))
                    }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser == null) {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}