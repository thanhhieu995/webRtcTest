package vn.hieunguyen1.appvideo

import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth


class SignupActivity : AppCompatActivity() {

    private lateinit var edtName: EditText
    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnSigUp: Button
    lateinit var imgEyePass: ImageView

    lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        edtName = findViewById(R.id.su_edtName)
        edtEmail = findViewById(R.id.su_edtEmail)
        edtPassword = findViewById(R.id.su_edtPassword)
        btnSigUp = findViewById(R.id.su_btnSignup)
        imgEyePass = findViewById(R.id.su_eye_password)

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
            if (isValidEmail(edtEmail.text.toString()) && findCharacterEmail() && !checkUpperCaseEmail()) {
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
        } else {
//            Toast.makeText(this@SignupActivity, "nhap so roi", Toast.LENGTH_LONG).show()
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




    fun findNumberOnEmail() : Boolean {
        var number: Int = 0
        for (i in edtEmail.text) {

        }
        return false
    }

    fun isAplhabetical(input: Any): Boolean {
        when (input) {
            // if the input is a String, check all the Chars of it
            is String -> return input.all { it.isLetter() }
            // if input is a Char, just check that single Char
            is Char -> return input.isLetter()
            // otherwise, input doesn't contain any Char
            else -> return false
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

//    public void ShowHidePass(View view){
//
//        if(view.getId()==R.id.show_pass_btn){
//
//            if(edit_password.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
//                ((ImageView(view)).setImageResource(R.drawable.hide_password);
//
//                //Show Password
//                edit_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
//            }
//            else{
//                ((ImageView)(view)).setImageResource(R.drawable.show_password);
//
//                //Hide Password
//                edit_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
//
//            }
//        }
//    }
    
}