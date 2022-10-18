package vn.hieunguyen1.appvideo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

class PlashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plash)

        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed(Runnable {
            LoginActivity.start(this)
        }, 500)
    }
}