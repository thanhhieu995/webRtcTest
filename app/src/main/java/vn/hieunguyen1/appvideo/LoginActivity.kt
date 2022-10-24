package vn.hieunguyen1.appvideo

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    lateinit var txtEmail: EditText
    lateinit var txtPassword: EditText

    lateinit var txtForget: TextView

    lateinit var btnLogin: Button
    lateinit var btnSignup: Button
    lateinit var eyePassword: ImageView
    lateinit var mAuth: FirebaseAuth
    var isPress: Boolean = true
    lateinit var strEmail: String
    lateinit var strPassword: String

    lateinit var checkRememberPass: CheckBox
    lateinit var txtRmbPass: TextView
    var isCheckbox: Boolean = false

    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        txtEmail = findViewById(R.id.lg_edt_email)
        txtPassword = findViewById(R.id.lg_edt_password)
        btnLogin = findViewById(R.id.lg_btn_login)
        btnSignup = findViewById(R.id.lg_btn_signup)

        eyePassword = findViewById(R.id.lg_eye_password)
        checkRememberPass = findViewById(R.id.lg_rememberPass)
        txtRmbPass = findViewById(R.id.lg_txtRememberPass)
        txtForget = findViewById(R.id.lg_txtForgotPass)

        mAuth = FirebaseAuth.getInstance()

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        editor = sharedPreferences.edit()


        sharePreference()

        btnLogin.setOnClickListener(object : OnClickListener{
            override fun onClick(p0: View?) {
                val email = txtEmail.text.toString()
                val password = txtPassword.text.toString()

                if (!email.isNullOrEmpty() && !password.isNullOrEmpty()) {
                    checkRememberPass()

                    mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this@LoginActivity, OnCompleteListener {
                            if (it.isSuccessful) {
                                Toast.makeText(
                                    this@LoginActivity,
                                    "Log in successfully",
                                    Toast.LENGTH_LONG
                                ).show()
                                val intent = Intent(this@LoginActivity, DashboardActivity::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                Toast.makeText(
                                    this@LoginActivity,
                                    it.exception!!.localizedMessage,
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        })
                } else {
                    Toast.makeText(this@LoginActivity, "Enter your email and password", Toast.LENGTH_LONG).show()
                }
            }
        })

        btnSignup.setOnClickListener(object : OnClickListener {
            override fun onClick(v: View?) {
               val intent = Intent(this@LoginActivity, SignupActivity::class.java)
                startActivity(intent)
                finish()
            }

        })

        eyePassword.setOnClickListener(object : OnClickListener{
            override fun onClick(p0: View?) {
                if (isPress) {
                    eyePassword.setImageResource(R.drawable.ic_baseline_visibility_24)
                    txtPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
                } else {
                    eyePassword.setImageResource(R.drawable.ic_baseline_visibility_off_24)
                    txtPassword.transformationMethod = PasswordTransformationMethod.getInstance()
                }
                isPress = !isPress
            }
        })

        txtForget.setOnClickListener(object : OnClickListener{
            override fun onClick(p0: View?) {
                val intent = Intent(this@LoginActivity, UpdatePasswordActivity::class.java)
                startActivity(intent)
                finish()
            }
        })
    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        }
    }

    fun checkRememberPass() {
        if (checkRememberPass.isChecked) {
            editor.putBoolean("check_remember", true)
            strEmail = txtEmail.text.toString()
            editor.putString("email", strEmail)
            strPassword = txtPassword.text.toString()
            editor.putString("password", strPassword)
        } else {
            editor.putBoolean("check_remember", false)
            editor.putString("email", "")
            editor.putString("password", "")
        }
        editor.commit()
    }

    fun sharePreference() {
        isCheckbox = sharedPreferences.getBoolean("check_remember", false)

        strEmail = sharedPreferences.getString("email", "").toString()
        txtEmail.setText(strEmail)
        strPassword = sharedPreferences.getString("password", "").toString()
        txtPassword.setText(strPassword)

        checkRememberPass.isChecked = isCheckbox
    }
}