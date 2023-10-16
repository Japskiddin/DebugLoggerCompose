package io.github.japskiddin.debuglogger.viewmodel

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.japskiddin.debuglogger.manager.LogManager
import io.github.japskiddin.debuglogger.model.LogEvent
import kotlinx.coroutines.launch

class LogsViewModel : ViewModel() {
    private val logsLiveData: MutableLiveData<List<LogEvent>> = MutableLiveData()
    private val logHandler = Handler(Looper.getMainLooper())
    private val logRunnable = object : Runnable {
        override fun run() {
            postNewList()
            logHandler.postDelayed(this, HANDLER_DELAY)
        }
    }

    init {
        postNewList()
    }

    fun resume() {
        logHandler.post(logRunnable)
    }

    fun pause() {
        logHandler.removeCallbacks(logRunnable)
    }

    fun getLogs(): LiveData<List<LogEvent>> {
        return logsLiveData
    }

    private fun postNewList() {
        if (!LogManager.getInstance().isEnabled()) return
        viewModelScope.launch {
            val logs = LogManager.getInstance().getLogs().toList()
            logsLiveData.value = logs
        }
    }

    companion object {
        private const val HANDLER_DELAY = 2000L
    }
}