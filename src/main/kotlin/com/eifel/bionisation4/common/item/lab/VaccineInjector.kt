package com.eifel.bionisation4.common.item.lab

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.util.EffectEntry
import com.eifel.bionisation4.common.extensions.addEffect
import com.eifel.bionisation4.common.item.CommonItem
import com.eifel.bionisation4.common.laboratory.treat.Vaccine
import com.eifel.bionisation4.util.nbt.NBTUtils
import com.eifel.bionisation4.util.translation.TranslationUtils
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.item.Rarity
import net.minecraft.util.ActionResult
import net.minecraft.util.ActionResultType
import net.minecraft.util.Hand
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.TextFormatting
import net.minecraft.world.World
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

class VaccineInjector(): CommonItem(desc = listOf(Triple("vaccine_injector", "usage", "desc")), rarity = Rarity.RARE) {

    override fun use(world: World, player: PlayerEntity, hand: Hand): ActionResult<ItemStack> {
        val stack = player.getItemInHand(hand)
        if(!player.level.isClientSide && !player.isShiftKeyDown) {
            compareGenes(stack, player)
            player.sendMessage(TranslationUtils.getText("${TextFormatting.GOLD}${TranslationUtils.getTranslatedText("vaccine_injector", "usage", "injected")}"), null)
        }
        return ActionResult.pass(stack)
    }

    override fun interactLivingEntity(stack: ItemStack, player: PlayerEntity, target: LivingEntity, hand: Hand): ActionResultType {
        val stack = player.getItemInHand(hand)
        if(!player.level.isClientSide && player.isShiftKeyDown) {
            target?.let { entity ->
                compareGenes(stack, entity)
                player.sendMessage(TranslationUtils.getText("${TextFormatting.GOLD}${TranslationUtils.getTranslatedText("vaccine_injector", "usage", "injected")}"), null)
            }
        }
        return ActionResultType.SUCCESS
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
    override fun appendHoverText(stack: ItemStack, world: World?, info: MutableList<ITextComponent>, flag: ITooltipFlag) {
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
                    info.add(TranslationUtils.getText("    ${TextFormatting.GRAY}-${TextFormatting.YELLOW} ${TranslationUtils.getTranslatedText("gene", gene.unlocName, "name")}"))
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