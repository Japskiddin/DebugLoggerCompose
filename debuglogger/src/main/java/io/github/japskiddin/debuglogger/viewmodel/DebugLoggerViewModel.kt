package io.github.japskiddin.debuglogger.viewmodel

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.japskiddin.debuglogger.manager.LogManager
import io.github.japskiddin.debuglogger.model.LogEvent

class DebugLoggerViewModel : ViewModel() {
    private val logsLiveData: MutableLiveData<List<LogEvent>> = MutableLiveData()
    private val logHandler = Handler(Looper.getMainLooper())
    private val debugLogRunnable = object : Runnable {
        override fun run() {
            postNewList()
            logHandler.postDelayed(this, HANDLER_DELAY)
        }
    }

    init {
        postNewList()
    }

    fun resume() {
        logHandler.post(debugLogRunnable)
    }

    fun pause() {
        logHandler.removeCallbacks(debugLogRunnable)
    }

    fun getLogs(): List<LogEvent> {
        return logsLiveData.value ?: ArrayList()
    }

    private fun postNewList() {
        if (!LogManager.getInstance().isEnabled()) return
        val list = ArrayList(LogManager.getInstance().getLogs())
        logsLiveData.postValue(list)
    }

    companion object {
        private const val HANDLER_DELAY = 2000L
    }
}