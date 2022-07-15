package ph.cadet.cabote.talan.attendance

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import ph.cadet.cabote.talan.attendance.databinding.ActivityAddClassBinding
import ph.cadet.cabote.talan.attendance.databinding.ActivityAddCourseBinding
import ph.cadet.cabote.talan.attendance.model.Course
import java.text.SimpleDateFormat
import java.util.*

class AddClassActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddClassBinding
    private lateinit var course: Course

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddClassBinding.inflate((layoutInflater))
        setContentView(binding.root)

        course = intent.getParcelableExtra<Course>("course")!!

        binding.textinputAddDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val setDateListener =
                DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
                    calendar.set(Calendar.YEAR, year)
                    calendar.set(Calendar.MONTH, month)
                    calendar.set(Calendar.DAY_OF_MONTH, day)

                    var date = SimpleDateFormat("EEE, MMM d, ''yy").format(calendar.time).toString()
                    binding.textinputAddDate.text = Editable.Factory.getInstance().newEditable(date)

                }

            DatePickerDialog(
                this,
                DatePickerDialog.THEME_HOLO_LIGHT,
                setDateListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show()
        }
    }

    fun addClass(v: View?) {
        var auth = FirebaseAuth.getInstance()
        var db: FirebaseFirestore = FirebaseFirestore.getInstance()
        when {
            TextUtils.isEmpty(binding.textinputAddDate.text.toString().trim { it <= ' ' }) -> {
                Toast.makeText(
                    this@AddClassActivity,
                    "Please Select Date",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else -> {

                val classEntry = HashMap<String, Any>()
                classEntry["classDate"] = binding.textinputAddDate.text.toString()
                classEntry["classBlock"] = course.courseClass
                classEntry["courseID"] = course.courseID
                classEntry["userID"] = FirebaseAuth.getInstance().currentUser?.uid.toString()

                db.collection("classes").add(classEntry)
                    .addOnSuccessListener {
                        Toast.makeText(
                            this@AddClassActivity,
                            "Class successfully added.",
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()
                        val intent = Intent(this, CourseActivity::class.java)
                        intent.putExtra("course", course)
                        startActivity(intent)
                    }
            }
        }
    }
}