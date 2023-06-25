package com.chooongg.formAdapter.simple

import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.chooongg.formAdapter.simple.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val fragments = listOf(
        BasicFragment()
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        ViewCompat.setOnApplyWindowInsetsListener(binding.viewPager) { v, insets ->
            val inset =
                insets.getInsets(WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.displayCutout())
            if (binding.navigationView is BottomNavigationView) {
                v.setPadding(inset.left, 0, inset.right, 0)
            } else {
                val isRtl = ViewCompat.getLayoutDirection(v) == ViewCompat.LAYOUT_DIRECTION_RTL
                if (isRtl) {
                    v.setPadding(inset.left, 0, 0, inset.bottom)
                } else {
                    v.setPadding(0, 0, inset.right, inset.bottom)
                }
            }
            insets
        }
        getAndroidScreenProperty()
        binding.viewPager.adapter = FragmentAdapter(this, fragments)
        binding.appbarLayout.liftOnScrollTargetViewId = R.id.formView
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

    private class FragmentAdapter(
        activity: FragmentActivity,
        private val fragments: List<Fragment>
    ) : FragmentStateAdapter(activity) {
        override fun getItemCount() = fragments.size
        override fun createFragment(position: Int) = fragments[position]
    }
}