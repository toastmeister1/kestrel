package com.toastmeister1.kestrel.core.ui.options

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.toastmeister1.kestrel.core.designsystem.theme.Gray600
import com.toastmeister1.kestrel.core.designsystem.theme.KestrelTheme

@Composable
fun OptionItem(
    modifier: Modifier = Modifier,
    label: String,
    contents: @Composable () -> Unit,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier.width(IntrinsicSize.Min),
            text = label,
            style = KestrelTheme.typography.h2,
            textAlign = TextAlign.Right,
        )

        Spacer(modifier = Modifier.width(24.dp))

        Surface(
            border = BorderStroke(width = 1.dp, color = Gray600),
            shape = RoundedCornerShape(6.dp),
        ) {
            Row(
                modifier = Modifier.height(32.dp),
            ) {
                contents()
            }
        }
    }
}
