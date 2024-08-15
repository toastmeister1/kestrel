package com.toastmeister1.kestrel.processor

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.validate
import com.toastmeister1.kestrel.core.common.UiStateExtension

class UiStateExtensionProcessor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger
) : SymbolProcessor {

    private val visitor = UiStateExtensionVisitor(codeGenerator, logger)

    override fun process(resolver: Resolver): List<KSAnnotated> {
        logger.warn("## start")
        // GenerateDataClass 어노테이션이 붙은 클래스 찾기
        val symbols = resolver.getSymbolsWithAnnotation(UiStateExtension::class.java.name)

        symbols
            .filter(KSAnnotated::validate)
            .filter { it is KSClassDeclaration }
            .forEach {
                it.accept(visitor, Unit)
            }

        return emptyList()
    }
}