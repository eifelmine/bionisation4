package com.eifel.bionisation4.common.laboratory.virus

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.util.EffectType
import com.eifel.bionisation4.common.laboratory.gene.species.potion.Jump
import com.eifel.bionisation4.common.laboratory.gene.species.potion.Poison
import com.eifel.bionisation4.common.laboratory.gene.species.potion.Weakness
import com.eifel.bionisation4.common.laboratory.gene.species.type.Aggressive
import com.eifel.bionisation4.common.laboratory.gene.species.type.AirSpread
import com.eifel.bionisation4.common.laboratory.gene.species.type.AttackSpread
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.monster.Spider
import net.minecraft.world.entity.player.Player


class Red(): AbstractEffect(InternalConstants.VIRUS_RED_ID, "Red", EffectType.VIRUS) {

    init {
        isInfinite = true
        canChangePower = true
        isSyncable = true
        isHidden = false

        effectGenes.add(AirSpread().setPredicate(mutableListOf("SpiderEntity")))
        effectGenes.add(AttackSpread())
        effectGenes.add(Aggressive())
        effectGenes.add(Jump().setActive(false))
        effectGenes.add(Weakness().setActive(false))
        effectGenes.add(Poison().setActive(false))
    }

    override fun onTick(entity: LivingEntity, isLastTick: Boolean) {
        super.onTick(entity, isLastTick)
        if(timeTicker == 10){
            when(entity){
                is Player -> {
                    activateGene(InternalConstants.GENE_JUMP_ID)
                    activateGene(InternalConstants.GENE_WEAKNESS_ID)
                }
                is LivingEntity -> {
                    if(entity !is Spider)
                        activateGene(InternalConstants.GENE_POISON_ID)
                }
            }
        }
    }

    override fun getCopy() = Red()
}