package com.eifel.bionisation4.common.item

import com.eifel.bionisation4.Info
import com.eifel.bionisation4.common.item.armor.BioArmor
import com.eifel.bionisation4.common.item.armor.BioMaterial
import com.eifel.bionisation4.common.item.lab.*
import com.eifel.bionisation4.common.item.utility.*
import net.minecraft.world.entity.EquipmentSlot
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries


object ItemRegistry {

    val ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Info.MOD_ID)

    val GARLIC_BULB = ITEMS.register("garlicbulb") { GarlicBulb() }

    val CLOTH = ITEMS.register("cloth") { CommonItem() }
    val BIO_BOOTS = ITEMS.register("bioarmor_boots") { BioArmor(BioMaterial.BIO_ARMOR, EquipmentSlot.FEET, ) }
    val BIO_LEGGINGS = ITEMS.register("bioarmor_leggings") { BioArmor(BioMaterial.BIO_ARMOR, EquipmentSlot.LEGS) }
    val BIO_CHEST = ITEMS.register("bioarmor_chest") { BioArmor(BioMaterial.BIO_ARMOR, EquipmentSlot.CHEST) }
    val BIO_HELMET = ITEMS.register("bioarmor_helmet") { BioArmor(BioMaterial.BIO_ARMOR, EquipmentSlot.HEAD) }

    val BANDAGE = ITEMS.register("bandage") { Bandage() }
    val SPLINT = ITEMS.register("splint") { Splint() }
    val IMMUNITY_CHECKER = ITEMS.register("immunitychecker") { ImmunityChecker() }

    val BIO_ANALYZER = ITEMS.register("bioanalyzer") { BioAnalyzer() }
    val COMPENDIUM = ITEMS.register("compendium") { Compendium() }
    val ANTIBIOTIC_VIAL = ITEMS.register("antibiotic_vial") { AntibioticVial() }
    val DNA_PATTERN = ITEMS.register("dna_pattern") { DNAPattern() }
    val VACCINE_INJECTOR = ITEMS.register("vaccine_injector") { VaccineInjector() }
    val VIRUS_SPREADER = ITEMS.register("virus_spreader") { VirusSpreader() }

    val ANTIBIOTIC_WEAK = ITEMS.register("antibiotic_weak") { Antibiotic() }
    val ANTIBIOTIC_MID = ITEMS.register("antibiotic_mid") { Antibiotic() }
    val ANTIBIOTIC_STRONG = ITEMS.register("antibiotic_strong") { Antibiotic() }

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