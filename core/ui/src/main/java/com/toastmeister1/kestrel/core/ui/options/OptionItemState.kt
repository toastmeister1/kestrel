package com.toastmeister1.kestrel.core.ui.options

interface OptionItemState {
    val label: String

    data class Toggle(override val label: String, val items: List<String>) : OptionItemState {
        var onClickToggleItem: (index: Int) -> Unit = {}
    }

    data class InputText(override val label: String, val initialValue: Float, val digits: Int = 1) : OptionItemState {
        var onChangeValue: (value: Float) -> Unit = {}
    }

}