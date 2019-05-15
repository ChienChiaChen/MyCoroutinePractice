package com.jason.mycoroutinepractice

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.ListFragment
import android.widget.ArrayAdapter
import com.jason.mycoroutinepractice.lesson.*

class MyListFragment : ListFragment() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val list = arrayListOf(
            LESSON_LAUNCH,
            LESSON_SEQUENTIALLY,
            LESSON_PARALLEL,
            LESSON_LIFECYCLE,
            LESSON_EXCEPTION,
            LESSON_EXCEPTION_HANDLER
        )

        listAdapter = ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, list)
        listView.setOnItemClickListener { _, _, position, _ ->
            when (list[position]) {
                LESSON_LAUNCH -> showFragment(LaunchFragment(), LaunchFragment.TAG)
                LESSON_SEQUENTIALLY -> showFragment(LaunchSequentiallyFragment(), LaunchSequentiallyFragment.TAG)
                LESSON_PARALLEL -> showFragment(LaunchParallelFragment(), LaunchParallelFragment.TAG)
                LESSON_LIFECYCLE -> showFragment(LifecycleAwareFragment(), LifecycleAwareFragment.TAG)
                LESSON_EXCEPTION -> showFragment(ExceptionFragment(), ExceptionFragment.TAG)
                LESSON_EXCEPTION_HANDLER -> showFragment(ExceptionHandlerFragment(), ExceptionHandlerFragment.TAG)
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
        private const val LESSON_LIFECYCLE = "4. Lifecycle Awareness (LifecycleObserver)"
        private const val LESSON_EXCEPTION = "5. Exception Handling (try/catch)"
        private const val LESSON_EXCEPTION_HANDLER = "6. Exception Handling (handler)"

    }
}