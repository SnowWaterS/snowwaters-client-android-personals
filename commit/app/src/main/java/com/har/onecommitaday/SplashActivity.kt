package com.har.onecommitaday

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.system.exitProcess


class SplashActivity : AppCompatActivity(), CoroutineScope {

    private val job = SupervisorJob()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)
        supportActionBar?.hide()

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE), 10)
            } else {
                startMainActivity()
            }
        } else {
            startMainActivity()
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
            val controller = window.insetsController
            controller?.apply {
                hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
                systemBarsBehavior =
                    WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineContext.cancelChildren()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (grantResults.isEmpty() || grantResults.contains(PackageManager.PERMISSION_DENIED)) {
                Log.i("permissionTest", "${grantResults.isEmpty()} or ${grantResults.contains(PackageManager.PERMISSION_DENIED)}")
                Toast.makeText(this, "파일 접근 권한이 없으면 앱을 실행 할 수 없습니다.", Toast.LENGTH_LONG).show()

                Handler(Looper.getMainLooper()).postDelayed({
                    finishAffinity()
                    exitProcess(2)
                }, 3000)

            } else {
                startMainActivity()
            }
        }
    }

    private fun startMainActivity() {
        launch {
            delay(1000)

            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}