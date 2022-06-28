package com.eifel.bionisation4.common.item.lab

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.util.EffectEntry
import com.eifel.bionisation4.common.extensions.addEffect
import com.eifel.bionisation4.common.item.CommonItem
import com.eifel.bionisation4.common.laboratory.treat.Vaccine
import com.eifel.bionisation4.util.nbt.NBTUtils
import com.eifel.bionisation4.util.translation.TranslationUtils
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.Component
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Rarity
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.Level
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

class VaccineInjector(): CommonItem(desc = listOf(Triple("vaccine_injector", "usage", "desc")), rarity = Rarity.RARE, size = 1) {

    override fun use(world: Level, player: Player, hand: InteractionHand): InteractionResultHolder<ItemStack> {
        val stack = player.getItemInHand(hand)
        if(!player.level.isClientSide && !player.isShiftKeyDown) {
            compareGenes(stack, player)
            player.sendSystemMessage(TranslationUtils.getCommonTranslation("vaccine_injector", "usage", "injected"))
        }
        return InteractionResultHolder.pass(stack)
    }

    override fun interactLivingEntity(stack: ItemStack, player: Player, target: LivingEntity, hand: InteractionHand): InteractionResult {
        val stack = player.getItemInHand(hand)
        if(!player.level.isClientSide && player.isShiftKeyDown) {
            target?.let { entity ->
                compareGenes(stack, entity)
                player.sendSystemMessage(TranslationUtils.getCommonTranslation("vaccine_injector", "usage", "injected"))
            }
        }
        return InteractionResult.SUCCESS
    }

    private fun compareGenes(injector: ItemStack, entity: LivingEntity){
        val data = NBTUtils.getNBT(injector)
        if(data.contains(InternalConstants.VACCINE_INJECTOR_GENES)) {
            val genes = mutableListOf<EffectEntry>()
            NBTUtils.nbtToObjects(data, genes, InternalConstants.VACCINE_INJECTOR_GENES, EffectEntry::class.java)
            if(genes.isNotEmpty()) {
                entity.addEffect(Vaccine().setExpirationData(genes))
                data.remove(InternalConstants.VACCINE_INJECTOR_GENES)
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    override fun appendHoverText(stack: ItemStack, world: Level?, info: MutableList<Component>, flag: TooltipFlag) {
        val data = NBTUtils.getNBT(stack)
        var noGenes = true
        if(data.contains(InternalConstants.VACCINE_INJECTOR_GENES)) {
            val genes = mutableListOf<EffectEntry>()
            NBTUtils.nbtToObjects(data, genes, InternalConstants.VACCINE_INJECTOR_GENES, EffectEntry::class.java)
            if(genes.isNotEmpty()) {
                noGenes = false
                info.add(TranslationUtils.getText(" "))
                info.add(TranslationUtils.getTranslatedTextComponent("vaccine_injector", "info", "genes"))
                genes.forEach { gene ->
                    info.add(TranslationUtils.getText("    ${ChatFormatting.GRAY}-${ChatFormatting.YELLOW} ${TranslationUtils.getTranslatedText("gene", gene.unlocName, "name")}"))
                }
            }
        }
        if(noGenes){
            info.add(TranslationUtils.getText(" "))
            info.add(TranslationUtils.getTranslatedTextComponent("vaccine_injector", "info", "no_genes"))
        }
        super.appendHoverText(stack, world, info, flag)
    }
}