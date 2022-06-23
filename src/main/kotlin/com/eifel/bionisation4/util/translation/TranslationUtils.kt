package com.eifel.bionisation4.util.translation

import net.minecraft.client.resources.I18n
import net.minecraft.util.text.StringTextComponent
import net.minecraft.util.text.TranslationTextComponent

object TranslationUtils {

    fun getTranslatedText(prefix: String, text: String, suffix: String, objs: Array<Any> = arrayOf()) = I18n.get("$prefix.$text.$suffix", objs)
    fun getTranslatedTextComponent(prefix: String, text: String, suffix: String) = TranslationTextComponent("$prefix.$text.$suffix")
    fun getText(text: String) = StringTextComponent(text)
    fun getCommonTranslation(prefix: String, text: String, suffix: String, objs: Array<Any> = arrayOf()) = TranslationTextComponent("$prefix.$text.$suffix", objs)

}