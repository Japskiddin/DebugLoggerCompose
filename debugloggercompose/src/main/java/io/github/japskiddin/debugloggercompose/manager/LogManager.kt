package io.github.japskiddin.debugloggercompose.manager

import io.github.japskiddin.debugloggercompose.model.Level
import io.github.japskiddin.debugloggercompose.model.Log

class LogManager private constructor() {
    private var isEnabled = false
    private val logs: MutableList<Log>

    init {
        logs = ArrayList()
    }

    fun getLogs(): List<Log> {
        return logs
    }

    fun clear() {
        synchronized(logs) { logs.clear() }
    }

    fun logInfo(tag: String, event: String) {
        addToLog(Level.INFO, tag, event)
    }

    fun logError(tag: String, event: String) {
        addToLog(Level.ERROR, tag, event)
    }

    fun logDebug(tag: String, event: String) {
        addToLog(Level.DEBUG, tag, event)
    }

    fun logWarn(tag: String, event: String) {
        addToLog(Level.WARN, tag, event)
    }

    fun isEnabled(): Boolean {
        return isEnabled
    }

    fun setEnabled(enabled: Boolean) {
        isEnabled = enabled
    }

    private fun addToLog(type: Level, tag: String, event: String) {
        if (!isEnabled) return
        synchronized(logs) {
            val log = Log(type, tag, event)
            logs.add(log)
        }
    }

    companion object {
        @Volatile
        private lateinit var instance: LogManager

        @JvmStatic
        fun getInstance(): LogManager {
            synchronized(this) {
                if (!::instance.isInitialized) {
                    instance = LogManager()
                }
                return instance
            }
        }
    }
}