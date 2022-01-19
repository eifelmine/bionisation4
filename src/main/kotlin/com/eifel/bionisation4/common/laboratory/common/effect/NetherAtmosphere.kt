package com.eifel.bionisation4.common.laboratory.common.effect

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.util.EffectType
import com.eifel.bionisation4.common.extensions.expire
import com.eifel.bionisation4.common.extensions.getBioTicker
import net.minecraft.entity.LivingEntity

class NetherAtmosphere(): AbstractEffect(InternalConstants.EFFECT_NETHER_ATMOSPHERE_ID, "Nether atmosphere", EffectType.COMMON) {

    init {
        isInfinite = false
        effectDuration = 1200
        canChangePower = false
        isSyncable = false
    }

    override fun onTick(entity: LivingEntity, isLastTick: Boolean) {
        super.onTick(entity, isLastTick)
        if(entity.getBioTicker() % 100 == 0)
            entity.setSecondsOnFire(10)
        if(entity.isInWater)
            entity.expire(this.effectID)
    }

    override fun getCopy() = NetherAtmosphere()
}