package com.toastmeister1.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

abstract class CustomTask : DefaultTask() {

    @TaskAction
    fun invoke() {

    }
}