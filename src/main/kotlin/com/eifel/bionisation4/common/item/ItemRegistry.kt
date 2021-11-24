package com.eifel.bionisation4.common.item

import com.eifel.bionisation4.Info
import com.eifel.bionisation4.common.item.lab.ItemEffectVial
import com.eifel.bionisation4.common.item.utility.Bandage
import com.eifel.bionisation4.common.item.utility.GarlicBulb
import com.eifel.bionisation4.common.item.utility.ImmunityChecker
import com.eifel.bionisation4.common.item.utility.Splint
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries


object ItemRegistry {

    val ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Info.MOD_ID)

    val BANDAGE = ITEMS.register("bandage") { Bandage() }
    val SPLINT = ITEMS.register("splint") { Splint() }
    val IMMUNITY_CHECKER = ITEMS.register("immunitychecker") { ImmunityChecker() }
    val GARLIC_BULB = ITEMS.register("garlicbulb") { GarlicBulb() }

    //utility
    val BAT_WING = ITEMS.register("batwing") { CommonItem() }
    val CREEPER_HEART = ITEMS.register("creeperheart") { CommonItem(enchanted = true) }
    val BLAZE_CORE = ITEMS.register("blazecore") { CommonItem(enchanted = true) }
    val DROWNED_TOOTH = ITEMS.register("drownedtooth") { CommonItem() }
    val CHICKEN_HEAD = ITEMS.register("chickenhead") { CommonItem() }
    val STRIDER_SKIN = ITEMS.register("striderskin") { CommonItem() }
    val OCT_TENTACLE = ITEMS.register("octtentacle") { CommonItem() }
    val ZOMBIE_HORSE_BONE = ITEMS.register("zhorsebone") { CommonItem() }
    val PIGLIN_FANG = ITEMS.register("piglinfang") { CommonItem() }
    val ZOGLIN_SKULL = ITEMS.register("zoglinskull") { CommonItem() }
    val SPECTRAL_DUST = ITEMS.register("spectraldust") { CommonItem() }
    val SPIDER_LEG = ITEMS.register("spiderleg") { CommonItem() }
    val SKELETON_DUST = ITEMS.register("skeletondust") { CommonItem() }
    val WITCH_POTION = ITEMS.register("witchpotion") { CommonItem(enchanted = true) }
    val EVOKER_POTION = ITEMS.register("evokerpotion") { CommonItem(enchanted = true) }
    val VEX_WING = ITEMS.register("vexwing") { CommonItem() }
    val PHANTOM_TAIL = ITEMS.register("phantomtail") { CommonItem() }
    val SHULKER_ESSENCE = ITEMS.register("shulkeressence") { CommonItem(enchanted = true) }
    val HUSK_BRAIN = ITEMS.register("huskbrain") { CommonItem() }
    val WITHER_CORE = ITEMS.register("withercore") { CommonItem(enchanted = true) }
    val WEIRD_SEEDS = ITEMS.register("weirdseeds") { CommonItem() }
    val WOLF_TOOTH = ITEMS.register("wolfstooth") { CommonItem() }


    val EFFECT_VIAL = ITEMS.register("effect_vial") { ItemEffectVial() }
}