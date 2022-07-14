package ph.cadet.cabote.talan.attendance

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
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

class AddCourseActivity : AppCompatActivity(){
    private lateinit var binding : ActivityAddCourseBinding
    private var courseList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddCourseBinding.inflate((layoutInflater))
        setContentView(binding.root)

        getCourseID()
        var courseOptions = courseList.toArray()
        println(courseOptions)
        binding.spinnerAddCourseID.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, courseOptions)


        Toast.makeText(this, "Add Course Button is clicked", Toast.LENGTH_SHORT).show()

        binding.spinnerAddCourseID.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                Toast.makeText(this@AddCourseActivity, courseOptions[position].toString(), Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        binding.textinputAddStartTime.setOnClickListener{
            val calendarTime = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener{
                timePicker, hour, minute ->
                calendarTime.set(Calendar.HOUR_OF_DAY, hour)
                calendarTime.set(Calendar.MINUTE, minute)

                var time = SimpleDateFormat("HH:mm").format(calendarTime.time).toString()
                binding.textinputAddStartTime.text = Editable.Factory.getInstance().newEditable(time)
            }
            TimePickerDialog(this, TimePickerDialog.THEME_HOLO_LIGHT, timeSetListener, calendarTime.get(Calendar.HOUR_OF_DAY), calendarTime.get(Calendar.MINUTE), true).show()
        }

        binding.textinputAddEndTime.setOnClickListener{
            val calendarTime = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener{
                    timePicker, hour, minute ->
                calendarTime.set(Calendar.HOUR_OF_DAY, hour)
                calendarTime.set(Calendar.MINUTE, minute)

                var time = SimpleDateFormat("HH:mm").format(calendarTime.time).toString()
                binding.textinputAddEndTime.text = Editable.Factory.getInstance().newEditable(time)
            }
            TimePickerDialog(this, TimePickerDialog.THEME_HOLO_LIGHT , timeSetListener, calendarTime.get(Calendar.HOUR_OF_DAY), calendarTime.get(Calendar.MINUTE), true).show()
        }

    }

    private fun getCourseID(){
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()

        db.collection("courses").get()
            .addOnSuccessListener { courses ->
                for (course in courses) {
                    courseList.add(course.data.get("courseID").toString())
                }
            }
    }

    private fun setSpinnerItems(){
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