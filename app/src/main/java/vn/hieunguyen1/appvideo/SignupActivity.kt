package vn.hieunguyen1.appvideo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class SignupActivity : AppCompatActivity() {

    private lateinit var edtName: EditText
    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnSigUp: Button
    lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        edtName = findViewById(R.id.su_edtName)
        edtEmail = findViewById(R.id.su_edtEmail)
        edtPassword = findViewById(R.id.su_edtPassword)
        btnSigUp = findViewById(R.id.su_btnSignup)

        mAuth = FirebaseAuth.getInstance()

        signUp()
    }

    private fun checkEmpty(): Boolean {
        return !(edtName.text.isNullOrEmpty() || edtEmail.text.isNullOrEmpty() || edtPassword.text.isNullOrEmpty())
    }

    fun signUp() {
        btnSigUp.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                if (checkEmpty()) {
                    mAuth.createUserWithEmailAndPassword(
                        edtEmail.text.toString(),
                        edtPassword.text.toString()
                    ).addOnCompleteListener { it
                        if (it.isSuccessful) {

                            Toast.makeText(
                                this@SignupActivity,
                                "Sign up success",
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            Toast.makeText(
                                this@SignupActivity,
                                "Sign up fail",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                } else {
                    Toast.makeText(
                        this@SignupActivity,
                        "Please fill in all the information to register for an account",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

        })
    }
}