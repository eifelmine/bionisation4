package com.eifel.bionisation4.common.item.utility

import com.eifel.bionisation4.api.laboratory.registry.EffectRegistry
import com.eifel.bionisation4.common.core.BionisationTab
import com.eifel.bionisation4.util.translation.TranslationUtils
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.Component
import net.minecraft.world.food.FoodProperties
import net.minecraft.world.item.*
import net.minecraft.world.level.Level
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

class Antibiotic(): Item(Properties().stacksTo(64).rarity(Rarity.UNCOMMON).tab(BionisationTab.BIONISATION_TAB).food(
    FoodProperties.Builder().alwaysEat().fast().build())) {

    @OnlyIn(Dist.CLIENT)
    override fun appendHoverText(stack: ItemStack, world: Level?, info: MutableList<Component>, flag: TooltipFlag) {
        super.appendHoverText(stack, world, info, flag)
        info.add(TranslationUtils.getText(" "))
        EffectRegistry.getAntibiotics()[this]?.let { antibiotic ->
            info.add(TranslationUtils.getText("${ChatFormatting.DARK_AQUA}${TranslationUtils.getTranslatedText("antibiotic", "info", "against")}"))
            antibiotic.forEach {
                info.add(TranslationUtils.getText("    ${ChatFormatting.GRAY}- ${EffectRegistry.getMobEffectInstance(it).getTranslationName()}"))
            }
            info.add(TranslationUtils.getText(" "))
        }
        info.add(TranslationUtils.getText("${ChatFormatting.GOLD}${TranslationUtils.getTranslatedText("antibiotic", "usage", "desc")}"))
    }

    override fun getUseAnimation(stack: ItemStack) = UseAnim.DRINK
}