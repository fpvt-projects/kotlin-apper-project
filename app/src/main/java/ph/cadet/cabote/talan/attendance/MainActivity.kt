package ph.cadet.cabote.talan.attendance

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val id = FirebaseAuth.getInstance().currentUser?.uid.toString()
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()

        db.collection("users").whereEqualTo("UserID", id).get()
            .addOnSuccessListener { users ->
                for (user in users) {
                    Toast.makeText(this, user.data.get("Name").toString(), Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {

            }

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