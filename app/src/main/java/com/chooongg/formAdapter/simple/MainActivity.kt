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
        binding.viewPager.isUserInputEnabled = false
        binding.viewPager.adapter = FragmentAdapter(this, fragments)
        binding.appbarLayout.liftOnScrollTargetViewId = R.id.formView
    }

    private class FragmentAdapter(
        activity: FragmentActivity,
        private val fragments: List<Fragment>
    ) : FragmentStateAdapter(activity) {
        override fun getItemCount() = fragments.size
        override fun createFragment(position: Int) = fragments[position]
    }
}