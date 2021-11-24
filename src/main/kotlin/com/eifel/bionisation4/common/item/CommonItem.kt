package com.eifel.bionisation4.common.item

import com.eifel.bionisation4.common.core.BionisationTab
import com.eifel.bionisation4.util.translation.TranslationUtils
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.Rarity
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.TextFormatting
import net.minecraft.world.World
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

open class CommonItem(rarity: Rarity = Rarity.UNCOMMON, size: Int = 64, val enchanted: Boolean = false, val desc: List<Triple<String, String, String>> = listOf()): Item(Properties().tab(BionisationTab.BIONISATION_TAB).rarity(rarity).stacksTo(size)) {

    @OnlyIn(Dist.CLIENT)
    override fun appendHoverText(stack: ItemStack, world: World?, info: MutableList<ITextComponent>, flag: ITooltipFlag) {
        super.appendHoverText(stack, world, info, flag)
        if(desc.isNotEmpty()) {
            info.add(TranslationUtils.getText(" "))
            desc.forEach { desc -> info.add(TranslationUtils.getText("${TextFormatting.GOLD}${TranslationUtils.getTranslatedText(desc.first, desc.second, desc.third)}")) }
        }
    }

    override fun isFoil(stack: ItemStack) = enchanted || super.isFoil(stack)
}