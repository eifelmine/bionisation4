package com.eifel.bionisation4.util.translation

import net.minecraft.client.resources.language.I18n
import net.minecraft.network.chat.Component

object TranslationUtils {

    fun getTranslatedText(prefix: String, text: String, suffix: String, objs: Array<Any> = arrayOf()) = I18n.get("$prefix.$text.$suffix", objs)
    fun getTranslatedTextComponent(prefix: String, text: String, suffix: String) = Component.translatable("$prefix.$text.$suffix")
    fun getText(text: String) = Component.literal(text)
    fun getCommonTranslation(prefix: String, text: String, suffix: String, objs: Array<Any> = arrayOf()) = Component.translatable("$prefix.$text.$suffix", objs)

}