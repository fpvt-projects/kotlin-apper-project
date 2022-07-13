package ph.cadet.cabote.talan.attendance

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import ph.cadet.cabote.talan.attendance.adapter.CourseAdapter
import ph.cadet.cabote.talan.attendance.databinding.ActivityMainBinding
import ph.cadet.cabote.talan.attendance.model.Course
import java.text.DateFormat.getTimeInstance
import java.time.Clock
import java.time.LocalTime


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerViewAdapter: CourseAdapter
    private var courses = ArrayList<Course>()

    //Floating action button
    lateinit var addFAB: FloatingActionButton
    lateinit var homeFAB: FloatingActionButton
    lateinit var settingsFAB: FloatingActionButton
    var fabVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate((layoutInflater))
        setContentView(binding.root)

        init()
        recyclerViewAdapter = CourseAdapter(applicationContext, courses)
        binding.recyclerviewCourses.adapter = recyclerViewAdapter

        binding.recyclerviewCourses.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)

    }

    private fun addClassButton(){
        // initializing variables of floating
        // action button on below line.
        addFAB = findViewById(R.id.idFABAdd)
        homeFAB = findViewById(R.id.idFABHome)
        settingsFAB = findViewById(R.id.idFABSettings)

        // on below line we are initializing our
        // fab visibility boolean variable
        fabVisible = false

        // on below line we are adding on click listener
        // for our add floating action button
        addFAB.setOnClickListener {
            // on below line we are checking
            // fab visible variable.
            if (!fabVisible) {

                // if its false we are displaying home fab
                // and settings fab by changing their
                // visibility to visible.
                homeFAB.show()
                settingsFAB.show()

                // on below line we are setting
                // their visibility to visible.
                homeFAB.visibility = View.VISIBLE
                settingsFAB.visibility = View.VISIBLE

                // on below line we are checking image icon of add fab
                addFAB.setImageDrawable(resources.getDrawable(R.drawable.plus))

                // on below line we are changing
                // fab visible to true
                fabVisible = true
            } else {

                // if the condition is true then we
                // are hiding home ans settings fab
                homeFAB.hide()
                settingsFAB.hide()

                // on below line we are changing the
                // visibility of home and settings fab
                homeFAB.visibility = View.GONE
                settingsFAB.visibility = View.GONE

                // on below line we are changing image source for add fab
                addFAB.setImageDrawable(resources.getDrawable(R.drawable.plus))

                // on below line we are changing
                // fab visible to false.
                fabVisible = false
            }
        }

        // on below line we are adding
        // click listener for our home fab
        homeFAB.setOnClickListener {
            // on below line we are displaying a toast message.
            Toast.makeText(this@MainActivity, "Home clicked..", Toast.LENGTH_LONG).show()
        }

        // on below line we are adding on
        // click listener for settings fab
        settingsFAB.setOnClickListener {
            // on below line we are displaying a toast message
            Toast.makeText(this@MainActivity, "Settings clicked..", Toast.LENGTH_LONG).show()
        }
    }


    private fun init() {
        courses.add(Course("CourseID", "CourseName", "Course Start", "Course End", "CourseClass"))
        courses.add(Course("CourseID2", "CourseName2", "Course Start", "Course End", "CourseClass2"))

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