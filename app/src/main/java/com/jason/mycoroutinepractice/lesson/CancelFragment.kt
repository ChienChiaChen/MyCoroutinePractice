package com.jason.mycoroutinepractice.lesson

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jason.mycoroutinepractice.R
import kotlinx.android.synthetic.main.fragment_cancel.*
import kotlinx.coroutines.*
import java.util.*
import java.util.concurrent.TimeUnit

class CancelFragment : Fragment() {
    private val uiDispatcher:CoroutineDispatcher = Dispatchers.Main
    private val dataProvider = DataProvider()
    private lateinit var job:Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = Job()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_cancel, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button.setOnClickListener {
            // if you want to know how coroutine was completed, attach invokeOnCompletion
            loadData().invokeOnCompletion {
                if (it is CancellationException) { // if coroutine was cancelled
                    textView.text = "Cancelled"
                    hideLoading()
                }
            }
        }
        buttonCancel.setOnClickListener {
            // cancelling parent job will cancel all attached child coroutines
            job.cancel()
            // we need to create new Job, because current is cancelled
            // new coroutine will not start, if parent Job is already cancelled
            job = Job()
        }
    }

    private fun loadData() = GlobalScope.launch(uiDispatcher + job) {
        showLoading()

        val result = dataProvider.loadData()

        showText(result)
        hideLoading()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        job.cancel()
    }

    private fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        progressBar.visibility = View.GONE
    }

    private fun showText(data: String) {
        textView.text = data
    }

    companion object {
        const val TAG = "LaunchFragment"
    }

    class DataProvider(private val dispatcher: CoroutineDispatcher = Dispatchers.IO) {

        suspend fun loadData(): String = withContext(dispatcher) {
            delay(TimeUnit.SECONDS.toMillis(2)) // imitate long running operation
            "Data is available: ${Random().nextInt()}"
        }
    }
}