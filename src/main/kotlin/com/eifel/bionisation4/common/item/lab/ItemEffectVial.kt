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
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.item.Rarity
import net.minecraft.nbt.CompoundNBT
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.NonNullList
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.TextFormatting
import net.minecraft.world.World
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

class ItemEffectVial(): CommonItem(rarity = Rarity.EPIC, size = 1) {

    override fun fillItemCategory(group: ItemGroup, list: NonNullList<ItemStack>) {
        if(group == BionisationTab.BIONISATION_TAB) {
            EffectRegistry.getEffects().keys.forEach { key ->
                val nbt = CompoundNBT()
                nbt.putInt(InternalConstants.VIAL_EFFECT_KEY, key)
                val stack = ItemStack(this)
                stack.tag = nbt
                list.add(stack)
            }
        }
    }

    override fun use(world: World, player: PlayerEntity, hand: Hand): ActionResult<ItemStack> {
        val stack = player.getItemInHand(hand)
        if(!player.level.isClientSide) {
            (stack.item as? ItemEffectVial)?.let { _ ->
                val nbt = NBTUtils.getNBT(stack)
                val id = nbt.getInt(InternalConstants.VIAL_EFFECT_KEY)
                val effect = EffectRegistry.getEffectInstance(id).getCopy()
                if(player.isShiftKeyDown)
                    EffectUtils.spreadEffect(effect, player.level, player.blockPosition(), ConfigProperties.vialSpreadRadius.get())
                else
                    player.addEffect(effect)
                player.sendMessage(TranslationUtils.getText("${TextFormatting.AQUA}${TranslationUtils.getTranslatedText("vial", "usage", "spread")}"), null)
            }
        }
        return ActionResult.pass(stack)
    }

    @OnlyIn(Dist.CLIENT)
    override fun appendHoverText(
        stack: ItemStack,
        world: World?,
        info: MutableList<ITextComponent>,
        flag: ITooltipFlag
    ) {
        super.appendHoverText(stack, world, info, flag)

        val nbt = NBTUtils.getNBT(stack)
        val id = nbt.getInt(InternalConstants.VIAL_EFFECT_KEY)

        val effect = EffectRegistry.getEffectInstance(id)

        info.add(TranslationUtils.getText(" "))
        info.add(TranslationUtils.getText( "${TextFormatting.GRAY}${TranslationUtils.getTranslatedText("vial", "effect", "name")} ${TextFormatting.GREEN}${effect.getTranslationName()}"))
        info.add(TranslationUtils.getText( "${TextFormatting.GRAY}${TranslationUtils.getTranslatedText("vial", "info", "type")} ${TextFormatting.DARK_AQUA}${effect.effectType.getTranslatedName()}"))
        val genes = effect.effectGenes
        if(genes.isNotEmpty()) {
            info.add(TranslationUtils.getText("${TextFormatting.GRAY}${TranslationUtils.getTranslatedText("vial", "info", "genes")}"))
            genes.forEach {
                info.add(TranslationUtils.getText("    ${TextFormatting.GRAY}-${TextFormatting.YELLOW} ${it.getTranslationName()} ${if(OverrideHandler.DISABLED_GENES.contains(it.geneName)) TranslationUtils.getTranslatedText("override", "info", "gene_disabled") else "" }"))
            }
        }
        if(OverrideHandler.DISABLED_EFFECTS.contains(effect.effectName)) {
            info.add(TranslationUtils.getText(" "))
            info.add(TranslationUtils.getText(TranslationUtils.getTranslatedText("override", "info", "eff_disabled")))
        }
        info.add(TranslationUtils.getText(" "))
        info.add(TranslationUtils.getText("${TextFormatting.GOLD}${TranslationUtils.getTranslatedText("vial", "usage", "desc")}"))
    }
}