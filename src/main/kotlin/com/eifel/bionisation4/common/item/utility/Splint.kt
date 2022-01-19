package com.eifel.bionisation4.common.item.utility

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.common.extensions.expire
import com.eifel.bionisation4.common.extensions.isEffectActive
import com.eifel.bionisation4.common.item.CommonItem
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.util.ActionResult
import net.minecraft.util.ActionResultType
import net.minecraft.util.Hand
import net.minecraft.world.World

class Splint(): CommonItem(desc = listOf(Triple("splint", "usage", "desc"))) {

    override fun use(world: World, player: PlayerEntity, hand: Hand): ActionResult<ItemStack> {
        val stack = player.getItemInHand(hand)
        if(!player.level.isClientSide) {
            if(player.isEffectActive(InternalConstants.EFFECT_FRACTURE_ID)) {
                player.expire(InternalConstants.EFFECT_FRACTURE_ID)
                stack.shrink(1)
            }
        }
        return ActionResult.pass(stack)
    }

    override fun interactLivingEntity(stack: ItemStack, player: PlayerEntity, target: LivingEntity, hand: Hand): ActionResultType {
        val stack = player.getItemInHand(hand)
        if(!player.level.isClientSide && player.isShiftKeyDown) {
            target?.let { entity ->
                if(entity.isEffectActive(InternalConstants.EFFECT_FRACTURE_ID)) {
                    entity.expire(InternalConstants.EFFECT_FRACTURE_ID)
                    stack.shrink(1)
                }
            }
        }
        return ActionResultType.SUCCESS
    }
}