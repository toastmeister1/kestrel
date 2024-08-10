package com.toastmeister1.kestrel.feature.home

import android.content.Context
import android.graphics.PixelFormat
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.layout
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.AbstractComposeView
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.coerceIn
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.lifecycle.setViewTreeViewModelStoreOwner
import androidx.savedstate.findViewTreeSavedStateRegistryOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import com.toastmeister1.kestrel.core.designsystem.theme.KestrelTheme
import java.util.Objects
import kotlin.math.sqrt

@Suppress("DEPRECATION")
class FullScreenTestActivity : ComponentActivity() {

    private val accelSensorManager by lazy {
        AccelerationSensorManager(context = applicationContext) { Toast.makeText(applicationContext, "Shake event detected", Toast.LENGTH_SHORT).show() }
    }
    private val composeView by lazy { ComposeView(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(accelSensorManager)

        setContent {
            val scrollState = rememberScrollState()
            enableEdgeToEdge()

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
                window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
            } else {
                window.insetsController?.apply {
                    hide(WindowInsets.Type.statusBars())
                    systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                }
            }

            KestrelTheme {
                Box(modifier = Modifier.fillMaxSize()) {
                    val panelState = rememberPanelState(isOpen = false)
                    val view = LocalView.current

                    val myComposeView = remember {
                        MyComposeView(composeView = view)
                    }
                    DisposableEffect(myComposeView) {
                        onDispose { myComposeView.dispose() }
                    }

                    Box(modifier = Modifier.fillMaxSize()) {
                        Button(onClick = { }) {
                            Text(text = "BUtton1")
                        }
                    }
                    accelSensorManager.onDetected = {
                        println("AAA")
                        if (myComposeView.isShown.not()) {
                            myComposeView.show()
                        }
                    }
                    DebugViewPanel(
                        panelState
                    )
                }
            }
        }
    }


}

class AccelerationSensorManager(
    private val context: Context,
    var onDetected: () -> Unit
) : LifecycleEventObserver {

    // Declaring sensorManager
    // and acceleration constants
    private var sensorManager: SensorManager? = null
    private var acceleration = 0f
    private var currentAcceleration = 0f
    private var lastAcceleration = 0f

    private val sensorListener: SensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) {
            // Fetching x,y,z values
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]
            lastAcceleration = currentAcceleration

            // Getting current accelerations
            // with the help of fetched x,y,z values
            currentAcceleration = sqrt((x * x + y * y + z * z).toDouble()).toFloat()
            val delta: Float = currentAcceleration - lastAcceleration
            acceleration = acceleration * 0.9f + delta

            // Display a Toast message if
            // acceleration value is over 12
            if (acceleration > 12) {
                onDetected()
            }
        }

        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_CREATE -> {
                sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

                Objects.requireNonNull(sensorManager)!!
                    .registerListener(
                        sensorListener, sensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL
                    )
                acceleration = 10f
                currentAcceleration = SensorManager.GRAVITY_EARTH
                lastAcceleration = SensorManager.GRAVITY_EARTH
            }

            Lifecycle.Event.ON_RESUME -> {
                sensorManager?.registerListener(sensorListener, sensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL)
            }

            Lifecycle.Event.ON_PAUSE -> {
                sensorManager!!.unregisterListener(sensorListener)
            }

            else -> Unit
        }
    }

}

@Composable
fun rememberPanelState(isOpen: Boolean) = remember { PanelState(isOpen = isOpen) }

class PanelState(isOpen: Boolean) {

    var isOpen by mutableStateOf(isOpen)
        private set

    fun open() {
        isOpen = true
    }

    fun close() {
        isOpen = false
    }
}

@Composable
fun DebugViewPanel(
    panelState: PanelState
) {
    val density = LocalDensity.current
    var panelWidth by remember { mutableStateOf(0.dp) }

    var panelOffsetX by remember(panelState.isOpen) { mutableStateOf(
        if (panelState.isOpen) {
            -panelWidth
        } else {
            0.dp
        }
    ) }
    var panelOffsetXAnim = animateDpAsState(targetValue = panelOffsetX)

    BoxWithConstraints {
        val maxWidth = this.constraints.maxWidth
        var offsetY by remember { mutableStateOf(0.dp) }

        Box(
            modifier = Modifier
                .layout { measurable, constraints ->
                    val placeable = measurable.measure(constraints)

                    layout(width = constraints.maxWidth, height = constraints.maxHeight) {
                        placeable.placeRelative(x = maxWidth - placeable.width, y = 0)
                    }
                }
                .offset(y = offsetY)
                .wrapContentSize()
                .background(Color.Transparent),
        ) {
            var panelHeight by remember { mutableStateOf(0.dp) }
            Row(
                modifier = Modifier
                    .onSizeChanged { panelHeight = with(density) { it.height.toDp() } }
                    .draggable(
                        state = rememberDraggableState {
                            offsetY = (offsetY + with(density) { it.toDp() })
                                .coerceIn(
                                    minimumValue = 0.dp,
                                    maximumValue = this@BoxWithConstraints.maxHeight - panelHeight
                                )
                        },
                        orientation = Orientation.Vertical
                    )
                    .offset(x = panelOffsetXAnim.value),
                verticalAlignment = Alignment.CenterVertically
            ) {
                PanelHandle(
                    onClick = {
                        if (panelState.isOpen) {
                            panelOffsetX += panelWidth
                            panelState.close()
                        } else {
                            panelOffsetX -= panelWidth
                            panelState.open()
                        }
                    }
                )
                PanelBody(modifier = Modifier.onSizeChanged { panelWidth = with(density) { it.width.toDp() } })
            }
        }
    }
}

@Preview
@Composable
private fun PanelHandle(
    onClick: () -> Unit = { }
) {
    Box(
        modifier = Modifier
            .size(width = 24.dp, height = 56.dp)
            .clip(RoundedCornerShape(2.dp))
            .background(Color.Red)
            .clickable { onClick() }
    ) {

    }
}

@Preview
@Composable
private fun PanelBody(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(horizontal = 14.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(space = 12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(shape = CircleShape)
                .background(color = Color.DarkGray)
        )

        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(shape = CircleShape)
                .background(color = Color.DarkGray)
        )

        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(shape = CircleShape)
                .background(color = Color.DarkGray)
        )
    }
}

class MyComposeView(val composeView: View) : AbstractComposeView(composeView.context) {

    private val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    private var params: WindowManager.LayoutParams =
        WindowManager.LayoutParams().apply {
            gravity = Gravity.CENTER
            type = WindowManager.LayoutParams.TYPE_APPLICATION_PANEL
            token = composeView.applicationWindowToken
            width = WindowManager.LayoutParams.WRAP_CONTENT
            height = WindowManager.LayoutParams.WRAP_CONTENT
            format = PixelFormat.TRANSLUCENT
            flags = flags or WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
            x = 0
            y = 0
        }

    init {
        this.setViewTreeLifecycleOwner(lifecycleOwner = composeView.findViewTreeLifecycleOwner())
        this.setViewTreeViewModelStoreOwner(viewModelStoreOwner = composeView.findViewTreeViewModelStoreOwner())
        this.setViewTreeSavedStateRegistryOwner(composeView.findViewTreeSavedStateRegistryOwner())
    }

    private var text = mutableStateOf("안녕하세요")

    @Composable
    override fun Content() {
        val density = LocalDensity.current

        var width by remember { mutableStateOf(300.dp) }
        var height by remember { mutableStateOf(300.dp) }

        var offsetX by remember { mutableStateOf(0F) }
        var offsetY by remember { mutableStateOf(0F) }

        val transformable = rememberTransformableState { zoomChange: Float, panChange: Offset, rotationChange: Float ->
            with(density) {
                width += zoomChange.toDp()
                height += zoomChange.toDp()

                offsetX += panChange.x
                offsetY += panChange.y
            }
        }

        val localText by rememberUpdatedState(newValue = text)

        LaunchedEffect(
            key1 = offsetX,
            key2 = offsetY,
        ) {
            params.x = offsetX.toInt()
            params.y = offsetY.toInt()
            windowManager.updateViewLayout(this@MyComposeView, params)
        }

        Box(
            modifier = Modifier
                .size(width, height)
                .background(color = Color.White)
                .shadow(elevation = 10.dp)
                .transformable(transformable)
        ) {
            Column {
                Text(text = localText.value)
            }

            Box(modifier = Modifier
                .size(24.dp)
                .background(Color.DarkGray)
                .align(Alignment.TopEnd)
                .clickable { dispose() }
            )
        }
    }

    fun setText(new: String) {
        text.value = new
    }

    fun show() {
        windowManager.addView(this, params)
        params.flags = params.flags or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        windowManager.updateViewLayout(this, params)
    }

    fun dispose() {
        disposeComposition()
        windowManager.removeViewImmediate(this)
    }
}
