package com.eifel.bionisation4.common.item.utility

import com.eifel.bionisation4.api.laboratory.registry.EffectRegistry
import com.eifel.bionisation4.common.core.BionisationTab
import com.eifel.bionisation4.util.translation.TranslationUtils
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.item.*
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.TextFormatting
import net.minecraft.world.World
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

class Antibiotic(): Item(Properties().stacksTo(64).rarity(Rarity.UNCOMMON).tab(BionisationTab.BIONISATION_TAB).food(Food.Builder().alwaysEat().fast().build())) {

    @OnlyIn(Dist.CLIENT)
    override fun appendHoverText(stack: ItemStack, world: World?, info: MutableList<ITextComponent>, flag: ITooltipFlag) {
        super.appendHoverText(stack, world, info, flag)
        info.add(TranslationUtils.getText(" "))
        EffectRegistry.getAntibiotics()[this]?.let { antibiotic ->
            info.add(TranslationUtils.getText("${TextFormatting.DARK_AQUA}${TranslationUtils.getTranslatedText("antibiotic", "info", "against")}"))
            antibiotic.forEach {
                info.add(TranslationUtils.getText("    ${TextFormatting.GRAY}- ${EffectRegistry.getEffectInstance(it).getTranslationName()}"))
            }
            info.add(TranslationUtils.getText(" "))
        }
        info.add(TranslationUtils.getText("${TextFormatting.GOLD}${TranslationUtils.getTranslatedText("antibiotic", "usage", "desc")}"))
    }

    override fun getUseAnimation(stack: ItemStack) = UseAction.DRINK
}