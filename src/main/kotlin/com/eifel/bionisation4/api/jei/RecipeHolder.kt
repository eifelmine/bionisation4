package com.eifel.bionisation4.api.jei

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.jei.recipes.CureStationRecipe
import com.eifel.bionisation4.api.laboratory.registry.EffectRegistry
import com.eifel.bionisation4.api.laboratory.util.EffectEntry
import com.eifel.bionisation4.common.item.ItemRegistry
import com.eifel.bionisation4.util.nbt.NBTUtils
import net.minecraft.item.ItemStack

object RecipeHolder {

    val CURE_STATION_RECIPES = mutableListOf<CureStationRecipe>()

    fun initRecipes(){
        //cure station
        EffectRegistry.getBacteriaCures().forEach { id, inputs ->
            val ant = ItemStack(ItemRegistry.ANTIBIOTIC_VIAL.get())
            val data = NBTUtils.getNBT(ant)
            val effect = EffectRegistry.getEffectInstance(id)
            NBTUtils.objectsToNBT(data, mutableListOf(EffectEntry(effect.effectID, effect.effectName, mutableListOf())), InternalConstants.VIAL_BACTERIA)
            CURE_STATION_RECIPES.add(CureStationRecipe(inputs, ant))
        }
    }
}