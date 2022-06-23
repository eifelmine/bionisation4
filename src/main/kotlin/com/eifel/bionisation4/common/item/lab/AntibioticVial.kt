package com.eifel.bionisation4.common.item.lab

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.registry.EffectRegistry
import com.eifel.bionisation4.api.laboratory.util.EffectEntry
import com.eifel.bionisation4.common.core.BionisationTab
import com.eifel.bionisation4.common.extensions.addEffect
import com.eifel.bionisation4.common.item.CommonItem
import com.eifel.bionisation4.common.item.ItemRegistry
import com.eifel.bionisation4.common.laboratory.treat.Antibiotic
import com.eifel.bionisation4.util.nbt.NBTUtils
import com.eifel.bionisation4.util.translation.TranslationUtils
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.item.Rarity
import net.minecraft.util.ActionResult
import net.minecraft.util.ActionResultType
import net.minecraft.util.Hand
import net.minecraft.util.NonNullList
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.TextFormatting
import net.minecraft.world.World
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

class AntibioticVial(): CommonItem(desc = listOf(Triple("antibiotic_vial", "usage", "desc")), rarity = Rarity.RARE, size = 1) {

    override fun fillItemCategory(group: ItemGroup, list: NonNullList<ItemStack>) {
        if(group == BionisationTab.BIONISATION_TAB) {
            EffectRegistry.getBacteriaCures().forEach { (id, inputs) ->
                val ant = ItemStack(ItemRegistry.ANTIBIOTIC_VIAL.get())
                val data = NBTUtils.getNBT(ant)
                val effect = EffectRegistry.getEffectInstance(id)
                NBTUtils.objectsToNBT(data, mutableListOf(EffectEntry(effect.effectID, effect.effectName, mutableListOf())), InternalConstants.VIAL_BACTERIA)
                list.add(ant)
            }
        }
    }

    override fun use(world: World, player: PlayerEntity, hand: Hand): ActionResult<ItemStack> {
        val stack = player.getItemInHand(hand)
        if(!player.level.isClientSide && !player.isShiftKeyDown) {
            addBacteriaCure(stack, player)
            player.sendMessage(TranslationUtils.getCommonTranslation("antibiotic_vial", "usage", "injected"), player.uuid)
        }
        return ActionResult.pass(stack)
    }

    override fun interactLivingEntity(stack: ItemStack, player: PlayerEntity, target: LivingEntity, hand: Hand): ActionResultType {
        val stack = player.getItemInHand(hand)
        if(!player.level.isClientSide && player.isShiftKeyDown) {
            target?.let { entity ->
                addBacteriaCure(stack, entity)
                player.sendMessage(TranslationUtils.getCommonTranslation("antibiotic_vial", "usage", "injected"), player.uuid)
            }
        }
        return ActionResultType.SUCCESS
    }

    private fun addBacteriaCure(injector: ItemStack, entity: LivingEntity){
        val data = NBTUtils.getNBT(injector)
        if(data.contains(InternalConstants.VIAL_BACTERIA)) {
            val bacteria = mutableListOf<EffectEntry>()
            NBTUtils.nbtToObjects(data, bacteria, InternalConstants.VIAL_BACTERIA, EffectEntry::class.java)
            if(bacteria.isNotEmpty()) {
                entity.addEffect(Antibiotic().setExpirationIds(bacteria.map { it.id }))
                data.remove(InternalConstants.VIAL_BACTERIA)
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    override fun appendHoverText(stack: ItemStack, world: World?, info: MutableList<ITextComponent>, flag: ITooltipFlag) {
        val data = NBTUtils.getNBT(stack)
        var noGenes = true
        if(data.contains(InternalConstants.VIAL_BACTERIA)) {
            val bacteria = mutableListOf<EffectEntry>()
            NBTUtils.nbtToObjects(data, bacteria, InternalConstants.VIAL_BACTERIA, EffectEntry::class.java)
            if(bacteria.isNotEmpty()) {
                noGenes = false
                info.add(TranslationUtils.getText(" "))
                info.add(TranslationUtils.getTranslatedTextComponent("antibiotic_vial", "info", "bacteria"))
                bacteria.forEach { bact ->
                    info.add(TranslationUtils.getText("    ${TextFormatting.GRAY}-${TextFormatting.YELLOW} ${TranslationUtils.getTranslatedText("effect", bact.unlocName, "name")}"))
                }
            }
        }
        if(noGenes){
            info.add(TranslationUtils.getText(" "))
            info.add(TranslationUtils.getTranslatedTextComponent("antibiotic_vial", "info", "no_bacteria"))
        }
        super.appendHoverText(stack, world, info, flag)
    }
}