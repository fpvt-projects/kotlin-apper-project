package ph.cadet.cabote.talan.attendance

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import ph.cadet.cabote.talan.attendance.databinding.ActivityRegisterBinding


class RegisterActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth
    private lateinit var binding : ActivityRegisterBinding
    private var db:FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        binding = ActivityRegisterBinding.inflate((layoutInflater))
        setContentView(binding.root)

        val registerEmailInput = binding.registerEmailInput
        val registerPasswordInput = binding.registerPassword
        val registerPasswordConfirmation = binding.registerPasswordConfirmation
        val registerButton = binding.registerBtn
        val registerName = binding.registerNameInput

        auth = FirebaseAuth.getInstance()

        registerButton.setOnClickListener{
            when {
                TextUtils.isEmpty(registerEmailInput.text.toString().trim { it <= ' '}) -> {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Please Enter Email",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(registerPasswordInput.text.toString().trim { it <= ' '}) -> {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Please Enter Password",
                        Toast.LENGTH_SHORT
                    ).show()

                }

                TextUtils.isEmpty(registerPasswordConfirmation.text.toString().trim { it <= ' '}) -> {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Please Enter Password",
                        Toast.LENGTH_SHORT
                    ).show()

                } else -> {

                var registerEmailValue = registerEmailInput.text.toString().trim()
                var registerPasswordValue = registerPasswordInput.text.toString().trim()
                var registerNameValue = registerName.text.toString()

                auth.createUserWithEmailAndPassword(registerEmailValue, registerPasswordValue)
                    .addOnSuccessListener {

                        val add = HashMap<String, Any>()

                        add["Name"] = registerNameValue
                        add["Email"] = registerPasswordValue
                        add["UserID"] = FirebaseAuth.getInstance().currentUser?.uid.toString()

                        db.collection("users").add(add)
                            .addOnSuccessListener {

                            }
                            .addOnFailureListener {

                            }

                        //TODO DOCUMENT-ADD-FIRE-STORE
                        Toast.makeText(this, "Register Success", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, LoginActivity::class.java ))
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                    }
            }
            }
        }

    }
}