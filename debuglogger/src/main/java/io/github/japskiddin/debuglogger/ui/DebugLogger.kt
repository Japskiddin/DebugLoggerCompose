package io.github.japskiddin.debuglogger.ui

import android.content.Context
import android.util.AttributeSet
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.AbstractComposeView

class DebugLogger @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : AbstractComposeView(context, attrs, defStyle) {
//    constructor(context: Context) : this(context, null, 0)
//
//    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
//
//    constructor(context: Context, attrs: AttributeSet?, defaultStyleAttr: Int) : super(
//        context,
//        attrs,
//        defaultStyleAttr
//    ) {
//        (context as LifecycleOwner).lifecycle.addObserver(this)
//    }

//    private val debugLogRunnable = object : Runnable {
//        override fun run() {
//            val logs: List<LogEvent> = ArrayList(LogManager.getInstance().getLogs())
//            if (LogManager.getInstance().isEnabled() && logs.isNotEmpty()) {
//                for (log in logs) {
//                    logAdapter.addItem(log)
//                }
//                binding.rvLogs.scrollToPosition(logAdapter.itemCount - 1)
//                LogManager.getInstance().clear()
//            }
//            logHandler.postDelayed(this, HANDLER_DELAY)
//        }
//    }
//
//    private fun onLogsClear() {
//        LogManager.getInstance().clear()
//        logAdapter.clear()
//    }
//
//    private fun onCopyLogs() {
//        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
//        val clip = ClipData.newPlainText("Copied text", logAdapter.allText)
//        clipboard.setPrimaryClip(clip)
//        Toast.makeText(context, "Text copied", Toast.LENGTH_LONG).show()
//    }

    @Composable
    override fun Content() {
        DebugLogger()
    }
}