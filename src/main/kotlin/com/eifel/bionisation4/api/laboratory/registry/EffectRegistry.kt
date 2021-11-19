package com.eifel.bionisation4.api.laboratory.registry

import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.util.IGene
import com.eifel.bionisation4.common.laboratory.common.DefaultEffect
import com.eifel.bionisation4.common.laboratory.gene.DefaultGene
import net.minecraft.item.ItemStack

object EffectRegistry {

    private val EFFECTS = mutableMapOf<Int, Class<out AbstractEffect>>()
    private val GENES = mutableMapOf<Int, Class<out IGene>>()

    private val GENE_VIALS = mutableMapOf<Int, ItemStack>()
    private val BACTERIA_CURES = mutableMapOf<Int, Triple<ItemStack, ItemStack, ItemStack>>()

    fun loadDefaultGenes() {
        //todo add mappings here
    }

    fun loadDefaultEffects() {
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

    fun registerGeneClass(id: Int, clazz: Class<out IGene>) {
        if(GENES.containsKey(id))
            throw IllegalStateException("Gene with id $id is already registered!")
        GENES[id] = clazz
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

    fun getEffectClassById(id: Int) = EFFECTS.getOrDefault(id, DefaultEffect::class.java)
    fun getGeneClassById(id: Int) = GENES.getOrDefault(id, DefaultGene::class.java)
    fun getBacteriaCureById(id: Int) = BACTERIA_CURES.getOrDefault(id, Triple(ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY))
    fun getGeneVialByID(id: Int) = GENE_VIALS.getOrDefault(id, ItemStack.EMPTY)
}