package com.jason.mycoroutinepractice.lesson

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jason.mycoroutinepractice.R
import kotlinx.android.synthetic.main.fragment_button.*
import kotlinx.coroutines.*
import java.util.*
import java.util.concurrent.TimeUnit

class ExceptionFragment : Fragment() {
    private val uiDispatcher: CoroutineDispatcher = Dispatchers.Main
    private val dataProvider = DataProvider()
    private lateinit var job: Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = Job()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_button, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        button.setOnClickListener { loadData() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        job.cancel()
    }

    private fun loadData() = GlobalScope.launch(uiDispatcher + job) {
        showLoading()

        try {
            val result = dataProvider.loadData()
            showText(result)
        } catch (e: IllegalArgumentException) {
            showText(e.message ?: "")
        }

        hideLoading()
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
        const val TAG = "ExceptionFragment"
    }

    class DataProvider(private val dispatcher: CoroutineDispatcher = Dispatchers.IO) {
        suspend fun loadData(): String = withContext(dispatcher) {
            delay(TimeUnit.SECONDS.toMillis(2)) // imitate long running operation
            mayThrowException()
            "Data is available: ${Random().nextInt()}"
        }

        private fun mayThrowException() {
            if (Random().nextBoolean()) {
                throw IllegalArgumentException("Ooops exception occurred")
            }
        }
    }
}