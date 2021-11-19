package com.eifel.bionisation4.api.laboratory.registry

object LocalizationRegistry {

    private val EFFECT_DESCS = mutableMapOf<Int, String>()
    private val GENE_DESCS = mutableMapOf<Int, String>()

    fun loadDefaultGeneDescs() {
        //todo add mappings here
    }

    fun loadDefaultEffectDescs() {
        //todo add mappings here
    }

    fun addEffectLocalization(id: Int, desc: String) {
        EFFECT_DESCS[id] = desc
    }

    fun addGeneLocalization(id: Int, desc: String) {
        GENE_DESCS[id] = desc
    }

    fun getGeneDesc(id: Int) = GENE_DESCS.getOrDefault(id, "Unknown")
    fun getEffectDesc(id: Int) = EFFECT_DESCS.getOrDefault(id, "Unknown")
}