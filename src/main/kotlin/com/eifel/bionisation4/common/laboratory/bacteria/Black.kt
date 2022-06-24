package com.eifel.bionisation4.common.laboratory.bacteria

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.util.EffectType
import com.eifel.bionisation4.common.laboratory.gene.species.potion.Blindness
import com.eifel.bionisation4.common.laboratory.gene.species.type.ImmunityDamage
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.entity.LivingEntity
import net.minecraftforge.event.entity.living.LivingAttackEvent

class Black(): AbstractEffect(InternalConstants.BACTERIA_BLACK_ID, "Black", EffectType.BACTERIA) {

    init {
        isInfinite = false
        effectDuration = 72000
        canChangePower = true
        isSyncable = false
        isHidden = false

        effectGenes.add(Blindness())
        effectGenes.add(ImmunityDamage().setDelay(3600).setImmunity(1))
    }

    override fun onAttack(event: LivingAttackEvent, victim: LivingEntity, attacker: LivingEntity) {
        super.onAttack(event, victim, attacker)
        victim.addEffect(MobEffectInstance(MobEffects.POISON, 100, 2))
    }

    override fun getCopy() = Black()
}