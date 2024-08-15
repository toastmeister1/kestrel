package com.toastmeister1.kestrel.feature.main

import com.toastmeister1.kestrel.core.common.UiStateExtension


@UiStateExtension
data class SampleModel(
    val name2: String,
    val age: Int,
    val sampleA: SampleA
)

@UiStateExtension
data class SampleA(
    val a: String,
    val b: String
)
