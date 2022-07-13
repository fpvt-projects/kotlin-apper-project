package ph.cadet.cabote.talan.attendance

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import ph.cadet.cabote.talan.attendance.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth
    private lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        binding = ActivityLoginBinding.inflate((layoutInflater))
        setContentView(binding.root)

        val passwordInput = binding.password
        val emailInput = binding.email

        //INIT FIREBASE
        auth = FirebaseAuth.getInstance()

        binding.loginBtn.setOnClickListener{
            when {
                TextUtils.isEmpty(emailInput.text.toString().trim { it <= ' '}) -> {
                    Toast.makeText(
                        this@LoginActivity,
                        "Please Enter Email",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(passwordInput.text.toString().trim { it <= ' '}) -> {
                    Toast.makeText(
                        this@LoginActivity,
                        "Please Enter Password",
                        Toast.LENGTH_SHORT
                    ).show()
                } else -> {

                var emailValue =  emailInput.text.toString().trim()
                var passwordValue = passwordInput.text.toString().trim()

                auth.signInWithEmailAndPassword(emailValue, passwordValue)
                    .addOnSuccessListener {
                        //LOGIN SUCCESS
                        Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, MainActivity::class.java))
                    }
                    .addOnFailureListener{
                        //LOGIN FAILED
                        Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show()
                    }
            }
            }
        }

        binding.registerBtn.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        fun checkUser() {
            val firebaseUser = auth.currentUser
            if(firebaseUser != null) {
                //USER IS ALREADY LOGGED-IN
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }

        }

    }
}