package com.tldrandroid.sdkversion.ui

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.tldrandroid.sdkversion.model.HomeViewModel
import com.tldrandroid.sdkversion.ui.theme.SDKVersionTheme

@Composable
fun Home(
    activity: ComponentActivity,
    model: HomeViewModel
) {
    Home(
        onShowNotificationTap = { model.showNotification(activity) }
    )
}

@Composable
private fun Home(
    onShowNotificationTap: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Button(
            modifier = Modifier.align(Alignment.Center),
            onClick = onShowNotificationTap
        ) {
            Text(text = "Show Notification")
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    SDKVersionTheme {
        Home(onShowNotificationTap = {})
    }
}
