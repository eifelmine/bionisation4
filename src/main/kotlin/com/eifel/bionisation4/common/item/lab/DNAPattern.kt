package com.eifel.bionisation4.common.item.lab

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.registry.EffectRegistry
import com.eifel.bionisation4.api.laboratory.util.EffectEntry
import com.eifel.bionisation4.common.core.BionisationTab
import com.eifel.bionisation4.common.item.CommonItem
import com.eifel.bionisation4.common.item.ItemRegistry
import com.eifel.bionisation4.util.nbt.NBTUtils
import com.eifel.bionisation4.util.translation.TranslationUtils
import net.minecraft.ChatFormatting
import net.minecraft.core.NonNullList
import net.minecraft.network.chat.Component
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Rarity
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.Level
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

class DNAPattern(): CommonItem(desc = listOf(Triple("dna_pattern", "usage", "desc")), rarity = Rarity.RARE, size = 1) {

    override fun fillItemCategory(group: CreativeModeTab, list: NonNullList<ItemStack>) {
        if(group == BionisationTab.BIONISATION_TAB) {
            EffectRegistry.getGenes().forEach { (id, clazz) ->
                val pattern = ItemStack(ItemRegistry.DNA_PATTERN.get())
                val gene = EffectRegistry.getGeneInstance(id)
                val patternData = NBTUtils.getNBT(pattern)
                NBTUtils.objectsToNBT(patternData, mutableListOf(EffectEntry(gene.id, gene.geneName, mutableListOf())), InternalConstants.PATTERN_GENES)
                list.add(pattern)
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    override fun appendHoverText(stack: ItemStack, world: Level?, info: MutableList<Component>, flag: TooltipFlag) {
        val data = NBTUtils.getNBT(stack)
        var noGenes = true
        if(data.contains(InternalConstants.PATTERN_GENES)) {
            val genes = mutableListOf<EffectEntry>()
            NBTUtils.nbtToObjects(data, genes, InternalConstants.PATTERN_GENES, EffectEntry::class.java)
            if(genes.isNotEmpty()) {
                noGenes = false
                info.add(TranslationUtils.getText(" "))
                info.add(TranslationUtils.getTranslatedTextComponent("dna_pattern", "info", "genes"))
                genes.forEach { gene ->
                    info.add(TranslationUtils.getText("    ${ChatFormatting.GRAY}-${ChatFormatting.YELLOW} ${TranslationUtils.getTranslatedText("gene", gene.unlocName, "name")}"))
                }
            }
        }
        if(noGenes){
            info.add(TranslationUtils.getText(" "))
            info.add(TranslationUtils.getTranslatedTextComponent("dna_pattern", "info", "no_genes"))
        }
        super.appendHoverText(stack, world, info, flag)
    }
}