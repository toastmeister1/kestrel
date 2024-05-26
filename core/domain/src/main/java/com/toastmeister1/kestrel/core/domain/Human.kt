package com.toastmeister1.kestrel.core.domain

data class Human(
    val a: Int,
) {

    fun of(name: String): Human {
        return when (name) {
            "a" -> Human(a = 1)
            else -> Human(a = 2)
        }
    }
}
