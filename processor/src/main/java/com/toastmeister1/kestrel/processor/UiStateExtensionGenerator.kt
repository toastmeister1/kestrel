package com.toastmeister1.kestrel.processor

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.google.devtools.ksp.symbol.Modifier
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.LambdaTypeName
import java.io.OutputStreamWriter
import java.util.Locale

class UiStateExtensionGenerator(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger
) {

    fun generate(classDeclaration: KSClassDeclaration) {
        val fileName = classDeclaration.simpleName.asString() + "Extensions"
        val packageName = classDeclaration.packageName.asString()

        val fileSpec = FileSpec.builder(packageName = packageName, fileName = fileName).apply {
            val properties = classDeclaration.getAllProperties()
            val classType = ClassName(packageName, classDeclaration.simpleName.asString())
            val parameterMemberNames = classDeclaration.primaryConstructor?.parameters?.map { it.name?.asString() } ?: emptyList()

            properties
                .filter { parameterMemberNames.contains(it.simpleName.asString()) }
                .forEach { property ->
                    if (property.isDataClass()) {
                        createExtensionParameterFunction(classType = classType, property = property)
                    } else {
                        createSetFunction(classType = classType, property = property)
                    }
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

    private fun FileSpec.Builder.createSetFunction(
        classType: ClassName,
        property: KSPropertyDeclaration
    ) {
        val propertyType = property.type.resolve()
        val simpleName = property.simpleName

        val funSpec = FunSpec.builder(("set" + simpleName.getShortName().capitalizeFirst()))
            .receiver(classType)
            .returns(returnType = classType)
            .addParameter(
                name = simpleName.asString(),
                type = ClassName(
                    propertyType.declaration.packageName.asString(),
                    "$propertyType"
                )
            )
            .addStatement("return copy(${simpleName.asString()} = ${simpleName.asString()})")
            .build()

        addFunction(funSpec)
    }

    private fun FileSpec.Builder.createExtensionParameterFunction(
        classType: ClassName,
        property: KSPropertyDeclaration
    ) {
        val propertyType = property.type.resolve()
        val simpleName = property.simpleName
        val propertyClassName = ClassName(propertyType.declaration.packageName.asString(), "$propertyType")

        val funSpec = FunSpec.builder(("set" + simpleName.getShortName().capitalizeFirst()))
            .receiver(classType)
            .returns(returnType = classType)
            .addParameter(
                name = "func",
                type = LambdaTypeName.get(
                    receiver = propertyClassName,
                    returnType = propertyClassName
                )
            )
            .addStatement("return copy(${simpleName.asString()} = ${simpleName.asString()}.func())")
            .build()

        addFunction(funSpec)
    }

    private fun KSPropertyDeclaration.isDataClass(): Boolean {
        val propertyType = this.type.resolve()
        return propertyType.declaration.modifiers.contains(Modifier.DATA)
    }

    private fun String.capitalizeFirst(): String {
        return this.ifBlank { this }
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
    }
}