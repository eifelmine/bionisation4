package com.eifel.bionisation4.common.laboratory.common.effect

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.util.EffectType
import com.eifel.bionisation4.common.extensions.expire
import com.eifel.bionisation4.common.extensions.getBioTicker
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Items
import net.minecraft.potion.EffectInstance
import net.minecraft.potion.Effects
import net.minecraft.util.Hand

class Alienation(): AbstractEffect(InternalConstants.EFFECT_ALIENATION_ID, "Alienation", EffectType.COMMON) {

    init {
        isInfinite = false
        effectDuration = 2400
        canChangePower = false
        isSyncable = false
    }

    override fun onTick(entity: LivingEntity, isLastTick: Boolean) {
        super.onTick(entity, isLastTick)
        if(entity.getBioTicker() % 600 == 0)
            entity.addEffect(EffectInstance(Effects.WITHER, 200, effectPower))
        if(entity is PlayerEntity && entity.getItemInHand(Hand.MAIN_HAND).item == Items.NETHER_STAR)
            entity.expire(this.effectID)
    }

    override fun getCopy() = Alienation()
}