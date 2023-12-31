package io.github.japskiddin.debugloggercompose.sample

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import io.github.japskiddin.debugloggercompose.manager.LogManager
import io.github.japskiddin.debugloggercompose.ui.DebugLogger

class MainActivity : ComponentActivity() {
    // TODO: 29.05.2022 поучиться написанию тестов

    private val testMessageHandler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LogManager.getInstance().setEnabled(true)
        testMessageHandler.post(testMessageRunnable)
        setContent {
            DebugLogger()
        }
    }

    override fun onStart() {
        super.onStart()
        LogManager.getInstance().logDebug("Activity", "onStart")
    }

    override fun onPause() {
        LogManager.getInstance().logDebug("Activity", "onPause")
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        LogManager.getInstance().logDebug("Activity", "onResume")
    }

    override fun onDestroy() {
        testMessageHandler.removeCallbacks(testMessageRunnable)
        super.onDestroy()
    }

    private val testMessageRunnable: Runnable = object : Runnable {
        override fun run() {
            LogManager.getInstance().logInfo("Test", "New message")
            testMessageHandler.postDelayed(this, 5000)
        }
    }
}