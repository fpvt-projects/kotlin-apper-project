package ph.cadet.cabote.talan.attendance

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ph.cadet.cabote.talan.attendance.databinding.ActivityAddClassBinding
import ph.cadet.cabote.talan.attendance.databinding.ActivityAttendanceBinding
import ph.cadet.cabote.talan.attendance.model.Course

class AttendanceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAttendanceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAttendanceBinding.inflate((layoutInflater))
        setContentView(binding.root)

        binding.buttonScanQR.setOnClickListener{
            startActivity(Intent(this, QRScannerActivity::class.java))
        }

    }
}