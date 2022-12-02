package com.tldrandroid.sdkversion.ext

import android.os.Build

fun Int.requiresNotificationPermissions() = this >= Build.VERSION_CODES.TIRAMISU
fun Int.supportsMutablePendingIntents() = this >= Build.VERSION_CODES.M
fun Int.supportsNotificationChannels() = this >= Build.VERSION_CODES.O
