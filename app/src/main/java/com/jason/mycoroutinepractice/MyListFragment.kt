package com.jason.mycoroutinepractice

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.ListFragment
import android.widget.ArrayAdapter
import com.jason.mycoroutinepractice.lesson.LaunchFragment
import com.jason.mycoroutinepractice.lesson.LaunchParallelFragment
import com.jason.mycoroutinepractice.lesson.LaunchSequentiallyFragment

class MyListFragment : ListFragment() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val list = arrayListOf(
            LESSON_LAUNCH,
            LESSON_SEQUENTIALLY,
            LESSON_PARALLEL)

        listAdapter = ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, list)
        listView.setOnItemClickListener { _, _, position, _ ->
            when (list[position]) {
                LESSON_LAUNCH -> showFragment(LaunchFragment(), LaunchFragment.TAG)
                LESSON_SEQUENTIALLY -> showFragment(LaunchSequentiallyFragment(), LaunchSequentiallyFragment.TAG)
                LESSON_PARALLEL -> showFragment(LaunchParallelFragment(), LaunchParallelFragment.TAG)

            }
        }
    }

    private fun showFragment(fragment: Fragment, tag: String) {
        activity?.supportFragmentManager?.beginTransaction()
            ?.add(R.id.fragmentContainer, fragment, tag)
            ?.addToBackStack(tag)
            ?.commit()
    }

    companion object {
        const val TAG = "SampleListFragment"
        private const val LESSON_LAUNCH = "1. Launch Coroutine"
        private const val LESSON_SEQUENTIALLY = "2. Launch Coroutine Sequentially"
        private const val LESSON_PARALLEL = "3. Launch Coroutine Parallel"
    }
}