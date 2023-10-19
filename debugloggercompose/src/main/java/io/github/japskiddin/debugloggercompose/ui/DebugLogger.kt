package io.github.japskiddin.debugloggercompose.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.japskiddin.debugloggercompose.R
import io.github.japskiddin.debugloggercompose.model.Level
import io.github.japskiddin.debugloggercompose.model.Log
import io.github.japskiddin.debugloggercompose.viewmodel.LogsViewModel
import kotlinx.coroutines.launch

@Preview
@Composable
fun DebugLogger(
    viewModel: LogsViewModel = viewModel(),
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier.background(colorResource(id = R.color.debug_logger_background))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp)
        ) {
            MenuButton(
                onClick = { viewModel.clearLogs() },
                text = stringResource(id = R.string.clear_log),
                modifier = Modifier.weight(1f)
            )
            MenuButton(
                onClick = { viewModel.copyLogs(context) },
                text = stringResource(id = R.string.copy_log),
                modifier = Modifier.weight(1f)
            )
        }
        LogList(viewModel)
    }
}

@Composable
fun LogList(viewModel: LogsViewModel) {
    val lifecycleEvent = rememberLifecycleEvent()
    LaunchedEffect(lifecycleEvent) {
        if (lifecycleEvent == Lifecycle.Event.ON_RESUME) {
            viewModel.resume()
        } else if (lifecycleEvent == Lifecycle.Event.ON_PAUSE) {
            viewModel.pause()
        }
    }
    val logs = viewModel.getLogs().observeAsState()
    val list = logs.value ?: listOf()
    val lazyColumnListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = lazyColumnListState
    ) {
        coroutineScope.launch {
            lazyColumnListState.scrollToItem(list.size)
        }
        items(list) {
            ListItem(log = it)
        }
    }
}

@Preview
@Composable
fun ListItem(
    log: Log = Log(Level.INFO, "TAG", "Test")
) {
    Text(
        text = log.toString(),
        fontSize = 12.sp,
        color = colorResource(id = R.color.debug_log_text),
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp)
    )
}

@Composable
fun MenuButton(onClick: () -> Unit, text: String, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(20),
        colors = ButtonDefaults.buttonColors(colorResource(id = R.color.debug_button_background)),
        modifier = modifier.padding(2.dp)
    ) {
        Text(
            text = text,
            color = colorResource(id = R.color.debug_button_text),
            fontSize = 12.sp
        )
    }
}

@Composable
fun rememberLifecycleEvent(lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current): Lifecycle.Event {
    var state by remember { mutableStateOf(Lifecycle.Event.ON_ANY) }
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            state = event
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
    return state
}