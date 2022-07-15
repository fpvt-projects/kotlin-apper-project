package ph.cadet.cabote.talan.attendance

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import ph.cadet.cabote.talan.attendance.databinding.ActivityChangePasswordBinding
import ph.cadet.cabote.talan.attendance.databinding.ActivityLoginBinding

class ChangePassword : AppCompatActivity() {

    private lateinit var binding: ActivityChangePasswordBinding

    private lateinit var auth: FirebaseAuth
    var currentPassword = binding.oldPasswordInput.toString()
    var newPassword = binding.newPasswordInput.toString().trim()
    var confirmNewPassword = binding.confirmNewPasswordInput.toString().trim()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)
        binding = ActivityChangePasswordBinding.inflate((layoutInflater))
        setContentView(binding.root)

        binding.changePasswordButton.setOnClickListener {
            changePassword()
        }
    }

    //FUNCTION-FOR-CHANGE-PASSWORD
    private fun changePassword() {
        Firebase.auth.sendPasswordResetEmail(currentPassword)
    }
}