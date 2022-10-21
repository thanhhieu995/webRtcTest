package vn.hieunguyen1.appvideo

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Patterns
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User


class SignupActivity : AppCompatActivity() {

    private lateinit var edtName: EditText
    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnSigUp: Button
    lateinit var imgEyePass: ImageView

    lateinit var database: FirebaseFirestore

    private lateinit var mAuth: FirebaseAuth
    var isPress: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        edtName = findViewById(R.id.su_edtName)
        edtEmail = findViewById(R.id.su_edtEmail)
        edtPassword = findViewById(R.id.su_edtPassword)
        btnSigUp = findViewById(R.id.su_btnSignup)
        imgEyePass = findViewById(R.id.su_eye_password)

        mAuth = FirebaseAuth.getInstance()
        database = FirebaseFirestore.getInstance()

        signUp()
//        countCharacter()
        clickEyePass()
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
        var user = User(edtEmail.text.toString(), edtPassword.text.toString())
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
            if (isValidEmail(edtEmail.text.toString()) && findCharacterEmail() && !checkUpperCaseEmail()) {
                mAuth.createUserWithEmailAndPassword(edtEmail.text.toString(), edtPassword.text.toString()).addOnCompleteListener { it
                    if (it.isSuccessful) {
                        addUserToData(user)
                        Toast.makeText(
                            this@SignupActivity,
                            "Sign up success",
                            Toast.LENGTH_LONG
                        ).show()
                        val intent = Intent(this@SignupActivity, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(
                            this@SignupActivity,
                            "Sign up fail " + it.exception!!.localizedMessage.toString(),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            } else {
                Toast.makeText(this@SignupActivity, "Your email is not valid, please try again", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun findCharacterEmail(): Boolean {
        val alphabet: String = "qwertyuiopasdfghjklzxcvbnm"
        for (i in edtEmail.text) {
            if (blockCharacter.contains(Char(edtEmail.text[0].toInt()))) {
                return false
            }
            if (i.toString() == "@") {
                break
            } else {
                if (alphabet.contains(i)) {
                    return true
                }
            }
        }
        return false
    }

    private fun checkUpperCaseEmail(): Boolean {
        val upperCase: String = "QWERTYUIOPASDFGHJKLZXCVBNM"
        for (i in edtEmail.text) {
            if (upperCase.contains(i)) {
                return true
            }
        }
        return false
    }


    private fun isValidEmail(email: String): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }


    private fun signUp() {
        btnSigUp.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                if (checkEmpty()) {
                    countCharacterAndCreateAccount()
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

    private fun clickEyePass() {
     imgEyePass.setOnClickListener(object : OnClickListener {
         override fun onClick(p0: View?) {
             if (isPress) {
                 imgEyePass.setImageResource(R.drawable.ic_baseline_visibility_24)
                 edtPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
             } else {
                 imgEyePass.setImageResource(R.drawable.ic_baseline_visibility_off_24)
                 edtPassword.transformationMethod = PasswordTransformationMethod.getInstance()
             }
             isPress = !isPress
            }
        })
    }

    fun addUserToData(user: vn.hieunguyen1.appvideo.User) {
        database.collection("users").document().set(user).addOnSuccessListener {
            Toast.makeText(this, "DocumentSnapshot successfully written!", Toast.LENGTH_LONG).show()
        }
            .addOnFailureListener {
                Toast.makeText(this, it.localizedMessage, Toast.LENGTH_LONG).show()
            }
    }
}