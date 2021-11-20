package com.eifel.bionisation4.util.translation

import net.minecraft.client.resources.I18n

object TranslationUtils {

    fun getTranslatedText(prefix: String, text: String, suffix: String, vararg objs: Object = arrayOf()) = I18n.get("$prefix.$text.$suffix", objs)
}