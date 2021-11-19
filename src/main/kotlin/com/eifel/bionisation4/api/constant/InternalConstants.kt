package com.eifel.bionisation4.api.constant

import com.eifel.bionisation4.api.util.Utils.getModIDString

object InternalConstants {

    val GENE_ID_KEY = getModIDString("gene_id")
    val GENE_NAME_KEY = getModIDString("gene_name")
    val GENE_ACTIVE_KEY = getModIDString("gene_active")

    val GENE_POT_ID_KEY = getModIDString("gene_pid")
    val GENE_POT_DUR_KEY = getModIDString("gene_pdur")
    val GENE_POT_POW_KEY = getModIDString("gene_ppow")

    val GENE_POTIONS_KEY = getModIDString("gene_pots")

    val EFFECT_ID_KEY = getModIDString("eff_id")
    val EFFECT_NAME_KEY = getModIDString("eff_name")
    val EFFECT_TYPE_KEY = getModIDString("eff_type")

    val EFFECT_DURATION_KEY = getModIDString("eff_duration")
    val EFFECT_POWER_KEY = getModIDString("eff_power")
    val EFFECT_GENES_KEY = getModIDString("eff_genes")

    val EFFECT_CURE_KEY = getModIDString("eff_cure")
    val EFFECT_INFINITE_KEY = getModIDString("eff_infinite")
    val EFFECT_HIDDEN_KEY = getModIDString("eff_hidden")

    val EFFECT_EXPIRED_KEY = getModIDString("eff_expired")
    val EFFECT_MUTATE_KEY = getModIDString("eff_mutate")
    val EFFECT_MUTATE_PERIOD_KEY = getModIDString("eff_mutate_period")

    val EFFECT_INFECT_ITEMS_KEY = getModIDString("eff_infect_items")
    val EFFECT_ANTIBIOTIC_VULNERABLE_KEY = getModIDString("eff_antibiotic_vulnerable")
    val EFFECT_ANTIBIOTIC_RESISTANCE_KEY = getModIDString("eff_antibiotic_resistance")

    val EFFECT_SYNCABLE_KEY = getModIDString("eff_syncable")
    val EFFECT_NEED_SYNC_KEY = getModIDString("eff_need_sync")
}