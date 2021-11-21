package com.eifel.bionisation4.api.laboratory.registry

import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.species.Gene
import com.eifel.bionisation4.common.laboratory.common.DefaultEffect
import com.eifel.bionisation4.common.laboratory.common.DefaultStateEffect
import com.eifel.bionisation4.common.laboratory.gene.DefaultGene
import net.minecraft.item.ItemStack

object EffectRegistry {

    private val EFFECTS = mutableMapOf<Int, Class<out AbstractEffect>>()
    private val GENES = mutableMapOf<Int, Class<out Gene>>()

    private val EFFECT_INSTANCES = mutableMapOf<Int, AbstractEffect>()
    private val GENE_INSTANCES = mutableMapOf<Int, Gene>()

    //list of gene ids it can mutate with
    private val GENE_MUTATIONS = mutableMapOf<Int, List<Int>>()

    //two effects for mutation and result
    private val EFFECT_SYMBIOSIS = mutableListOf<Triple<Int, Int, Int>>()

    private val GENE_VIALS = mutableMapOf<Int, ItemStack>()
    //list of 3 stacks used in Cure Station
    private val BACTERIA_CURES = mutableMapOf<Int, Triple<ItemStack, ItemStack, ItemStack>>()

    fun loadDefaultGenes() {
        registerGeneClass(0, DefaultGene::class.java)
    }

    fun loadDefaultGeneMutations() {
        //todo add mappings here
    }

    fun loadDefaultEffects() {
        registerEffectClass(0, DefaultEffect::class.java)
        registerEffectClass(1, DefaultStateEffect::class.java)
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
    fun getGeneMutationsById(id: Int) = GENE_MUTATIONS.getOrDefault(id, null)
    fun getBacteriaCureById(id: Int) = BACTERIA_CURES.getOrDefault(id, Triple(ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY))
    fun getGeneVialByID(id: Int) = GENE_VIALS.getOrDefault(id, ItemStack.EMPTY)

    fun getEffects() = EFFECTS
    fun getGenes() = GENES
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