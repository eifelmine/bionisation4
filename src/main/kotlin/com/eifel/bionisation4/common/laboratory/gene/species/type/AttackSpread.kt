package com.eifel.bionisation4.common.laboratory.gene.species.type

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.species.Gene
import com.eifel.bionisation4.common.extensions.addEffect
import com.eifel.bionisation4.common.extensions.isEffectActive
import net.minecraft.entity.LivingEntity

class AttackSpread(): Gene(InternalConstants.GENE_ATTACK_SPREAD_ID, "Attack spread", true) {

    override fun onAttack(victim: LivingEntity, attacker: LivingEntity, effect: AbstractEffect) {
        super.onAttack(victim, attacker, effect)
        if(victim.isEffectActive(effect))
            attacker.addEffect(effect.getCopy())
        else if(attacker.isEffectActive(effect))
            victim.addEffect(effect.getCopy())
    }

    override fun getCopy() = AttackSpread()
}