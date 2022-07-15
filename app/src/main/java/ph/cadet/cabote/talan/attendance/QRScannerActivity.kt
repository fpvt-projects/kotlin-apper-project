package ph.cadet.cabote.talan.attendance

import android.Manifest
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.budiyev.android.codescanner.*
import com.budiyev.android.codescanner.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import ph.cadet.cabote.talan.attendance.databinding.ActivityQrscannerBinding
import ph.cadet.cabote.talan.attendance.model.Classes
import ph.cadet.cabote.talan.attendance.model.Course
import ph.cadet.cabote.talan.attendance.model.Student
import java.util.HashMap

class QRScannerActivity : AppCompatActivity() {
    private lateinit var codeScanner: CodeScanner
    private val PERMISSION_REQUEST_CODE = 200

    private val id = FirebaseAuth.getInstance().currentUser?.uid.toString()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    private lateinit var binding: ActivityQrscannerBinding
    private lateinit var classObject: Classes
    private lateinit var course: Course

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQrscannerBinding.inflate((layoutInflater))
        setContentView(binding.root)

        classObject = intent.getParcelableExtra<Classes>("class")!!
        course = intent.getParcelableExtra<Course>("course")!!

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            val scannerView = binding.scannerView

            codeScanner = CodeScanner(this, scannerView)

            // Parameters (default values)
            codeScanner.camera = CodeScanner.CAMERA_BACK // or CAMERA_FRONT or specific camera id
            codeScanner.formats = CodeScanner.ALL_FORMATS // list of type BarcodeFormat,
            // ex. listOf(BarcodeFormat.QR_CODE)
            codeScanner.autoFocusMode = AutoFocusMode.SAFE // or CONTINUE
            codeScanner.scanMode = ScanMode.SINGLE // or CONTINUOUS or PREVIEW
            codeScanner.isAutoFocusEnabled = true // Whether to enable auto focus or not
            codeScanner.isFlashEnabled = false // Whether to enable flash or not

            // Callbacks
            codeScanner.decodeCallback = DecodeCallback {
                runOnUiThread {

                    val studentJSON = it.text.trimIndent()

                    val gson = Gson()
                    var student = gson.fromJson(studentJSON, Student.StudentInfo::class.java)

                    val studentAttendance = HashMap<String, Any>()
                    studentAttendance["firstName"] = student.firstName
                    studentAttendance["lastName"] = student.lastName
                    studentAttendance["studentEmail"] = student.studentEmail
                    studentAttendance["studentNumber"] = student.studentNumber
                    studentAttendance["courseID"] = course.courseID
                    studentAttendance["date"] = classObject.date
                    studentAttendance["userID"] = FirebaseAuth.getInstance().currentUser?.uid.toString()

                    db.collection("attendance").add(studentAttendance)
                        .addOnSuccessListener {
                            Toast.makeText(
                                this@QRScannerActivity,
                                "Successfully Logged Attendance.",
                                Toast.LENGTH_SHORT
                            ).show()
                            finish()
                            val intent = Intent(this, AttendanceActivity::class.java)
                            intent.putExtra("class", classObject)
                            intent.putExtra("course", course)
                            startActivity(intent)                        }
                }
            }
            codeScanner.errorCallback = ErrorCallback { // or ErrorCallback.SUPPRESS
                runOnUiThread {
                    Toast.makeText(
                        this, "Camera initialization error: ${it.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
            scannerView.setOnClickListener {
                codeScanner.startPreview()
            }

        } else {
            requestPermission()
        }
    }

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (permissions != null) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(applicationContext, "Permission Granted", Toast.LENGTH_SHORT).show()

                // main logic
            } else {
                Toast.makeText(applicationContext, "Permission Denied", Toast.LENGTH_SHORT).show()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED
                    ) {
                        showMessageOKCancel(
                            "You need to allow access permissions"
                        ) { dialog, which ->
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                requestPermission()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this, arrayOf(Manifest.permission.CAMERA),
            PERMISSION_REQUEST_CODE
        )
    }

    private fun showMessageOKCancel(message: String, okListener: DialogInterface.OnClickListener) {
        AlertDialog.Builder(this@QRScannerActivity)
            .setMessage(message)
            .setPositiveButton("OK", okListener)
            .setNegativeButton("Cancel", null)
            .create()
            .show()
    }
}