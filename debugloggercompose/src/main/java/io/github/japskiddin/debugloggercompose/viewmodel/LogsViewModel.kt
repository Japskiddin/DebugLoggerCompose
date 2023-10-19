package io.github.japskiddin.debugloggercompose.viewmodel

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.japskiddin.debugloggercompose.R
import io.github.japskiddin.debugloggercompose.manager.LogManager
import io.github.japskiddin.debugloggercompose.model.Log
import kotlinx.coroutines.launch

class LogsViewModel : ViewModel() {
    private val logs: MutableLiveData<List<Log>> = MutableLiveData()
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

    fun getLogs(): LiveData<List<Log>> {
        return logs
    }

    fun clearLogs() {
        LogManager.getInstance().clear()
        logs.postValue(listOf())
    }

    fun copyLogs(context: Context) {
        val clipboardManager =
            context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Copied text", getAllItemsString())
        clipboardManager.setPrimaryClip(clip)
        Toast.makeText(context.applicationContext, R.string.text_copied, Toast.LENGTH_LONG).show()
    }

    private fun getAllItemsString(): String {
        val list = logs.value ?: listOf()
        val sb = StringBuilder()
        for (i in list.indices) {
            val log = list[i]
            sb.append(log.toString())
            if (i + 1 < list.size) {
                sb.append("\n")
            }
        }
        return sb.toString()
    }

    private fun postNewList() {
        if (!LogManager.getInstance().isEnabled()) return
        viewModelScope.launch {
            val list = LogManager.getInstance().getLogs().toList()
            logs.value = list
        }
    }

    companion object {
        private const val HANDLER_DELAY = 2000L
    }
}