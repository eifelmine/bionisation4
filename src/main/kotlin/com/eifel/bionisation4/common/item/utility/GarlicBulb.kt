package com.eifel.bionisation4.common.item.utility

import com.eifel.bionisation4.common.block.BlockRegistry
import com.eifel.bionisation4.common.core.BionisationTab
import com.eifel.bionisation4.common.extensions.addEffect
import com.eifel.bionisation4.common.laboratory.common.effect.Immunity
import com.eifel.bionisation4.util.translation.TranslationUtils
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.food.FoodProperties
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Rarity
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.Level
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

class GarlicBulb(): BlockItem(BlockRegistry.GARLIC.get(), Properties().stacksTo(64).rarity(Rarity.UNCOMMON).tab(BionisationTab.BIONISATION_TAB).food(FoodProperties.Builder().alwaysEat().fast().build())) {

    override fun finishUsingItem(stack: ItemStack, world: Level?, entity: LivingEntity?): ItemStack {
        val itemStack = super.finishUsingItem(stack, world, entity)
        (entity as? Player)?.let { player ->
            if(!player.level.isClientSide) {
                player.addEffect(Immunity())
                itemStack.shrink(1)
            }
        }
        return itemStack
    }

    @OnlyIn(Dist.CLIENT)
    override fun appendHoverText(stack: ItemStack, world: Level?, info: MutableList<Component>, flag: TooltipFlag) {
        super.appendHoverText(stack, world, info, flag)
        info.add(TranslationUtils.getText(" "))
        info.add(TranslationUtils.getText("${ChatFormatting.GOLD}${TranslationUtils.getTranslatedText("garlic", "usage", "desc")}"))
    }
}