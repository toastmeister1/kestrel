package com.toastmeister1.kestrel.core.ui.options

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.toastmeister1.kestrel.core.designsystem.components.ToggleButton

@Composable
fun ToggleOptionComponent(
    modifier: Modifier = Modifier,
    label: String,
    items: List<String>,
    toggleState: ToggleSetState = rememberToggleSetState(),
    onChangeActiveItem: (checkedIndex: Int) -> Unit
) {
    OptionItem(
        modifier = modifier,
        label = label
    ) {
        items.forEachIndexed { index, value ->
            ToggleButton(
                label = value,
                checked = toggleState.isChecked(index),
                onCheckedChange = { onChangeActiveItem(toggleState.check(index)) }
            )
        }
    }
}

@Composable
fun rememberToggleSetState(initialCheckedIndex: Int = 0) = remember { ToggleSetState(initialCheckedIndex) }

@Stable
class ToggleSetState(initialCheckedIndex: Int = 0) {

    private var checkedIndex: MutableState<Int> = mutableStateOf(initialCheckedIndex)

    fun isChecked(index: Int): Boolean {
        return checkedIndex.value == index
    }

    fun check(index: Int): Int {
        checkedIndex.value = index
        return checkedIndex.value
    }

}