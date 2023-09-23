package com.toastmeister1.convention

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.ApplicationProductFlavor
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.ProductFlavor

enum class FlavorDimension {
    ConventType
}

@Suppress("EnumEntryName")
enum class KestrelFlavor(val dimension: FlavorDimension, val applicationSuffix: String) {
    demo(dimension = FlavorDimension.ConventType, applicationSuffix = ".demo"),
    pro(dimension = FlavorDimension.ConventType, applicationSuffix = ".pro")
}

fun configureFlavors(
    commonExtension: CommonExtension<*, *, *, *>,
    flavorConfigurationBlock: ProductFlavor.(flavor: KestrelFlavor) -> Unit = {}
) {
    commonExtension.apply {
        flavorDimensions += FlavorDimension.ConventType.name
        productFlavors {
            KestrelFlavor.values().forEach {
                create(it.name) {
                    dimension = it.dimension.name
                    flavorConfigurationBlock(this, it)
                    if (this@apply is ApplicationExtension && this is ApplicationProductFlavor) {
                        applicationIdSuffix = it.applicationSuffix
                    }
                }
            }
        }
    }
}