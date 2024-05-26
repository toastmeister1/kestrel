package com.toastmeister1.kestrel.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.toastmeister1.kestrel.core.navigation.AppComposeNavigator
import com.toastmeister1.kestrel.core.navigation.KestrelScreens

@Composable
fun HomeScreen(
    navigator: AppComposeNavigator,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray),
        contentAlignment = Alignment.Center,
    ) {
        Button(onClick = { navigator.navigateUp(KestrelScreens.Animation.route) }) {
            Text(text = "goto animation Screen")
        }
    }
}
