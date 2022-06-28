package com.eifel.bionisation4.common.item.utility

import com.eifel.bionisation4.api.util.Utils
import com.eifel.bionisation4.common.extensions.getImmunity
import com.eifel.bionisation4.common.item.CommonItem
import com.eifel.bionisation4.util.translation.TranslationUtils
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.UseAnim
import net.minecraft.world.level.Level


class ImmunityChecker(): CommonItem(desc = listOf(Triple("checker", "usage", "desc")), size = 1) {

    override fun getUseDuration(stack: ItemStack) = 72000
    override fun getUseAnimation(stack: ItemStack) = UseAnim.BOW

    override fun use(world: Level, player: Player, hand: InteractionHand): InteractionResultHolder<ItemStack> {
        val stack = player.getItemInHand(hand)
        if (!player.level.isClientSide) {
            player.startUsingItem(hand)
            return InteractionResultHolder.consume(stack)
        }
        return InteractionResultHolder.pass(stack)
    }

    override fun releaseUsing(stack: ItemStack, world: Level, entity: LivingEntity, duration: Int) {
        (entity as? Player)?.let { player ->
            if(!player.level.isClientSide && (getUseDuration(stack) - duration) >= 20) {
                val immunity = player.getImmunity()
                player.sendSystemMessage(TranslationUtils.getCommonTranslation("checker", "usage", "result").append("${Utils.getColorFromValue(immunity)}$immunity%"))
            }
        }
    }
}