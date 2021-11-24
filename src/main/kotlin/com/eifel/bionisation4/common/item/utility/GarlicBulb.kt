package com.eifel.bionisation4.common.item.utility

import com.eifel.bionisation4.common.block.BlockRegistry
import com.eifel.bionisation4.common.core.BionisationTab
import com.eifel.bionisation4.common.extensions.addEffect
import com.eifel.bionisation4.common.laboratory.common.effect.Immunity
import com.eifel.bionisation4.util.translation.TranslationUtils
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.BlockItem
import net.minecraft.item.Food
import net.minecraft.item.ItemStack
import net.minecraft.item.Rarity
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.TextFormatting
import net.minecraft.world.World
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

class GarlicBulb(): BlockItem(BlockRegistry.GARLIC.get(), Properties().stacksTo(64).rarity(Rarity.UNCOMMON).tab(BionisationTab.BIONISATION_TAB).food(Food.Builder().fast().build())) {

    override fun finishUsingItem(stack: ItemStack, world: World?, entity: LivingEntity?): ItemStack {
        val itemStack = super.finishUsingItem(stack, world, entity)
        (entity as? PlayerEntity)?.let { player ->
            if(!player.level.isClientSide) {
                player.addEffect(Immunity())
                itemStack.shrink(1)
            }
        }
        return itemStack
    }

    @OnlyIn(Dist.CLIENT)
    override fun appendHoverText(stack: ItemStack, world: World?, info: MutableList<ITextComponent>, flag: ITooltipFlag) {
        super.appendHoverText(stack, world, info, flag)
        info.add(TranslationUtils.getText(" "))
        info.add(TranslationUtils.getText("${TextFormatting.GOLD}${TranslationUtils.getTranslatedText("garlic", "usage", "desc")}"))
    }
}