package com.eifel.bionisation4.api.constant

import com.eifel.bionisation4.api.util.Utils.getModIDString

object InternalConstants {

    val GENE_ID_KEY = getModIDString("gene_id")
    val GENE_NAME_KEY = getModIDString("gene_name")
    val GENE_ACTIVE_KEY = getModIDString("gene_active")
    val GENE_POWER_KEY = getModIDString("gene_power")
    val GENE_CYCLIC_KEY = getModIDString("gene_cyclic")
    val GENE_DEACTIVATE_KEY = getModIDString("gene_deactivate")
    val GENE_POWER_MODIFY_KEY = getModIDString("gene_power_modify")

    val GENE_POT_ID_KEY = getModIDString("gene_pid")
    val GENE_POT_DUR_KEY = getModIDString("gene_pdur")
    val GENE_POT_POW_KEY = getModIDString("gene_ppow")
    val GENE_POT_REPL_KEY = getModIDString("gene_prepl")

    val GENE_POTIONS_KEY = getModIDString("gene_pots")

    val GENE_RADIUS_KEY = getModIDString("gene_radius")
    val GENE_HEALTH_KEY = getModIDString("gene_health")
    val GENE_BLOOD_KEY = getModIDString("gene_blood")
    val GENE_IMMUNITY_KEY = getModIDString("gene_immunity")
    val GENE_DURATION_KEY = getModIDString("gene_duration")
    val GENE_AMOUNT_KEY = getModIDString("gene_amount")
    val GENE_DELAY_KEY = getModIDString("gene_delay")
    val GENE_COUNT_KEY = getModIDString("gene_count")

    val EFFECT_ID_KEY = getModIDString("eff_id")
    val EFFECT_NAME_KEY = getModIDString("eff_name")
    val EFFECT_TYPE_KEY = getModIDString("eff_type")

    val EFFECT_DURATION_KEY = getModIDString("eff_duration")
    val EFFECT_TICKER_KEY = getModIDString("eff_ticker")
    val EFFECT_POWER_KEY = getModIDString("eff_power")
    val EFFECT_POWER_CHANGE_KEY = getModIDString("eff_power_change")
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

    val PROP_IMMUNITY_KEY = getModIDString("prop_immunity")
    val PROP_BLOOD_KEY = getModIDString("prop_blood")
    val PROP_EFFECT_KEY = getModIDString("prop_effects")
    val PROP_TICKER_KEY = getModIDString("prop_ticker")

    val PROP_EFFECT_LIST_KEY = getModIDString("prop_effect_list")

    val VIAL_EFFECT_KEY = getModIDString("vial_effect")

    //effects

    val GENE_DEFAULT_ID = 0

    val GENE_MOVEMENT_SPEED_ID = 1
    val GENE_MOVEMENT_SLOWDOWN_ID = 2
    val GENE_DIG_SPEED_ID = 3
    val GENE_DIG_SLOWDOWN_ID = 4
    val GENE_DAMAGE_BOOST_ID = 5
    val GENE_HEAL_ID = 6
    val GENE_HARM_ID = 7
    val GENE_JUMP_ID = 8
    val GENE_CONFUSION_ID = 9
    val GENE_REGENERATION_ID = 10
    val GENE_DAMAGE_RESISTANCE_ID = 11
    val GENE_FIRE_RESISTANCE_ID = 12
    val GENE_WATER_BREATHING_ID = 13
    val GENE_INVISIBILITY_ID = 14
    val GENE_BLINDNESS_ID = 15
    val GENE_NIGHT_VISION_ID = 16
    val GENE_HUNGER_ID = 17
    val GENE_WEAKNESS_ID = 18
    val GENE_POISON_ID = 19
    val GENE_WITHER_ID = 20
    val GENE_HEALTH_BOOST_ID = 21
    val GENE_ABSORPTION_ID = 22
    val GENE_SATURATION_ID = 23
    val GENE_GLOWING_ID = 24
    val GENE_LEVITATION_ID = 25
    val GENE_LUCK_ID = 26
    val GENE_UNLUCK_ID = 27
    val GENE_SLOW_FALLING_ID = 28
    val GENE_CONDUIT_POWER_ID = 29
    val GENE_DOLPHINS_GRACE_ID = 30
    val GENE_BAD_OMEN_ID = 31

    val GENE_AIR_ID = 32
    val GENE_HAND_WEAKNESS_ID = 33
    val GENE_IMMUNITY_DAMAGE_ID = 34
    val GENE_EXPLOSION_ID = 35
    val GENE_BLOOD_DAMAGE_ID = 36
    val GENE_CLONE_ID = 37
    val GENE_AIR_SPREAD_ID = 38
    val GENE_ATTACK_SPREAD_ID = 39
    val GENE_END_ID = 40
    val GENE_RANDOM_TELEPORT_ID = 41
    val GENE_WATER_FEAR_ID = 42
    val GENE_HEALTH_DAMAGE_ID = 43
    val GENE_HOSTILE_ID = 44
    val GENE_SUNBURN_ID = 45
    val GENE_AGGRESSIVE_ID = 46
    val GENE_PEACEFUL_ID = 47
    val GENE_GROUND_ID = 48
    val GENE_ARROW_IMMUNITY_ID = 49
    val GENE_FIRE_ID = 50
    val GENE_RADIUS_ID = 51
    val GENE_MUTAGEN_ID = 52
    val GENE_UNDEAD_ID = 53
    val GENE_BURN_ID = 54



    val EFFECT_DEFAULT_ID = 0
    val EFFECT_DEFAULT_STATE_ID = 1

    //effects
    val EFFECT_BLEEDING_ID = 2
    val EFFECT_IMMUNITY_ID = 3
    val EFFECT_INTERNAL_BLEEDING_ID = 4
    val EFFECT_SUNSTROKE_ID = 5
    val EFFECT_COLD_ID = 6
    val EFFECT_FATIGUE_ID = 7
    val EFFECT_FOOD_POISONING_ID = 8
    val EFFECT_FRACTURE_ID = 9
    val EFFECT_LACK_OF_BLOOD_ID = 10
    val EFFECT_LACK_OF_AIR_ID = 11
    val EFFECT_NETHER_ATMOSPHERE_ID = 12
    val EFFECT_ALIENATION_ID = 13
    val EFFECT_NIGHTMARES_ID = 14
    val EFFECT_DEBUG_ID = 15

    val VIRUS_RABIES_ID = 500
    val VIRUS_GIANT_ID = 501
    val VIRUS_ENDER_ID = 502
    val VIRUS_BRAIN_ID = 503
    val VIRUS_WITHER_ID = 504
    val VIRUS_BAT_ID = 505
    val VIRUS_CREEPER_ID = 506
    val VIRUS_RED_ID = 507
    val VIRUS_OCEAN_ID = 508
    val VIRUS_SKULL_ID = 509
    val VIRUS_POLAR_ID = 510
    val VIRUS_AER_ID = 511
    val VIRUS_DESERT_ID = 512
    val VIRUS_PTERO_ID = 513

}