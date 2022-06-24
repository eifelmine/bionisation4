package com.eifel.bionisation4.common.item.utility

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.common.extensions.expire
import com.eifel.bionisation4.common.extensions.isEffectActive
import com.eifel.bionisation4.common.item.CommonItem
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level


class Bandage(): CommonItem(desc = listOf(Triple("bandage", "usage", "desc"))) {

    override fun use(world: Level, player: Player, hand: InteractionHand): InteractionResultHolder<ItemStack> {
        val stack = player.getItemInHand(hand)
        if(!player.level.isClientSide) {
            if(player.isEffectActive(InternalConstants.EFFECT_BLEEDING_ID)) {
                player.expire(InternalConstants.EFFECT_BLEEDING_ID)
                stack.shrink(1)
            }
        }
        return InteractionResultHolder.pass(stack)
    }

    override fun interactLivingEntity(stack: ItemStack, player: Player, target: LivingEntity, hand: InteractionHand): InteractionResult {
        val stack = player.getItemInHand(hand)
        if(!player.level.isClientSide && player.isShiftKeyDown) {
            target?.let { entity ->
                if(entity.isEffectActive(InternalConstants.EFFECT_BLEEDING_ID)){
                    entity.expire(InternalConstants.EFFECT_BLEEDING_ID)
                    stack.shrink(1)
                }
            }
        }
        return InteractionResult.SUCCESS
    }
}