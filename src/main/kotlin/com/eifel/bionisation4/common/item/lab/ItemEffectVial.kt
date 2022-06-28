package com.eifel.bionisation4.common.item.lab

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.registry.EffectRegistry
import com.eifel.bionisation4.common.config.ConfigProperties
import com.eifel.bionisation4.common.config.OverrideHandler
import com.eifel.bionisation4.common.core.BionisationTab
import com.eifel.bionisation4.common.extensions.addEffect
import com.eifel.bionisation4.common.item.CommonItem
import com.eifel.bionisation4.util.lab.EffectUtils
import com.eifel.bionisation4.util.nbt.NBTUtils
import com.eifel.bionisation4.util.translation.TranslationUtils
import net.minecraft.ChatFormatting
import net.minecraft.core.NonNullList
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.Component
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Rarity
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.Level
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

class ItemEffectVial(): CommonItem(rarity = Rarity.EPIC, size = 1) {

    override fun fillItemCategory(group: CreativeModeTab, list: NonNullList<ItemStack>) {
        if(group == BionisationTab.BIONISATION_TAB) {
            EffectRegistry.getEffects().keys.forEach { key ->
                val nbt = CompoundTag()
                nbt.putInt(InternalConstants.VIAL_EFFECT_KEY, key)
                val stack = ItemStack(this)
                stack.tag = nbt
                list.add(stack)
            }
        }
    }

    override fun use(world: Level, player: Player, hand: InteractionHand): InteractionResultHolder<ItemStack> {
        val stack = player.getItemInHand(hand)
        if(!player.level.isClientSide) {
            (stack.item as? ItemEffectVial)?.let { _ ->
                val nbt = NBTUtils.getNBT(stack)
                val id = nbt.getInt(InternalConstants.VIAL_EFFECT_KEY)
                val effect = EffectRegistry.getMobEffectInstance(id).getCopy()
                if(player.isShiftKeyDown)
                    EffectUtils.spreadEffect(effect, player.level, player.blockPosition(), ConfigProperties.vialSpreadRadius.get())
                else
                    player.addEffect(effect)
                player.sendSystemMessage(TranslationUtils.getCommonTranslation("vial", "usage", "spread"))
            }
        }
        return InteractionResultHolder.pass(stack)
    }

    @OnlyIn(Dist.CLIENT)
    override fun appendHoverText(
        stack: ItemStack,
        world: Level?,
        info: MutableList<Component>,
        flag: TooltipFlag
    ) {
        super.appendHoverText(stack, world, info, flag)

        val nbt = NBTUtils.getNBT(stack)
        val id = nbt.getInt(InternalConstants.VIAL_EFFECT_KEY)

        val effect = EffectRegistry.getMobEffectInstance(id)

        info.add(TranslationUtils.getText(" "))
        info.add(TranslationUtils.getText( "${ChatFormatting.GRAY}${TranslationUtils.getTranslatedText("vial", "effect", "name")} ${ChatFormatting.GREEN}${effect.getTranslationName()}"))
        info.add(TranslationUtils.getText( "${ChatFormatting.GRAY}${TranslationUtils.getTranslatedText("vial", "info", "type")} ${ChatFormatting.DARK_AQUA}${effect.effectType.getTranslatedName()}"))
        val genes = effect.effectGenes
        if(genes.isNotEmpty()) {
            info.add(TranslationUtils.getText("${ChatFormatting.GRAY}${TranslationUtils.getTranslatedText("vial", "info", "genes")}"))
            genes.forEach {
                info.add(TranslationUtils.getText("    ${ChatFormatting.GRAY}-${ChatFormatting.YELLOW} ${it.getTranslationName()} ${if(OverrideHandler.DISABLED_GENES.contains(it.geneName)) TranslationUtils.getTranslatedText("override", "info", "gene_disabled") else "" }"))
            }
        }
        if(OverrideHandler.DISABLED_EFFECTS.contains(effect.effectName)) {
            info.add(TranslationUtils.getText(" "))
            info.add(TranslationUtils.getText(TranslationUtils.getTranslatedText("override", "info", "eff_disabled")))
        }
        info.add(TranslationUtils.getText(" "))
        info.add(TranslationUtils.getText("${ChatFormatting.GOLD}${TranslationUtils.getTranslatedText("vial", "usage", "desc")}"))
    }
}