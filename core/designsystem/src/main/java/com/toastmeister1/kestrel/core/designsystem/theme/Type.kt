package com.toastmeister1.kestrel.core.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.toastmeister1.kestrel.core.designsystem.R

private val Pretendard = FontFamily(
    Font(R.font.pretendard_bold, FontWeight.Bold),
    Font(R.font.pretendard_semi_bold, FontWeight.SemiBold),
    Font(R.font.pretendard_medium, FontWeight.Medium),
    Font(R.font.pretendard_regular, FontWeight.Normal),
    Font(R.font.pretendard_light, FontWeight.Light)
)

internal val Typography = KestrelTypography(
    h1 = TextStyle(
        fontFamily = Pretendard,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        fontWeight = FontWeight.Bold
    ),
    h2 = TextStyle(
        fontFamily = Pretendard,
        fontSize = 18.sp,
        lineHeight = 24.sp,
        fontWeight = FontWeight.Bold
    ),
    subtitle1 = TextStyle(
        fontFamily = Pretendard,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        fontWeight = FontWeight.SemiBold
    ),
    subtitle2 = TextStyle(
        fontFamily = Pretendard,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        fontWeight = FontWeight.SemiBold
    ),
    body1 = TextStyle(
        fontFamily = Pretendard,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        fontWeight = FontWeight.Medium
    ),
    body2 = TextStyle(
        fontFamily = Pretendard,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        fontWeight = FontWeight.Normal
    ),
    caption1 = TextStyle(
        fontFamily = Pretendard,
        fontSize = 12.sp,
        lineHeight = 20.sp,
        fontWeight = FontWeight.Medium
    )
)

@Immutable
data class KestrelTypography(
    val h1: TextStyle,
    val h2: TextStyle,
    val subtitle1: TextStyle,
    val subtitle2: TextStyle,
    val body1: TextStyle,
    val body2: TextStyle,
    val caption1: TextStyle
)

val LocalTypography = staticCompositionLocalOf {
    KestrelTypography(
        h1 = TextStyle.Default,
        h2 = TextStyle.Default,
        subtitle1 = TextStyle.Default,
        subtitle2 = TextStyle.Default,
        body1 = TextStyle.Default,
        body2 = TextStyle.Default,
        caption1 = TextStyle.Default
    )
}