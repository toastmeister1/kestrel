package com.toastmeister1.kestrel.processor

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSVisitorVoid
import com.google.devtools.ksp.symbol.Modifier
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import java.io.OutputStreamWriter
import java.util.Locale

class UiStateExtensionVisitor(
    codeGenerator: CodeGenerator,
    logger: KSPLogger
) : KSVisitorVoid() {

    private val generator = UiStateExtensionGenerator(codeGenerator, logger)

    override fun visitClassDeclaration(classDeclaration: KSClassDeclaration, data: Unit) {
        check(classDeclaration.isDataClass()) { "${classDeclaration.simpleName} must be data class" }

        generator.generate(classDeclaration)
    }

    private fun KSClassDeclaration.isDataClass() = modifiers.contains(Modifier.DATA)

}

class UiStateExtensionGenerator(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger
) {
    fun generate(
        classDeclaration: KSClassDeclaration
    ) {
        val fileName = classDeclaration.simpleName.asString() + "Extensions"
        val packageName = classDeclaration.packageName.asString()
        val properties = classDeclaration.getAllProperties()

        val fileSpec = FileSpec.builder(
            packageName = packageName,
            fileName = fileName
        ).apply {
            val classType = ClassName(packageName, classDeclaration.simpleName.asString())

            properties.forEach { property ->
                val funSpec = FunSpec.builder(
                    ("set" + property.simpleName.getShortName().capitalizeFirst())
                )
                    .receiver(classType)
                    .returns(returnType = classType)
                    .addParameter(
                        name = property.simpleName.asString(),
                        type = ClassName(
                            property.type.resolve().declaration.packageName.asString(),
                            "${property.type.resolve()}"
                        )
                    )
                    .addCode(
                        """
                        return copy(${property.simpleName.asString()} = ${property.simpleName.asString()})
                        """.trimIndent()
                    )
                    .build()

                addFunction(funSpec)
            }
        }
            .build()

        codeGenerator.createNewFile(
            dependencies = Dependencies(aggregating = true, classDeclaration.containingFile!!),
            packageName = packageName,
            fileName = fileName
        ).use { output ->
            OutputStreamWriter(output).use { writer ->
                fileSpec.writeTo(writer)
            }
        }
    }

    fun String.capitalizeFirst(): String {
        return this.ifBlank { this }
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
    }

}