package com.eifel.bionisation4.common.item.utility

import com.eifel.bionisation4.api.util.Utils
import com.eifel.bionisation4.common.extensions.getImmunity
import com.eifel.bionisation4.common.item.CommonItem
import com.eifel.bionisation4.util.translation.TranslationUtils
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.item.UseAction
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.text.TextFormatting
import net.minecraft.world.World

class ImmunityChecker(): CommonItem(desc = listOf(Triple("checker", "usage", "desc"))) {

    override fun getUseDuration(stack: ItemStack) = 72000
    override fun getUseAnimation(stack: ItemStack) = UseAction.BOW

    override fun use(world: World, player: PlayerEntity, hand: Hand): ActionResult<ItemStack> {
        val stack = player.getItemInHand(hand)
        if (!player.level.isClientSide) {
            player.startUsingItem(hand)
            return ActionResult.consume(stack)
        }
        return ActionResult.pass(stack)
    }

    override fun releaseUsing(stack: ItemStack, world: World, entity: LivingEntity, duration: Int) {
        (entity as? PlayerEntity)?.let { player ->
            if(!player.level.isClientSide && (getUseDuration(stack) - duration) >= 20) {
                val immunity = player.getImmunity()
                player.sendMessage(TranslationUtils.getText("${TextFormatting.DARK_AQUA}${TranslationUtils.getTranslatedText("checker", "usage", "result")}${Utils.getColorFromValue(immunity)}$immunity ${TextFormatting.DARK_AQUA}%"), null)
            }
        }
    }
}