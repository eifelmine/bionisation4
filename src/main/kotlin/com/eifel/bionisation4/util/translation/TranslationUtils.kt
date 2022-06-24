package com.eifel.bionisation4.util.translation

import net.minecraft.client.resources.language.I18n
import net.minecraft.network.chat.TextComponent
import net.minecraft.network.chat.TranslatableComponent

object TranslationUtils {

    fun getTranslatedText(prefix: String, text: String, suffix: String, objs: Array<Any> = arrayOf()) = I18n.get("$prefix.$text.$suffix", objs)
    fun getTranslatedTextComponent(prefix: String, text: String, suffix: String) = TranslatableComponent("$prefix.$text.$suffix")
    fun getText(text: String) = TextComponent(text)
    fun getCommonTranslation(prefix: String, text: String, suffix: String, objs: Array<Any> = arrayOf()) = TranslatableComponent("$prefix.$text.$suffix", objs)

}