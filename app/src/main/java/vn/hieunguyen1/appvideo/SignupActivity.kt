package vn.hieunguyen1.appvideo

import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
//        countCharacter()
    }

    private fun checkEmpty(): Boolean {
        return !(edtName.text.isNullOrEmpty() || edtEmail.text.isNullOrEmpty() || edtPassword.text.isNullOrEmpty())
    }

    fun checkString(): Boolean {
        return edtName.text.contains("[a-zA-Z]+")
    }

    private val blockCharacter: String = "!@#$%^&*()_=+?/:;{}1234567890"

    fun countCharacterAndCreateAccount() {
        var hasMore: Boolean = false
        for (i in edtName.text) {
            if (blockCharacter.contains(i)) {
                Toast.makeText(
                    this@SignupActivity,
                    "Your name is not valid, your name don't contain number and specific character",
                    Toast.LENGTH_LONG
                ).show()
                hasMore = true
                break
            }
        }

        if (!hasMore) {
            if (isValidEmail(edtEmail.text.toString())) {
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
                            "Sign up fail " + it.exception.toString(),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            } else {
                Toast.makeText(this@SignupActivity, "Your email is not valid, please try again", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun findCharacterEmail() {
        val hasMore: Boolean = false
        for (i in edtEmail.text) {
            if (i.toString() == "@") {
                break
            } else {

            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun checkLength(): Boolean {
        return edtName.text.length > 2
    }

    fun checkSpecificCharacter(): Boolean {
        return !edtName.text.contains("[!@#$%^&*()_+=<>,.?/|]")
    }


    private fun signUp() {
        btnSigUp.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                if (checkEmpty()) {
                    countCharacterAndCreateAccount()
//                    mAuth.createUserWithEmailAndPassword(
//                        edtEmail.text.toString(),
//                        edtPassword.text.toString()
//                    ).addOnCompleteListener {
//                        it
//                        if (it.isSuccessful) {
//
//                            Toast.makeText(
//                                this@SignupActivity,
//                                "Sign up success",
//                                Toast.LENGTH_LONG
//                            ).show()
//                        } else {
//                            Toast.makeText(
//                                this@SignupActivity,
//                                "Sign up fail",
//                                Toast.LENGTH_LONG
//                            ).show()
//                        }
//                    }
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