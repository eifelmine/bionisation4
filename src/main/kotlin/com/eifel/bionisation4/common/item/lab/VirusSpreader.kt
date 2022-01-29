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
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.item.Rarity
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.TextFormatting
import net.minecraft.world.World
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

class VirusSpreader(): CommonItem(desc = listOf(Triple("virus_spreader", "usage", "desc")), rarity = Rarity.RARE, size = 1) {

    override fun use(world: World, player: PlayerEntity, hand: Hand): ActionResult<ItemStack> {
        val stack = player.getItemInHand(hand)
        if(!player.level.isClientSide) {
            val data = NBTUtils.getNBT(stack)
            val genes = mutableListOf<EffectEntry>()
            NBTUtils.nbtToObjects(data, genes, InternalConstants.SPREADER_GENES, EffectEntry::class.java)
            val effect = Custom()
            effect.loadProperties(genes.map { EffectRegistry.getGeneInstance(it.id).getCopy() })
            if (player.isShiftKeyDown)
                EffectUtils.spreadEffect(effect, player.level, player.blockPosition(), ConfigProperties.vialSpreadRadius.get())
            else
                player.addEffect(effect)
            data.remove(InternalConstants.SPREADER_GENES)
            player.sendMessage(TranslationUtils.getText("${TextFormatting.AQUA}${TranslationUtils.getTranslatedText("virus_spreader", "usage", "spread")}"), null)
        }
        return ActionResult.pass(stack)
    }

    @OnlyIn(Dist.CLIENT)
    override fun appendHoverText(stack: ItemStack, world: World?, info: MutableList<ITextComponent>, flag: ITooltipFlag) {
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
                    info.add(TranslationUtils.getText("    ${TextFormatting.GRAY}-${TextFormatting.YELLOW} ${TranslationUtils.getTranslatedText("gene", gene.unlocName, "name")}"))
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