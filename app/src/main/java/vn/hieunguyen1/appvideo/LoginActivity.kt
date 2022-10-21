package vn.hieunguyen1.appvideo

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView

class LoginActivity : AppCompatActivity() {

    lateinit var txtEmail: EditText
    lateinit var txtPassword: EditText
    lateinit var btnLogin: Button
    lateinit var btnSignup: Button
    lateinit var eyePassword: ImageView
    var isPress: Boolean = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        txtEmail = findViewById(R.id.lg_edt_email)
        txtPassword = findViewById(R.id.lg_edt_password)
        btnLogin = findViewById(R.id.lg_btn_login)
        btnSignup = findViewById(R.id.lg_btn_signup)

        eyePassword = findViewById(R.id.lg_eye_password)

//        btnSignup.setOnClickListener { OnClickListener {
//            val intent = Intent(this@LoginActivity, SignupActivity::class.java)
//            startActivity(intent)
//            finish()
//        } }

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
    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        }
    }


}