package vn.hieunguyen1.appvideo

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.EditText

class LoginActivity : AppCompatActivity() {

    lateinit var txtEmail: EditText
    lateinit var txtPassword: EditText
    lateinit var btnLogin: Button
    lateinit var btnSignup: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        txtEmail = findViewById(R.id.lg_edt_email)
        txtPassword = findViewById(R.id.lg_edt_password)
        btnLogin = findViewById(R.id.lg_btn_login)
        btnSignup = findViewById(R.id.lg_btn_signup)

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
    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        }
    }


}