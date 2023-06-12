package com.chooongg.formAdapter.simple

import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.chooongg.formAdapter.simple.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        getAndroidScreenProperty()
    }

    private fun getAndroidScreenProperty() {
        val wm = this.getSystemService(WINDOW_SERVICE) as WindowManager
        val dm = DisplayMetrics()
        wm.defaultDisplay.getMetrics(dm)
        val width = dm.widthPixels // 屏幕宽度（像素）
        val height = dm.heightPixels // 屏幕高度（像素）
        val density = dm.density //屏幕密度（0.75 / 1.0 / 1.5）
        val densityDpi = dm.densityDpi //屏幕密度dpi（120 / 160 / 240）
        //屏幕宽度算法:屏幕宽度（像素）/屏幕密度
        val screenWidth = (width / density).toInt() //屏幕宽度(dp)
        val screenHeight = (height / density).toInt() //屏幕高度(dp)
        Log.e("123", "$screenWidth======$screenHeight")
    }
}