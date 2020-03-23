/*
 * Copyright 2010-2020 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.tools.projectWizard.wizard.service

import com.intellij.ide.fileTemplates.FileTemplateUtil
import org.jetbrains.kotlin.tools.projectWizard.core.service.TemplateEngineService
import org.jetbrains.kotlin.tools.projectWizard.templates.FileTemplateDescriptor
import org.jetbrains.kotlin.tools.projectWizard.templates.Template
import java.util.*

class IdeaVelocityEngineTemplateService : TemplateEngineService, IdeaWizardService {
    override fun renderTemplate(template: FileTemplateDescriptor, data: Map<String, Any?>): String {
        val templatePath = template.templateId
        val templateText = try {
             Template::class.java.getResource(templatePath).readText()
        } catch (e: Throwable) {
            println(e)
            throw e
        }
        val properties = Properties().apply { putAll(data) }
        return FileTemplateUtil.mergeTemplate(properties, templateText, false) { e ->
            println(e)
            throw e
        }
    }

}