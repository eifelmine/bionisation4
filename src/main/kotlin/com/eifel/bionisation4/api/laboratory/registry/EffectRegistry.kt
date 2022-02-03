package com.eifel.bionisation4.api.laboratory.registry

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.species.Gene
import com.eifel.bionisation4.api.util.Utils
import com.eifel.bionisation4.common.block.BlockRegistry
import com.eifel.bionisation4.common.config.OverrideHandler
import com.eifel.bionisation4.common.item.ItemRegistry
import com.eifel.bionisation4.common.laboratory.bacteria.*
import com.eifel.bionisation4.common.laboratory.bacteria.Clone
import com.eifel.bionisation4.common.laboratory.common.DefaultEffect
import com.eifel.bionisation4.common.laboratory.common.DefaultStateEffect
import com.eifel.bionisation4.common.laboratory.common.effect.*
import com.eifel.bionisation4.common.laboratory.gene.DefaultGene
import com.eifel.bionisation4.common.laboratory.gene.species.potion.*
import com.eifel.bionisation4.common.laboratory.gene.species.potion.Glowing
import com.eifel.bionisation4.common.laboratory.gene.species.type.*
import com.eifel.bionisation4.common.laboratory.treat.Antibiotic
import com.eifel.bionisation4.common.laboratory.treat.Vaccine
import com.eifel.bionisation4.common.laboratory.treat.VaccineImmunity
import com.eifel.bionisation4.common.laboratory.virus.*
import com.eifel.bionisation4.common.laboratory.virus.Ender
import com.eifel.bionisation4.common.laboratory.virus.Wither
import net.minecraft.block.Blocks
import net.minecraft.entity.EntityType
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.monster.MonsterEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.Items

object EffectRegistry {

    private val EFFECTS = mutableMapOf<Int, Class<out AbstractEffect>>()
    private val GENES = mutableMapOf<Int, Class<out Gene>>()

    private val EFFECT_INSTANCES = mutableMapOf<Int, AbstractEffect>()
    private val EFFECT_CHANCES = mutableMapOf<Int, Int>()
    private val GENE_INSTANCES = mutableMapOf<Int, Gene>()

    private val EFFECT_OCCASIONS = mutableMapOf<EntityType<*>, MutableMap<Int, Int>>()
    private val EFFECT_OCCASIONS_CLASS = mutableMapOf<Class<out LivingEntity>, MutableMap<Int, Int>>()

    private val RANDOM_VIRUS_GENES = mutableMapOf<Int, Int>()
    //list of gene ids it can mutate with
    private val GENE_MUTATIONS = mutableMapOf<Int, List<Int>>()

    private val ANTIBIOTICS = mutableMapOf<Item, List<Int>>()

    //two effects for mutation and result
    private val EFFECT_SYMBIOSIS = mutableListOf<Triple<Int, Int, Int>>()

    private val GENE_VIALS = mutableMapOf<Int, ItemStack>()
    //list of 3 stacks used in Cure Station
    private val BACTERIA_CURES = mutableMapOf<Int, Triple<ItemStack, ItemStack, ItemStack>>()

    private val DROPS = mutableMapOf<EntityType<*>, Pair<Int, ItemStack>>()

    fun loadDefaultGenes() {
        //todo add mappings here
        registerGeneClass(InternalConstants.GENE_DEFAULT_ID, DefaultGene::class.java)
        registerGeneClass(InternalConstants.GENE_MOVEMENT_SPEED_ID, MovementSpeed::class.java)
        registerGeneClass(InternalConstants.GENE_MOVEMENT_SLOWDOWN_ID, MovementSlowdown::class.java)
        registerGeneClass(InternalConstants.GENE_DIG_SPEED_ID, DigSpeed::class.java)
        registerGeneClass(InternalConstants.GENE_DIG_SLOWDOWN_ID, DigSlowdown::class.java)
        registerGeneClass(InternalConstants.GENE_DAMAGE_BOOST_ID, DamageBoost::class.java)
        registerGeneClass(InternalConstants.GENE_HEAL_ID, Heal::class.java)
        registerGeneClass(InternalConstants.GENE_HARM_ID, Harm::class.java)
        registerGeneClass(InternalConstants.GENE_JUMP_ID, Jump::class.java)
        registerGeneClass(InternalConstants.GENE_CONFUSION_ID, Confusion::class.java)
        registerGeneClass(InternalConstants.GENE_REGENERATION_ID, Regeneration::class.java)
        registerGeneClass(InternalConstants.GENE_DAMAGE_RESISTANCE_ID, DamageResistance::class.java)
        registerGeneClass(InternalConstants.GENE_FIRE_RESISTANCE_ID, FireResistance::class.java)
        registerGeneClass(InternalConstants.GENE_WATER_BREATHING_ID, WaterBreathing::class.java)
        registerGeneClass(InternalConstants.GENE_INVISIBILITY_ID, Invisibility::class.java)
        registerGeneClass(InternalConstants.GENE_BLINDNESS_ID, Blindness::class.java)
        registerGeneClass(InternalConstants.GENE_NIGHT_VISION_ID, NightVision::class.java)
        registerGeneClass(InternalConstants.GENE_HUNGER_ID, Hunger::class.java)
        registerGeneClass(InternalConstants.GENE_WEAKNESS_ID, Weakness::class.java)
        registerGeneClass(InternalConstants.GENE_POISON_ID, Poison::class.java)
        registerGeneClass(InternalConstants.GENE_HEALTH_BOOST_ID, HealthBoost::class.java)
        registerGeneClass(InternalConstants.GENE_ABSORPTION_ID, Absorption::class.java)
        registerGeneClass(InternalConstants.GENE_SATURATION_ID, Saturation::class.java)
        registerGeneClass(InternalConstants.GENE_GLOWING_ID, Glowing::class.java)
        registerGeneClass(InternalConstants.GENE_LEVITATION_ID, Levitation::class.java)
        registerGeneClass(InternalConstants.GENE_LUCK_ID, Luck::class.java)
        registerGeneClass(InternalConstants.GENE_UNLUCK_ID, Unluck::class.java)
        registerGeneClass(InternalConstants.GENE_SLOW_FALLING_ID, SlowFalling::class.java)
        registerGeneClass(InternalConstants.GENE_CONDUIT_POWER_ID, ConduitPower::class.java)
        registerGeneClass(InternalConstants.GENE_DOLPHINS_GRACE_ID, DolphinsGrace::class.java)
        registerGeneClass(InternalConstants.GENE_BAD_OMEN_ID, BadOmen::class.java)
        registerGeneClass(InternalConstants.GENE_AIR_ID, Air::class.java)
        registerGeneClass(InternalConstants.GENE_HAND_WEAKNESS_ID, HandWeakness::class.java)
        registerGeneClass(InternalConstants.GENE_IMMUNITY_DAMAGE_ID, ImmunityDamage::class.java)
        registerGeneClass(InternalConstants.GENE_EXPLOSION_ID, Explosion::class.java)
        registerGeneClass(InternalConstants.GENE_BLOOD_DAMAGE_ID, BloodDamage::class.java)
        registerGeneClass(InternalConstants.GENE_CLONE_ID, com.eifel.bionisation4.common.laboratory.gene.species.type.Clone::class.java)
        registerGeneClass(InternalConstants.GENE_AIR_SPREAD_ID, AirSpread::class.java)
        registerGeneClass(InternalConstants.GENE_ATTACK_SPREAD_ID, AttackSpread::class.java)
        registerGeneClass(InternalConstants.GENE_END_ID, End::class.java)
        registerGeneClass(InternalConstants.GENE_RANDOM_TELEPORT_ID, RandomTeleport::class.java)
        registerGeneClass(InternalConstants.GENE_WATER_FEAR_ID, WaterFear::class.java)
        registerGeneClass(InternalConstants.GENE_HEALTH_DAMAGE_ID, HealthDamage::class.java)
        registerGeneClass(InternalConstants.GENE_HOSTILE_ID, Hostile::class.java)
        registerGeneClass(InternalConstants.GENE_SUNBURN_ID, Sunburn::class.java)
        registerGeneClass(InternalConstants.GENE_AGGRESSIVE_ID, Aggressive::class.java)
        registerGeneClass(InternalConstants.GENE_PEACEFUL_ID, Peaceful::class.java)
        registerGeneClass(InternalConstants.GENE_GROUND_ID, Ground::class.java)
        registerGeneClass(InternalConstants.GENE_ARROW_IMMUNITY_ID, ArrowImmunity::class.java)
        registerGeneClass(InternalConstants.GENE_FIRE_ID, Fire::class.java)
        registerGeneClass(InternalConstants.GENE_RADIUS_ID, Radius::class.java)
        registerGeneClass(InternalConstants.GENE_MUTAGEN_ID, Mutagen::class.java)
        registerGeneClass(InternalConstants.GENE_UNDEAD_ID, Undead::class.java)
        registerGeneClass(InternalConstants.GENE_BURN_ID, Burn::class.java)
        registerGeneClass(InternalConstants.GENE_RANDOM_WORDS_ID, RandomWords::class.java)
        registerGeneClass(InternalConstants.GENE_SHADOW_ID, Shadow::class.java)
        registerGeneClass(InternalConstants.GENE_INFINITY_ID, Infinity::class.java)
    }

    fun loadDefaultGeneMutations() {
        //todo add mappings here
        //just make all genes can mutate into each other
        getGenes().keys.forEach { id ->
            registerGeneMutation(id, getGenes().keys.toList())
        }
        //whoever wants to change this - go ahead
    }

    fun loadDefaultEffectOccasions() {
        //todo add mappings here
        registerEffectOccasion(EntityType.FOX, InternalConstants.VIRUS_RABIES_ID, 30)
        registerEffectOccasion(EntityType.WOLF, InternalConstants.VIRUS_RABIES_ID, 10)
        registerEffectOccasion(EntityType.POLAR_BEAR, InternalConstants.VIRUS_RABIES_ID, 10)
        registerEffectOccasion(MonsterEntity::class.java, InternalConstants.VIRUS_GIANT_ID, 10)
        registerEffectOccasion(MonsterEntity::class.java, InternalConstants.BACTERIA_CLONE_ID, 10)
        registerEffectOccasion(EntityType.ENDERMAN, InternalConstants.VIRUS_ENDER_ID, 35)
        registerEffectOccasion(EntityType.ZOMBIE, InternalConstants.VIRUS_BRAIN_ID, 15)
        registerEffectOccasion(EntityType.ZOMBIE_VILLAGER, InternalConstants.VIRUS_BRAIN_ID, 15)
        registerEffectOccasion(EntityType.HUSK, InternalConstants.VIRUS_BRAIN_ID, 15)
        registerEffectOccasion(EntityType.WITHER_SKELETON, InternalConstants.VIRUS_WITHER_ID, 25)
        registerEffectOccasion(EntityType.BAT, InternalConstants.VIRUS_BAT_ID, 25)
        registerEffectOccasion(EntityType.CREEPER, InternalConstants.VIRUS_CREEPER_ID, 15)
        registerEffectOccasion(EntityType.SPIDER, InternalConstants.VIRUS_RED_ID, 10)
        registerEffectOccasion(EntityType.GUARDIAN, InternalConstants.VIRUS_OCEAN_ID, 25)
        registerEffectOccasion(EntityType.SKELETON, InternalConstants.VIRUS_SKULL_ID, 10)
        registerEffectOccasion(EntityType.POLAR_BEAR, InternalConstants.VIRUS_POLAR_ID, 10)
        registerEffectOccasion(EntityType.HUSK, InternalConstants.VIRUS_DESERT_ID, 15)
        registerEffectOccasion(EntityType.CHICKEN, InternalConstants.VIRUS_PTERO_ID, 5)
    }

    fun loadDefaultEffects() {
        //todo add mappings here
        //effect
        registerEffectClass(InternalConstants.EFFECT_DEFAULT_ID, DefaultEffect::class.java)
        registerEffectClass(InternalConstants.EFFECT_DEFAULT_STATE_ID, DefaultStateEffect::class.java)
        registerEffectClass(InternalConstants.EFFECT_BLEEDING_ID, Bleeding::class.java)
        registerEffectClass(InternalConstants.EFFECT_IMMUNITY_ID, Immunity::class.java)
        registerEffectClass(InternalConstants.EFFECT_INTERNAL_BLEEDING_ID, InternalBleeding::class.java)
        registerEffectClass(InternalConstants.EFFECT_SUNSTROKE_ID, Sunstroke::class.java)
        registerEffectClass(InternalConstants.EFFECT_COLD_ID, Cold::class.java)
        registerEffectClass(InternalConstants.EFFECT_FATIGUE_ID, Fatigue::class.java)
        registerEffectClass(InternalConstants.EFFECT_FOOD_POISONING_ID, FoodPoisoning::class.java)
        registerEffectClass(InternalConstants.EFFECT_FRACTURE_ID, Fracture::class.java)
        registerEffectClass(InternalConstants.EFFECT_LACK_OF_BLOOD_ID, LackOfBlood::class.java)
        registerEffectClass(InternalConstants.EFFECT_LACK_OF_AIR_ID, LackOfAir::class.java)
        registerEffectClass(InternalConstants.EFFECT_NETHER_ATMOSPHERE_ID, NetherAtmosphere::class.java)
        registerEffectClass(InternalConstants.EFFECT_ALIENATION_ID, Alienation::class.java)
        registerEffectClass(InternalConstants.EFFECT_NIGHTMARES_ID, Nightmares::class.java)
        registerEffectClass(InternalConstants.EFFECT_DEBUG_ID, Debug::class.java)
        //virus
        registerEffectClass(InternalConstants.VIRUS_RABIES_ID, Rabies::class.java)
        registerEffectClass(InternalConstants.VIRUS_GIANT_ID, Giant::class.java)
        registerEffectClass(InternalConstants.VIRUS_ENDER_ID, Ender::class.java)
        registerEffectClass(InternalConstants.VIRUS_BRAIN_ID, Brain::class.java)
        registerEffectClass(InternalConstants.VIRUS_WITHER_ID, Wither::class.java)
        registerEffectClass(InternalConstants.VIRUS_BAT_ID, Bat::class.java)
        registerEffectClass(InternalConstants.VIRUS_CREEPER_ID, Creeper::class.java)
        registerEffectClass(InternalConstants.VIRUS_RED_ID, Red::class.java)
        registerEffectClass(InternalConstants.VIRUS_OCEAN_ID, Ocean::class.java)
        registerEffectClass(InternalConstants.VIRUS_SKULL_ID, Skull::class.java)
        registerEffectClass(InternalConstants.VIRUS_POLAR_ID, Polar::class.java)
        registerEffectClass(InternalConstants.VIRUS_AER_ID, Aer::class.java)
        registerEffectClass(InternalConstants.VIRUS_DESERT_ID, Desert::class.java)
        registerEffectClass(InternalConstants.VIRUS_PTERO_ID, Ptero::class.java)
        registerEffectClass(InternalConstants.VIRUS_WILD_ID, Wild::class.java)
        registerEffectClass(InternalConstants.VIRUS_CUSTOM_ID, Custom::class.java)
        //bacteria
        registerEffectClass(InternalConstants.BACTERIA_BLACK_ID, Black::class.java)
        registerEffectClass(InternalConstants.BACTERIA_SWAMP_ID, Swamp::class.java)
        registerEffectClass(InternalConstants.BACTERIA_GLOWING_ID, com.eifel.bionisation4.common.laboratory.bacteria.Glowing::class.java)
        registerEffectClass(InternalConstants.BACTERIA_WATER_ID, Water::class.java)
        registerEffectClass(InternalConstants.BACTERIA_ENDER_ID, com.eifel.bionisation4.common.laboratory.bacteria.Ender::class.java)
        registerEffectClass(InternalConstants.BACTERIA_CACTUS_ID, Cactus::class.java)
        registerEffectClass(InternalConstants.BACTERIA_BONE_ID, Bone::class.java)
        registerEffectClass(InternalConstants.BACTERIA_TERRA_ID, Terra::class.java)
        registerEffectClass(InternalConstants.BACTERIA_MYCELIUM_ID, Mycelium::class.java)
        registerEffectClass(InternalConstants.BACTERIA_SEA_ID, Sea::class.java)
        registerEffectClass(InternalConstants.BACTERIA_CLONE_ID, Clone::class.java)
        //cures
        registerEffectClass(InternalConstants.EFFECT_ANTIBIOTIC_ID, Antibiotic::class.java)
        registerEffectClass(InternalConstants.EFFECT_VACCINE_ID, Vaccine::class.java)
        registerEffectClass(InternalConstants.EFFECT_VACCINNE_IMMUNITY_ID, VaccineImmunity::class.java)
    }

    fun loadDefaultAntibiotics() {
        registerAntibiotic(ItemRegistry.ANTIBIOTIC_WEAK.get(), mutableListOf(InternalConstants.BACTERIA_SWAMP_ID, InternalConstants.BACTERIA_WATER_ID, InternalConstants.BACTERIA_CACTUS_ID, InternalConstants.BACTERIA_SEA_ID))
        registerAntibiotic(ItemRegistry.ANTIBIOTIC_MID.get(), mutableListOf(InternalConstants.BACTERIA_ENDER_ID, InternalConstants.BACTERIA_BONE_ID, InternalConstants.BACTERIA_TERRA_ID, InternalConstants.BACTERIA_MYCELIUM_ID))
        registerAntibiotic(ItemRegistry.ANTIBIOTIC_STRONG.get(), mutableListOf(InternalConstants.BACTERIA_GLOWING_ID, InternalConstants.BACTERIA_BLACK_ID))
    }

    fun loadDefaultEffectChances() {
        //todo add mappings here
        registerEffectChance(InternalConstants.EFFECT_BLEEDING_ID, 5)
        registerEffectChance(InternalConstants.EFFECT_SUNSTROKE_ID, 35)
        registerEffectChance(InternalConstants.EFFECT_COLD_ID, 20)
        registerEffectChance(InternalConstants.EFFECT_FOOD_POISONING_ID, 15)
        registerEffectChance(InternalConstants.EFFECT_FRACTURE_ID, 10)
        registerEffectChance(InternalConstants.EFFECT_LACK_OF_AIR_ID, 5)
        registerEffectChance(InternalConstants.EFFECT_NETHER_ATMOSPHERE_ID, 10)
        registerEffectChance(InternalConstants.EFFECT_ALIENATION_ID, 10)
        registerEffectChance(InternalConstants.EFFECT_NIGHTMARES_ID, 10)
        //virus
        registerEffectChance(InternalConstants.VIRUS_AER_ID, 10)
        //bacteria
        registerEffectChance(InternalConstants.BACTERIA_BLACK_ID, 100)
        registerEffectChance(InternalConstants.BACTERIA_SWAMP_ID, 10)
        registerEffectChance(InternalConstants.BACTERIA_GLOWING_ID, 15)
        registerEffectChance(InternalConstants.BACTERIA_WATER_ID, 25)
        registerEffectChance(InternalConstants.BACTERIA_ENDER_ID, 100)
        registerEffectChance(InternalConstants.BACTERIA_CACTUS_ID, 20)
        registerEffectChance(InternalConstants.BACTERIA_BONE_ID, 5)
        registerEffectChance(InternalConstants.BACTERIA_TERRA_ID, 10)
        registerEffectChance(InternalConstants.BACTERIA_MYCELIUM_ID, 25)
        registerEffectChance(InternalConstants.BACTERIA_SEA_ID, 25)
    }

    fun loadRandomVirusGenes() {
        //todo add mappings here
        //im too lazy to do it myself, so
        getGenes().keys.forEach { id ->
            registerRandomVirusGene(id, Utils.random.nextInt(1, 15))
        }
        //ooooh yeah
    }

    fun loadDefaultSymbiosis() {
        //todo add mappings here
    }

    fun loadDefaultGeneVials() {
        //todo add mappings here
        registerGeneVial(InternalConstants.GENE_DEFAULT_ID, ItemStack.EMPTY)
        registerGeneVial(InternalConstants.GENE_MOVEMENT_SPEED_ID, ItemStack(Items.SUGAR))
        registerGeneVial(InternalConstants.GENE_MOVEMENT_SLOWDOWN_ID, ItemStack(Items.BONE))
        registerGeneVial(InternalConstants.GENE_DIG_SPEED_ID, ItemStack(Items.GHAST_TEAR))
        registerGeneVial(InternalConstants.GENE_DIG_SLOWDOWN_ID, ItemStack(Items.INK_SAC))
        registerGeneVial(InternalConstants.GENE_DAMAGE_BOOST_ID, ItemStack(ItemRegistry.HUSK_BRAIN.get()))
        registerGeneVial(InternalConstants.GENE_HEAL_ID, ItemStack(Items.GOLDEN_APPLE))
        registerGeneVial(InternalConstants.GENE_HARM_ID, ItemStack(ItemRegistry.WITCH_POTION.get()))
        registerGeneVial(InternalConstants.GENE_JUMP_ID, ItemStack(Items.FEATHER))
        registerGeneVial(InternalConstants.GENE_CONFUSION_ID, ItemStack(ItemRegistry.OCT_TENTACLE.get()))
        registerGeneVial(InternalConstants.GENE_REGENERATION_ID, ItemStack(Items.GLISTERING_MELON_SLICE))
        registerGeneVial(InternalConstants.GENE_DAMAGE_RESISTANCE_ID, ItemStack(Items.MAGMA_CREAM))
        registerGeneVial(InternalConstants.GENE_FIRE_RESISTANCE_ID, ItemStack(ItemRegistry.BLAZE_CORE.get()))
        registerGeneVial(InternalConstants.GENE_WATER_BREATHING_ID, ItemStack(ItemRegistry.DROWNED_TOOTH.get()))
        registerGeneVial(InternalConstants.GENE_INVISIBILITY_ID, ItemStack(ItemRegistry.SPECTRAL_DUST.get()))
        registerGeneVial(InternalConstants.GENE_BLINDNESS_ID, ItemStack(Items.SPIDER_EYE))
        registerGeneVial(InternalConstants.GENE_NIGHT_VISION_ID, ItemStack(Items.ENDER_PEARL))
        registerGeneVial(InternalConstants.GENE_HUNGER_ID, ItemStack(Items.ROTTEN_FLESH))
        registerGeneVial(InternalConstants.GENE_WEAKNESS_ID, ItemStack(ItemRegistry.SKELETON_DUST.get()))
        registerGeneVial(InternalConstants.GENE_POISON_ID, ItemStack(Items.FERMENTED_SPIDER_EYE))
        registerGeneVial(InternalConstants.GENE_HEALTH_BOOST_ID, ItemStack(ItemRegistry.EVOKER_POTION.get()))
        registerGeneVial(InternalConstants.GENE_ABSORPTION_ID, ItemStack(Items.PHANTOM_MEMBRANE))
        registerGeneVial(InternalConstants.GENE_SATURATION_ID, ItemStack(Items.EGG))
        registerGeneVial(InternalConstants.GENE_GLOWING_ID, ItemStack(Items.GLOWSTONE_DUST))
        registerGeneVial(InternalConstants.GENE_LEVITATION_ID, ItemStack(ItemRegistry.VEX_WING.get()))
        registerGeneVial(InternalConstants.GENE_LUCK_ID, ItemStack(Items.RABBIT_FOOT))
        registerGeneVial(InternalConstants.GENE_UNLUCK_ID, ItemStack(ItemRegistry.CHICKEN_HEAD.get()))
        registerGeneVial(InternalConstants.GENE_SLOW_FALLING_ID, ItemStack(ItemRegistry.STRIDER_SKIN.get()))
        registerGeneVial(InternalConstants.GENE_CONDUIT_POWER_ID, ItemStack(Items.CONDUIT))
        registerGeneVial(InternalConstants.GENE_DOLPHINS_GRACE_ID, ItemStack(Items.TROPICAL_FISH))
        registerGeneVial(InternalConstants.GENE_BAD_OMEN_ID, ItemStack(ItemRegistry.WEIRD_SEEDS.get()))
        registerGeneVial(InternalConstants.GENE_AIR_ID, ItemStack(Items.PRISMARINE_SHARD))
        registerGeneVial(InternalConstants.GENE_HAND_WEAKNESS_ID, ItemStack(ItemRegistry.ZOMBIE_HORSE_BONE.get()))
        registerGeneVial(InternalConstants.GENE_IMMUNITY_DAMAGE_ID, ItemStack(ItemRegistry.PIGLIN_FANG.get()))
        registerGeneVial(InternalConstants.GENE_EXPLOSION_ID, ItemStack(ItemRegistry.CREEPER_HEART.get()))
        registerGeneVial(InternalConstants.GENE_BLOOD_DAMAGE_ID, ItemStack(ItemRegistry.SPIDER_LEG.get()))
        registerGeneVial(InternalConstants.GENE_CLONE_ID, ItemStack(ItemRegistry.PHANTOM_TAIL.get()))
        registerGeneVial(InternalConstants.GENE_AIR_SPREAD_ID, ItemStack(Items.NETHER_STAR))
        registerGeneVial(InternalConstants.GENE_ATTACK_SPREAD_ID, ItemStack(Items.NETHER_WART))
        registerGeneVial(InternalConstants.GENE_END_ID, ItemStack(ItemRegistry.SHULKER_ESSENCE.get()))
        registerGeneVial(InternalConstants.GENE_RANDOM_TELEPORT_ID, ItemStack(Items.ENDER_EYE))
        registerGeneVial(InternalConstants.GENE_WATER_FEAR_ID, ItemStack(Items.NAUTILUS_SHELL))
        registerGeneVial(InternalConstants.GENE_HEALTH_DAMAGE_ID, ItemStack(ItemRegistry.WITHER_CORE.get()))
        registerGeneVial(InternalConstants.GENE_HOSTILE_ID, ItemStack(ItemRegistry.WOLF_TOOTH.get()))
        registerGeneVial(InternalConstants.GENE_SUNBURN_ID, ItemStack(ItemRegistry.BAT_WING.get()))
        registerGeneVial(InternalConstants.GENE_AGGRESSIVE_ID, ItemStack(ItemRegistry.ZOGLIN_SKULL.get()))
        registerGeneVial(InternalConstants.GENE_PEACEFUL_ID, ItemStack(Items.GOLDEN_CARROT))
        registerGeneVial(InternalConstants.GENE_GROUND_ID, ItemStack(Items.POISONOUS_POTATO))
        registerGeneVial(InternalConstants.GENE_ARROW_IMMUNITY_ID, ItemStack(Items.TURTLE_EGG))
        registerGeneVial(InternalConstants.GENE_FIRE_ID, ItemStack(Items.BLAZE_ROD))
        registerGeneVial(InternalConstants.GENE_RADIUS_ID, ItemStack(Items.SLIME_BALL))
        registerGeneVial(InternalConstants.GENE_MUTAGEN_ID, ItemStack(Items.BONE_MEAL))
        registerGeneVial(InternalConstants.GENE_UNDEAD_ID, ItemStack(Items.MILK_BUCKET))
        registerGeneVial(InternalConstants.GENE_BURN_ID, ItemStack(Items.FIRE_CORAL))
        registerGeneVial(InternalConstants.GENE_RANDOM_WORDS_ID, ItemStack(Items.PUFFERFISH))
        registerGeneVial(InternalConstants.GENE_SHADOW_ID, ItemStack(Items.ZOMBIE_HEAD))
        registerGeneVial(InternalConstants.GENE_INFINITY_ID, ItemStack(Items.DRAGON_BREATH))
    }

    fun loadDefaultBacteriaCures() {
        //todo add mappings here
        registerBacteriaCure(InternalConstants.BACTERIA_BLACK_ID, Triple(ItemStack(Items.POTION), ItemStack(BlockRegistry.SPIDER_EYE.get()), ItemStack(Items.SPIDER_EYE)))
        registerBacteriaCure(InternalConstants.BACTERIA_SWAMP_ID, Triple(ItemStack(Items.POTION), ItemStack(BlockRegistry.SNOW_WARDEN.get()), ItemStack(Blocks.LILY_PAD)))
        registerBacteriaCure(InternalConstants.BACTERIA_GLOWING_ID, Triple(ItemStack(Items.POTION), ItemStack(BlockRegistry.NETHER_AMBER.get()), ItemStack(ItemRegistry.WEIRD_SEEDS.get())))
        registerBacteriaCure(InternalConstants.BACTERIA_WATER_ID, Triple(ItemStack(Items.POTION), ItemStack(BlockRegistry.SPECTRAL_LILY.get()), ItemStack(Items.NAUTILUS_SHELL)))
        registerBacteriaCure(InternalConstants.BACTERIA_ENDER_ID, Triple(ItemStack(Items.POTION), ItemStack(BlockRegistry.ENDER_FLOWER.get()), ItemStack(Items.ENDER_EYE)))
        registerBacteriaCure(InternalConstants.BACTERIA_CACTUS_ID, Triple(ItemStack(Items.POTION), ItemStack(BlockRegistry.DESERT_BONE.get()), ItemStack(Blocks.CACTUS)))
        registerBacteriaCure(InternalConstants.BACTERIA_BONE_ID, Triple(ItemStack(Items.POTION), ItemStack(BlockRegistry.RED_FLOWER.get()), ItemStack(ItemRegistry.WOLF_TOOTH.get())))
        registerBacteriaCure(InternalConstants.BACTERIA_TERRA_ID, Triple(ItemStack(Items.POTION), ItemStack(BlockRegistry.CAVE_LANTERN.get()), ItemStack(Items.BEETROOT)))
        registerBacteriaCure(InternalConstants.BACTERIA_MYCELIUM_ID, Triple(ItemStack(Items.POTION), ItemStack(BlockRegistry.CREEPER_SOUL.get()), ItemStack(Blocks.MYCELIUM)))
        registerBacteriaCure(InternalConstants.BACTERIA_SEA_ID, Triple(ItemStack(Items.POTION), ItemStack(BlockRegistry.FIRE_LILY.get()), ItemStack(Items.TROPICAL_FISH)))
    }

    fun loadDefaultDrops() {
        //todo add mappings here
        registerDrop(EntityType.BAT, Pair(15, ItemStack(ItemRegistry.BAT_WING.get(), 1)))
        registerDrop(EntityType.CREEPER, Pair(25, ItemStack(ItemRegistry.CREEPER_HEART.get(), 1)))
        registerDrop(EntityType.BLAZE, Pair(15, ItemStack(ItemRegistry.BLAZE_CORE.get(), 1)))
        registerDrop(EntityType.DROWNED, Pair(20, ItemStack(ItemRegistry.DROWNED_TOOTH.get(), 1)))
        registerDrop(EntityType.CHICKEN, Pair(35, ItemStack(ItemRegistry.CHICKEN_HEAD.get(), 1)))
        registerDrop(EntityType.STRIDER, Pair(45, ItemStack(ItemRegistry.STRIDER_SKIN.get(), 1)))
        registerDrop(EntityType.SQUID, Pair(15, ItemStack(ItemRegistry.OCT_TENTACLE.get(), 1)))
        registerDrop(EntityType.ZOMBIE_HORSE, Pair(45, ItemStack(ItemRegistry.ZOMBIE_HORSE_BONE.get(), 1)))
        registerDrop(EntityType.PIGLIN, Pair(25, ItemStack(ItemRegistry.PIGLIN_FANG.get(), 1)))
        registerDrop(EntityType.ZOGLIN, Pair(80, ItemStack(ItemRegistry.ZOGLIN_SKULL.get(), 1)))
        registerDrop(EntityType.EVOKER, Pair(35, ItemStack(ItemRegistry.EVOKER_POTION.get(), 1)))
        registerDrop(EntityType.ENDERMAN, Pair(35, ItemStack(ItemRegistry.SPECTRAL_DUST.get(), 1)))
        registerDrop(EntityType.SPIDER, Pair(40, ItemStack(ItemRegistry.SPIDER_LEG.get(), 1)))
        registerDrop(EntityType.SKELETON, Pair(15, ItemStack(ItemRegistry.SKELETON_DUST.get(), 1)))
        registerDrop(EntityType.WITCH, Pair(25, ItemStack(ItemRegistry.WITCH_POTION.get(), 1)))
        registerDrop(EntityType.VEX, Pair(35, ItemStack(ItemRegistry.VEX_WING.get(), 1)))
        registerDrop(EntityType.PHANTOM, Pair(25, ItemStack(ItemRegistry.PHANTOM_TAIL.get(), 1)))
        registerDrop(EntityType.SHULKER, Pair(45, ItemStack(ItemRegistry.SHULKER_ESSENCE.get(), 1)))
        registerDrop(EntityType.HUSK, Pair(35, ItemStack(ItemRegistry.HUSK_BRAIN.get(), 1)))
        registerDrop(EntityType.WITHER_SKELETON, Pair(45, ItemStack(ItemRegistry.WITHER_CORE.get(), 1)))
        registerDrop(EntityType.VINDICATOR, Pair(35, ItemStack(ItemRegistry.WEIRD_SEEDS.get(), 1)))
    }

    fun registerEffectClass(id: Int, clazz: Class<out AbstractEffect>) {
        if(EFFECTS.containsKey(id))
            throw IllegalStateException("Effect with id $id is already registered!")
        EFFECTS[id] = clazz
    }

    fun registerGeneClass(id: Int, clazz: Class<out Gene>) {
        if(GENES.containsKey(id))
            throw IllegalStateException("Gene with id $id is already registered!")
        GENES[id] = clazz
    }

    fun registerEffectChance(id: Int, chance: Int) {
        if(EFFECT_CHANCES.containsKey(id))
            throw IllegalStateException("Effect chance with id $id is already registered!")
        EFFECT_CHANCES[id] = chance
    }

    fun registerEffectOccasion(entity: EntityType<*>, id: Int, chance: Int) {
        val data = EFFECT_OCCASIONS.getOrDefault(entity, mutableMapOf())
        data[id] = chance
        EFFECT_OCCASIONS[entity] = data
    }

    fun registerEffectOccasion(clazz: Class<out LivingEntity>, id: Int, chance: Int) {
        val data = EFFECT_OCCASIONS_CLASS.getOrDefault(clazz, mutableMapOf())
        data[id] = chance
        EFFECT_OCCASIONS_CLASS[clazz] = data
    }

    fun registerRandomVirusGene(id: Int, chance: Int) {
        if(RANDOM_VIRUS_GENES.containsKey(id))
            throw IllegalStateException("Gene chance with id $id is already registered!")
        RANDOM_VIRUS_GENES[id] = chance
    }

    fun registerGeneMutation(id: Int, mutations: List<Int>) {
        if(GENE_MUTATIONS.containsKey(id))
            throw IllegalStateException("Gene mutations with id $id is already registered!")
        GENE_MUTATIONS[id] = mutations
    }

    fun registerBacteriaCure(id: Int, cure: Triple<ItemStack, ItemStack, ItemStack>) {
        if(BACTERIA_CURES.containsKey(id))
            throw IllegalStateException("Bacteria cure with id $id is already registered!")
        BACTERIA_CURES[id] = cure
    }

    fun registerGeneVial(id: Int, vial: ItemStack) {
        if(GENE_VIALS.containsKey(id))
            throw IllegalStateException("Gene vial with id $id is already registered!")
        GENE_VIALS[id] = vial
    }

    fun registerAntibiotic(item: Item, list: MutableList<Int>) {
        if(ANTIBIOTICS.containsKey(item))
            throw IllegalStateException("Antibiotic is already registered!")
        ANTIBIOTICS[item] = list
    }

    fun registerDrop(entity: EntityType<*>, data: Pair<Int, ItemStack>) {
        DROPS.put(entity, data)
    }

    fun registerSymbiosis(data: Triple<Int, Int, Int>) {
        if(EFFECT_SYMBIOSIS.any { it.first == data.first && it.second == data.second })
            throw IllegalStateException("Specified input for symbiosis is already registered!")
        EFFECT_SYMBIOSIS.add(data)
    }

    fun getEffectClassById(id: Int) = EFFECTS.getOrDefault(id, DefaultEffect::class.java)
    fun getGeneClassById(id: Int) = GENES.getOrDefault(id, DefaultGene::class.java)
    fun getEffectChance(id: Int): Int {
        return if(OverrideHandler.CHANCES.containsKey(id))
            OverrideHandler.CHANCES[id]!!
        else EFFECT_CHANCES.getOrDefault(id, 0)
    }
    fun getGeneMutationsById(id: Int) = GENE_MUTATIONS.getOrDefault(id, null)
    fun getBacteriaCureById(id: Int) = BACTERIA_CURES.getOrDefault(id, Triple(ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY))
    fun getGeneVialByID(id: Int) = GENE_VIALS.getOrDefault(id, ItemStack.EMPTY)

    fun getEffects() = EFFECTS
    fun getGenes() = GENES
    fun getEffectChances() = EFFECT_CHANCES
    fun getGeneMutations() = GENE_MUTATIONS
    fun getGeneVials() = GENE_VIALS
    fun getSymbiosis() = EFFECT_SYMBIOSIS
    fun getOccasions() = EFFECT_OCCASIONS
    fun getOccasionsClass() = EFFECT_OCCASIONS_CLASS
    fun getBacteriaCures() = BACTERIA_CURES
    fun getRandomVirusGenes() = RANDOM_VIRUS_GENES
    fun getAntibiotics() = ANTIBIOTICS
    fun getDrops() = DROPS

    fun getEffectInstance(id: Int): AbstractEffect {
        if(EFFECT_INSTANCES.containsKey(id))
            return EFFECT_INSTANCES[id]!!
        val effect = getEffectClassById(id).newInstance()
        EFFECT_INSTANCES[id] = effect
        return effect
    }

    fun getGeneInstance(id: Int): Gene {
        if(GENE_INSTANCES.containsKey(id))
            return GENE_INSTANCES[id]!!
        val gene = getGeneClassById(id).newInstance()
        GENE_INSTANCES[id] = gene
        return gene
    }
}