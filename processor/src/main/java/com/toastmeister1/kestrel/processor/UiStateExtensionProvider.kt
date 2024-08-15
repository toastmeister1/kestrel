package com.toastmeister1.kestrel.processor

import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider

class UiStateExtensionProvider : SymbolProcessorProvider {

    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor =
        UiStateExtensionProcessor(
            codeGenerator = environment.codeGenerator,
            logger = environment.logger,
        )
}
