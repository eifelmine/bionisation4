package com.eifel.bionisation4.api.jei.recipes

import net.minecraft.item.ItemStack

class CureStationRecipe(val input: Triple<ItemStack, ItemStack, ItemStack>, val output: ItemStack)
class DNAModifierRecipe(val input: ItemStack, val output: ItemStack)
class VaccineCreatorRecipe(val input: Pair<ItemStack, ItemStack>, val output: ItemStack)
class VirusReplicatorRecipe(val input: ItemStack, val output: ItemStack)