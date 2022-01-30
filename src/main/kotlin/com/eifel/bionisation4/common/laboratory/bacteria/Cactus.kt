package com.eifel.bionisation4.common.laboratory.bacteria

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.util.EffectType
import net.minecraft.entity.LivingEntity
import net.minecraft.util.DamageSource
import net.minecraftforge.event.entity.living.LivingHurtEvent

class Cactus(): AbstractEffect(InternalConstants.BACTERIA_CACTUS_ID, "Cactus", EffectType.BACTERIA) {

    init {
        isInfinite = false
        effectDuration = 24000
        canChangePower = true
        isSyncable = false
        isHidden = false
    }

    override fun onHurt(event: LivingHurtEvent, victim: LivingEntity) {
        super.onHurt(event, victim)
        event.source.directEntity?.let{ attacker ->
            attacker.hurt(DamageSource.CACTUS, 2f)
        }
    }

    override fun getCopy() = Cactus()
}