package com.eifel.bionisation4.api.laboratory.registry

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.species.Gene
import com.eifel.bionisation4.common.laboratory.common.DefaultEffect
import com.eifel.bionisation4.common.laboratory.common.DefaultStateEffect
import com.eifel.bionisation4.common.laboratory.common.effect.*
import com.eifel.bionisation4.common.laboratory.gene.DefaultGene
import com.eifel.bionisation4.common.laboratory.gene.species.potion.*
import com.eifel.bionisation4.common.laboratory.gene.species.type.*
import net.minecraft.item.ItemStack

object EffectRegistry {

    private val EFFECTS = mutableMapOf<Int, Class<out AbstractEffect>>()
    private val GENES = mutableMapOf<Int, Class<out Gene>>()

    private val EFFECT_INSTANCES = mutableMapOf<Int, AbstractEffect>()
    private val EFFECT_CHANCES = mutableMapOf<Int, Int>()
    private val GENE_INSTANCES = mutableMapOf<Int, Gene>()

    //list of gene ids it can mutate with
    private val GENE_MUTATIONS = mutableMapOf<Int, List<Int>>()

    //two effects for mutation and result
    private val EFFECT_SYMBIOSIS = mutableListOf<Triple<Int, Int, Int>>()

    private val GENE_VIALS = mutableMapOf<Int, ItemStack>()
    //list of 3 stacks used in Cure Station
    private val BACTERIA_CURES = mutableMapOf<Int, Triple<ItemStack, ItemStack, ItemStack>>()

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
        registerGeneClass(InternalConstants.GENE_CLONE_ID, Clone::class.java)
        registerGeneClass(InternalConstants.GENE_AIR_SPREAD_ID, AirSpread::class.java)
        registerGeneClass(InternalConstants.GENE_ATTACK_SPREAD_ID, AttackSpread::class.java)
        registerGeneClass(InternalConstants.GENE_END_ID, End::class.java)
        registerGeneClass(InternalConstants.GENE_RANDOM_TELEPORT_ID, RandomTeleport::class.java)
        registerGeneClass(InternalConstants.GENE_WATER_FEAR_ID, WaterFear::class.java)
        registerGeneClass(InternalConstants.GENE_HEALTH_DAMAGE_ID, HealthDamage::class.java)
        registerGeneClass(InternalConstants.GENE_HOSTILE_ID, Hostile::class.java)
        registerGeneClass(InternalConstants.GENE_SUNBURN_ID, Sunburn::class.java)
        registerGeneClass(InternalConstants.GENE_AGGRESSIVE_ID, Aggressive::class.java)


    }

    fun loadDefaultGeneMutations() {
        //todo add mappings here
    }

    fun loadDefaultEffects() {
        //todo add mappings here
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
    }

    fun loadDefaultSymbiosis() {
        //todo add mappings here
    }

    fun loadDefaultGeneVials() {
        //todo add mappings here
    }

    fun loadDefaultBacteriaCures() {
        //todo add mappings here
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

    fun registerSymbiosis(data: Triple<Int, Int, Int>) {
        if(EFFECT_SYMBIOSIS.any { it.first == data.first && it.second == data.second })
            throw IllegalStateException("Specified input for symbiosis is already registered!")
        EFFECT_SYMBIOSIS.add(data)
    }

    fun getEffectClassById(id: Int) = EFFECTS.getOrDefault(id, DefaultEffect::class.java)
    fun getGeneClassById(id: Int) = GENES.getOrDefault(id, DefaultGene::class.java)
    fun getEffectChance(id: Int) = EFFECT_CHANCES.getOrDefault(id, 0)
    fun getGeneMutationsById(id: Int) = GENE_MUTATIONS.getOrDefault(id, null)
    fun getBacteriaCureById(id: Int) = BACTERIA_CURES.getOrDefault(id, Triple(ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY))
    fun getGeneVialByID(id: Int) = GENE_VIALS.getOrDefault(id, ItemStack.EMPTY)

    fun getEffects() = EFFECTS
    fun getGenes() = GENES
    fun getEffectChances() = EFFECT_CHANCES
    fun getGeneMutations() = GENE_MUTATIONS
    fun getGeneVials() = GENE_VIALS
    fun getSymbiosis() = EFFECT_SYMBIOSIS
    fun getBacteriaCures() = BACTERIA_CURES

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