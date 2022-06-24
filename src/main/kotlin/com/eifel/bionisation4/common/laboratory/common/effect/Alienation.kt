package com.eifel.bionisation4.common.laboratory.common.effect

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.util.EffectType
import com.eifel.bionisation4.common.extensions.expire
import com.eifel.bionisation4.common.extensions.getBioTicker
import net.minecraft.world.InteractionHand
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Items


class Alienation(): AbstractEffect(InternalConstants.EFFECT_ALIENATION_ID, "Alienation", EffectType.COMMON) {

    init {
        isInfinite = false
        effectDuration = 4800
        canChangePower = false
        isSyncable = false
    }

    override fun onTick(entity: LivingEntity, isLastTick: Boolean) {
        super.onTick(entity, isLastTick)
        if(entity.getBioTicker() % 600 == 0)
            entity.addEffect(MobEffectInstance(MobEffects.WITHER, 200, effectPower))
        if(entity is Player && entity.getItemInHand(InteractionHand.MAIN_HAND).item == Items.NETHER_STAR)
            entity.expire(this.effectID)
    }

    override fun getCopy() = Alienation()
}