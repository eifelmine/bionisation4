package com.eifel.bionisation4.common.item.lab

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.util.EffectEntry
import com.eifel.bionisation4.common.item.CommonItem
import com.eifel.bionisation4.util.nbt.NBTUtils
import com.eifel.bionisation4.util.translation.TranslationUtils
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.item.ItemStack
import net.minecraft.item.Rarity
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.TextFormatting
import net.minecraft.world.World
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

class DNAPattern(): CommonItem(desc = listOf(Triple("dna_pattern", "usage", "desc")), rarity = Rarity.RARE, size = 1) {

    @OnlyIn(Dist.CLIENT)
    override fun appendHoverText(stack: ItemStack, world: World?, info: MutableList<ITextComponent>, flag: ITooltipFlag) {
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
                    info.add(TranslationUtils.getText("    ${TextFormatting.GRAY}-${TextFormatting.YELLOW} ${TranslationUtils.getTranslatedText("gene", gene.unlocName, "name")}"))
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