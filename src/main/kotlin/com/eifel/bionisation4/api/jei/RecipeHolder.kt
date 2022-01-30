package com.eifel.bionisation4.api.jei

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.jei.recipes.CureStationRecipe
import com.eifel.bionisation4.api.jei.recipes.DNAModifierRecipe
import com.eifel.bionisation4.api.jei.recipes.VaccineCreatorRecipe
import com.eifel.bionisation4.api.jei.recipes.VirusReplicatorRecipe
import com.eifel.bionisation4.api.laboratory.registry.EffectRegistry
import com.eifel.bionisation4.api.laboratory.util.EffectEntry
import com.eifel.bionisation4.common.item.ItemRegistry
import com.eifel.bionisation4.util.nbt.NBTUtils
import net.minecraft.item.ItemStack

object RecipeHolder {

    val CURE_STATION_RECIPES = mutableListOf<CureStationRecipe>()
    val VACCINE_CREATOR_RECIPES = mutableListOf<VaccineCreatorRecipe>()
    val DNA_MODIFIER_RECIPES = mutableListOf<DNAModifierRecipe>()
    val VIRUS_REPLICATOR_RECIPES = mutableListOf<VirusReplicatorRecipe>()

    fun initRecipes(){
        //cure station
        EffectRegistry.getBacteriaCures().forEach { (id, inputs) ->
            val ant = ItemStack(ItemRegistry.ANTIBIOTIC_VIAL.get())
            val data = NBTUtils.getNBT(ant)
            val effect = EffectRegistry.getEffectInstance(id)
            NBTUtils.objectsToNBT(data, mutableListOf(EffectEntry(effect.effectID, effect.effectName, mutableListOf())), InternalConstants.VIAL_BACTERIA)
            CURE_STATION_RECIPES.add(CureStationRecipe(Triple(inputs.first.copy(), inputs.second.copy(), inputs.third.copy()), ant))
        }
        //vaccine creator
        EffectRegistry.getGenes().forEach { (id, clazz) ->
            val injector = ItemStack(ItemRegistry.VACCINE_INJECTOR.get())
            val pattern = ItemStack(ItemRegistry.DNA_PATTERN.get())
            val gene = EffectRegistry.getGeneInstance(id)
            val patternData = NBTUtils.getNBT(pattern)
            NBTUtils.objectsToNBT(patternData, mutableListOf(EffectEntry(gene.id, gene.geneName, mutableListOf())), InternalConstants.PATTERN_GENES)
            val outInjector = injector.copy()
            val injectorData = NBTUtils.getNBT(outInjector)
            NBTUtils.objectsToNBT(injectorData, mutableListOf(EffectEntry(gene.id, gene.geneName, mutableListOf())), InternalConstants.VACCINE_INJECTOR_GENES)
            VACCINE_CREATOR_RECIPES.add(VaccineCreatorRecipe(Pair(pattern, injector), outInjector))
        }
        //dna modifier
        EffectRegistry.getGeneVials().forEach { (id, stack) ->
            val pattern = ItemStack(ItemRegistry.DNA_PATTERN.get())
            val gene = EffectRegistry.getGeneInstance(id)
            val patternData = NBTUtils.getNBT(pattern)
            NBTUtils.objectsToNBT(patternData, mutableListOf(EffectEntry(gene.id, gene.geneName, mutableListOf())), InternalConstants.PATTERN_GENES)
            DNA_MODIFIER_RECIPES.add(DNAModifierRecipe(stack.copy(), pattern))
        }
        //virus replicator
        EffectRegistry.getGenes().forEach { (id, clazz) ->
            val pattern = ItemStack(ItemRegistry.DNA_PATTERN.get())
            val gene = EffectRegistry.getGeneInstance(id)
            val patternData = NBTUtils.getNBT(pattern)
            NBTUtils.objectsToNBT(patternData, mutableListOf(EffectEntry(gene.id, gene.geneName, mutableListOf())), InternalConstants.PATTERN_GENES)
            val spreader = ItemStack(ItemRegistry.VIRUS_SPREADER.get())
            val spreaderData = NBTUtils.getNBT(spreader)
            NBTUtils.objectsToNBT(spreaderData, mutableListOf(EffectEntry(gene.id, gene.geneName, mutableListOf())), InternalConstants.SPREADER_GENES)
            VIRUS_REPLICATOR_RECIPES.add(VirusReplicatorRecipe(pattern, spreader))
        }
    }
}