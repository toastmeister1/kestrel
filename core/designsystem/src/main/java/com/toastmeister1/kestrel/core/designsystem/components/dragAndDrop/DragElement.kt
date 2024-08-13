package com.toastmeister1.kestrel.core.designsystem.components.dragAndDrop

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionOnScreen
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp


@Preview
@Composable
private fun DragElementPreview() {
    val density = LocalDensity.current

    var isDetect by remember { mutableStateOf(false) }

    var draggableItems by remember { mutableStateOf(listOf("apple")) }
    var draggedItems by remember { mutableStateOf(emptyList<String>()) }

    var containerBound by remember {
        mutableStateOf(
            Rect(offset = Offset(x = 0F, y = 0F), size = Size.Zero)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        DragContainer(
            modifier = Modifier.onGloballyPositioned {
                containerBound = it.boundsInWindow()
                println(containerBound.toString())
            },
            items = draggedItems,
            isDetect = isDetect
        )

        draggableItems.forEach {
            DragElement(
                title = it,
                onDrag = { offset ->
                    with(density) {
                        println(offset.toString())
                        isDetect = containerBound.contains(
                            offset = Offset(
                                x = offset.x.toPx(),
                                y = offset.y.toPx(),
                            )
                        )
                    }
                },
                onDragEnd = { offset ->
                    with(density) {
                        println(offset.toString())
                        isDetect = containerBound.contains(
                            offset = Offset(
                                x = offset.x.toPx(),
                                y = offset.y.toPx(),
                            )
                        )
                        if (isDetect) {
                            draggedItems += "banana"
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun DragElement(
    modifier: Modifier = Modifier,
    title: String,
    onDrag: (DpOffset) -> Unit,
    onDragEnd: (DpOffset) -> Unit
) {
    val density = LocalDensity.current
    var offset by remember { mutableStateOf(DpOffset(x = 0.dp, y = 0.dp)) }

    var positionOnScreen by remember { mutableStateOf(DpOffset(x = 0.dp, y = 0.dp)) }

    Box(
        modifier = modifier
            .offset(x = offset.x, y = offset.y)
            .pointerInput(key1 = Unit) {
                detectDragGestures(
                    onDragEnd = {
                        onDragEnd(positionOnScreen)
                    },
                    onDrag = { change, dragAmount ->
                        val x = dragAmount.x.toDp()
                        val y = dragAmount.y.toDp()
                        offset = DpOffset(x = offset.x + x, y = offset.y + y)
                        onDrag(positionOnScreen)
                    }
                )
            }
            .background(color = Color.Red)
            .padding(vertical = 12.dp, horizontal = 20.dp)
            .onGloballyPositioned {
                val position = it.positionOnScreen()

                positionOnScreen = with(density) {
                    DpOffset(
                        x = position.x.toDp(),
                        y = position.y.toDp()
                    )
                }
            }
    ) {
        Text(text = title)
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DragContainer(
    modifier: Modifier,
    isDetect: Boolean = false,
    items: List<String>
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                if (isDetect) {
                    Color.Magenta
                } else {
                    Color.Cyan
                }
            )
            .height(200.dp)
    ) {
        FlowRow(modifier = Modifier.animateContentSize()) {
            items.forEach {
                DragElement(
                    modifier = Modifier,
                    it,
                    onDrag = {},
                    onDragEnd = {}
                )
            }
        }
    }
}