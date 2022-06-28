package com.eifel.bionisation4.common.item.lab

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.registry.EffectRegistry
import com.eifel.bionisation4.api.laboratory.util.EffectEntry
import com.eifel.bionisation4.common.config.ConfigProperties
import com.eifel.bionisation4.common.extensions.addEffect
import com.eifel.bionisation4.common.item.CommonItem
import com.eifel.bionisation4.common.laboratory.virus.Custom
import com.eifel.bionisation4.util.lab.EffectUtils
import com.eifel.bionisation4.util.nbt.NBTUtils
import com.eifel.bionisation4.util.translation.TranslationUtils
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.Component
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Rarity
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.Level
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

class VirusSpreader(): CommonItem(desc = listOf(Triple("virus_spreader", "usage", "desc")), rarity = Rarity.RARE, size = 1) {

    override fun use(world: Level, player: Player, hand: InteractionHand): InteractionResultHolder<ItemStack> {
        val stack = player.getItemInHand(hand)
        if(!player.level.isClientSide) {
            val data = NBTUtils.getNBT(stack)
            val genes = mutableListOf<EffectEntry>()
            NBTUtils.nbtToObjects(data, genes, InternalConstants.SPREADER_GENES, EffectEntry::class.java)
            if(genes.isNotEmpty()) {
                val effect = Custom().apply {
                    loadProperties(genes.map { EffectRegistry.getGeneInstance(it.id).getCopy() })
                }
                if (player.isShiftKeyDown)
                    EffectUtils.spreadEffect(effect, player.level, player.blockPosition(), ConfigProperties.vialSpreadRadius.get())
                else
                    player.addEffect(effect)
            }
            data.remove(InternalConstants.SPREADER_GENES)
            player.sendSystemMessage(TranslationUtils.getCommonTranslation("virus_spreader", "usage", "spread"))
        }
        return InteractionResultHolder.pass(stack)
    }

    @OnlyIn(Dist.CLIENT)
    override fun appendHoverText(stack: ItemStack, world: Level?, info: MutableList<Component>, flag: TooltipFlag) {
        val data = NBTUtils.getNBT(stack)
        var noGenes = true
        if(data.contains(InternalConstants.SPREADER_GENES)) {
            val genes = mutableListOf<EffectEntry>()
            NBTUtils.nbtToObjects(data, genes, InternalConstants.SPREADER_GENES, EffectEntry::class.java)
            if(genes.isNotEmpty()) {
                noGenes = false
                info.add(TranslationUtils.getText(" "))
                info.add(TranslationUtils.getTranslatedTextComponent("virus_spreader", "info", "genes"))
                genes.forEach { gene ->
                    info.add(TranslationUtils.getText("    ${ChatFormatting.GRAY}-${ChatFormatting.YELLOW} ${TranslationUtils.getTranslatedText("gene", gene.unlocName, "name")}"))
                }
            }
        }
        if(noGenes){
            info.add(TranslationUtils.getText(" "))
            info.add(TranslationUtils.getTranslatedTextComponent("virus_spreader", "info", "no_genes"))
        }
        super.appendHoverText(stack, world, info, flag)
    }
}