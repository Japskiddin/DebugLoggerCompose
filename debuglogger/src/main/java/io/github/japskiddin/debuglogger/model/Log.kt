package io.github.japskiddin.debuglogger.model

import java.text.SimpleDateFormat
import java.util.Locale

data class Log(private val type: Level, private val tag: String, private val text: String) :
    Comparable<Log> {
    private val time: Long = System.currentTimeMillis()

    override fun compareTo(other: Log): Int {
        return time.compareTo(other.time)
    }

    override fun toString(): String {
        val sdf = SimpleDateFormat("HH:mm:ss.SSS", Locale.getDefault())
        return "${sdf.format(time)} - $type : $tag / $text"
    }
}