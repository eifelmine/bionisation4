package com.eifel.bionisation4.common.laboratory.common.effect

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.util.EffectType
import com.eifel.bionisation4.common.extensions.expire
import com.eifel.bionisation4.common.extensions.getBioTicker
import net.minecraft.entity.LivingEntity
import net.minecraft.util.DamageSource

class LackOfAir(): AbstractEffect(InternalConstants.EFFECT_LACK_OF_AIR_ID, "Lack of air", EffectType.COMMON) {

    init {
        isInfinite = false
        effectDuration = 4800
        canChangePower = false
        isSyncable = false
    }

    override fun onTick(entity: LivingEntity, isLastTick: Boolean) {
        super.onTick(entity, isLastTick)
        if(entity.getBioTicker() % 100 == 0)
            entity.hurt(DamageSource.DROWN, 2f)
        if(entity.isInLava)
            entity.expire(this.effectID)
    }

    override fun getCopy() = LackOfAir()
}