package io.github.japskiddin.debuglogger.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.japskiddin.debuglogger.model.Level
import io.github.japskiddin.debuglogger.model.LogEvent
import io.github.japskiddin.debuglogger.viewmodel.DebugLoggerViewModel

/*
 * https://stackoverflow.com/questions/73284058/best-practise-of-using-view-model-in-jetpack-compose
 * https://www.fandroid.info/mobilnoe-prilojenie-kotlin-jetpack-compose-ui/
 * https://developer.android.com/codelabs/basic-android-kotlin-compose-viewmodel-and-state#2
 * https://developer.android.com/topic/libraries/architecture/viewmodel#jetpack-compose_1
 * https://medium.com/mobile-app-development-publication/jetpack-compose-custom-layout-made-easy-b5743f8cc82c
 * https://stackoverflow.com/questions/74392142/jetpack-compose-how-to-trigger-an-event-when-a-screen-is-composed
 * https://medium.com/@yhdista/change-the-brightness-for-the-screen-thinking-in-compose-84378dce3b6c
 * https://semicolonspace.com/jetpack-compose-lazycolumn/
 * https://medium.com/@rahulchaurasia3592/custom-compose-view-part-1-fd53e2b7ffbd
 * https://medium.com/dscvitpune/getting-started-with-jetpack-compose-e799f262ce50
 * https://androidwave.com/ultimate-guide-to-jetpack-compose-list/
 * https://stackoverflow.com/questions/52566018/reacting-to-activity-lifecycle-in-viewmodel
 * https://developer.android.com/jetpack/compose/migrate/interoperability-apis/compose-in-views
 */

@Composable
fun DebugLogger(
    vm: DebugLoggerViewModel = viewModel()
) {
    LogList(logs = vm.getLogs())
}

@Composable
fun LogList(logs: List<LogEvent>) {
    LazyColumn {
        items(logs) { log ->
            ListItem(log)
        }
    }
}

@Preview
@Composable
fun ListItem(
    logEvent: LogEvent = LogEvent(Level.INFO, "TAG", "Test")
) {
    Text(
        text = logEvent.toString(),
        fontSize = 12.sp,
        modifier = Modifier.padding(2.dp)
    )
}