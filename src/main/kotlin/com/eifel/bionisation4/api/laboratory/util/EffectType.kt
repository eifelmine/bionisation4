package com.eifel.bionisation4.api.laboratory.util

import com.eifel.bionisation4.util.translation.TranslationUtils

enum class EffectType {

    COMMON, BACTERIA, VIRUS, FUNGI;

    fun getTranslatedName() = TranslationUtils.getTranslatedText("effect", this.name, "type")
}