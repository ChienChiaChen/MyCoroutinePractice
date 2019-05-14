package com.jason.mycoroutinepractice

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.jason.mycoroutinepractice.lesson.LaunchFragment
import kotlinx.coroutines.Job

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, MyListFragment(), LaunchFragment.TAG)
                .commitNow()
        }
    }

    override fun onBackPressed() {
        if (!supportFragmentManager.popBackStackImmediate()) {
            super.onBackPressed()
        }
    }
}
