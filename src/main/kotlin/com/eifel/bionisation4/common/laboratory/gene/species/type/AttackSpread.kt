package com.eifel.bionisation4.common.laboratory.gene.species.type

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.species.Gene
import com.eifel.bionisation4.common.extensions.addEffect
import com.eifel.bionisation4.common.extensions.isEffectActive
import net.minecraft.world.entity.LivingEntity
import net.minecraftforge.event.entity.living.LivingAttackEvent

class AttackSpread(): Gene(InternalConstants.GENE_ATTACK_SPREAD_ID, "Attack spread", true) {

    override fun onAttack(event: LivingAttackEvent, victim: LivingEntity, attacker: LivingEntity, effect: AbstractEffect) {
        super.onAttack(event, victim, attacker, effect)
        if(victim.isEffectActive(effect))
            attacker.addEffect(effect.getCopy())
        else if(attacker.isEffectActive(effect))
            victim.addEffect(effect.getCopy())
    }

    override fun getCopy() = AttackSpread()
}