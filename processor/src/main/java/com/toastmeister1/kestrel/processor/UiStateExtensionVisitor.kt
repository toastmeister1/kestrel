package com.toastmeister1.kestrel.processor

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSVisitorVoid
import com.google.devtools.ksp.symbol.Modifier

class UiStateExtensionVisitor(
    codeGenerator: CodeGenerator,
    logger: KSPLogger
) : KSVisitorVoid() {

    private val generator = UiStateExtensionGenerator(codeGenerator, logger)

    override fun visitClassDeclaration(classDeclaration: KSClassDeclaration, data: Unit) {
        check(classDeclaration.isDataClass()) { "${classDeclaration.simpleName.asString()} must be data class" }

        generator.generate(classDeclaration)
    }

    private fun KSClassDeclaration.isDataClass() = modifiers.contains(Modifier.DATA)
}