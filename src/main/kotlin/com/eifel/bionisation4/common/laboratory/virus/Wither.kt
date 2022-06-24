package com.eifel.bionisation4.common.laboratory.virus

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.util.EffectType
import com.eifel.bionisation4.common.extensions.getBioTicker
import com.eifel.bionisation4.common.laboratory.gene.species.type.AirSpread
import com.eifel.bionisation4.common.laboratory.gene.species.type.AttackSpread
import com.eifel.bionisation4.common.laboratory.gene.species.type.ImmunityDamage
import com.eifel.bionisation4.util.lab.EffectUtils
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.entity.LivingEntity

class Wither(): AbstractEffect(InternalConstants.VIRUS_WITHER_ID, "Wither", EffectType.VIRUS) {

    init {
        isInfinite = true
        canChangePower = true
        isSyncable = false
        isHidden = false

        effectGenes.add(ImmunityDamage().setImmunity(1))
        effectGenes.add(AttackSpread())
        effectGenes.add(AirSpread().setRadius(5.0))
    }

    override fun onTick(entity: LivingEntity, isLastTick: Boolean) {
        super.onTick(entity, isLastTick)
        if(entity.getBioTicker() % 300 == 0){
            EffectUtils.applyToEntities(entity.level, entity.blockPosition(), 5.0, action = {
                (it as LivingEntity).addEffect(MobEffectInstance(MobEffects.WITHER, 100, effectPower))
            })
        }
    }

    override fun getCopy() = Wither()
}