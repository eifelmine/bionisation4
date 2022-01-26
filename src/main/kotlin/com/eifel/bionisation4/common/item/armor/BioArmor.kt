package com.eifel.bionisation4.common.item.armor

import com.eifel.bionisation4.common.core.BionisationTab
import com.eifel.bionisation4.util.translation.TranslationUtils
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.inventory.EquipmentSlotType
import net.minecraft.item.ArmorItem
import net.minecraft.item.IArmorMaterial
import net.minecraft.item.ItemStack
import net.minecraft.item.Rarity
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.TextFormatting
import net.minecraft.world.World
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

class BioArmor(material: IArmorMaterial, type: EquipmentSlotType) : ArmorItem(material, type, Properties().rarity(Rarity.UNCOMMON).tab(
    BionisationTab.BIONISATION_TAB)) {

    @OnlyIn(Dist.CLIENT)
    override fun appendHoverText(stack: ItemStack, world: World?, info: MutableList<ITextComponent>, flag: ITooltipFlag) {
        super.appendHoverText(stack, world, info, flag)
        info.add(TranslationUtils.getText(" "))
        info.add(TranslationUtils.getText("${TextFormatting.GOLD}${TranslationUtils.getTranslatedText("armor", "info", "desc")}"))
    }

    override fun isFoil(stack: ItemStack) = true
}