package com.eifel.bionisation4.common.item.armor

import com.eifel.bionisation4.common.core.BionisationTab
import com.eifel.bionisation4.util.translation.TranslationUtils
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.item.*
import net.minecraft.world.level.Level
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

class BioArmor(material: ArmorMaterial, type: EquipmentSlot) : ArmorItem(material, type, Properties().rarity(Rarity.UNCOMMON).tab(
    BionisationTab.BIONISATION_TAB)) {

    @OnlyIn(Dist.CLIENT)
    override fun appendHoverText(stack: ItemStack, world: Level?, info: MutableList<Component>, flag: TooltipFlag) {
        super.appendHoverText(stack, world, info, flag)
        info.add(TranslationUtils.getText(" "))
        info.add(TranslationUtils.getText("${ChatFormatting.GOLD}${TranslationUtils.getTranslatedText("armor", "info", "desc")}"))
    }

    override fun isFoil(stack: ItemStack) = true
}