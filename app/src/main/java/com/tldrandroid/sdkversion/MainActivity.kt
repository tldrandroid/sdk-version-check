package com.tldrandroid.sdkversion

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.tldrandroid.sdkversion.di.BuildVersionModule
import com.tldrandroid.sdkversion.ext.requiresNotificationPermissions
import com.tldrandroid.sdkversion.model.HomeViewModel
import com.tldrandroid.sdkversion.ui.Home
import com.tldrandroid.sdkversion.ui.theme.SDKVersionTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    @JvmField
    @Named(BuildVersionModule.SDK_INT)
    var sdkInt: Int = 0

    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

    @SuppressLint("InlinedApi") // Accounted for via DI
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (sdkInt.requiresNotificationPermissions()) {
            requestPermissionLauncher =
                registerForActivityResult(ActivityResultContracts.RequestPermission()) { outcome ->
                    if (!outcome) showDirectionsToast()
                }

            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }

        setContent {
            SDKVersionTheme {
                val model: HomeViewModel by viewModels()

                Home(
                    activity = this,
                    model = model
                )
            }
        }
    }

    private fun showDirectionsToast() {
        Toast.makeText(
            this,
            "Please accept the notification permission",
            Toast.LENGTH_LONG
        ).show()
    }
}
