package com.eifel.bionisation4.common.laboratory.virus

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.util.EffectType
import com.eifel.bionisation4.common.laboratory.gene.species.potion.DamageResistance
import com.eifel.bionisation4.common.laboratory.gene.species.potion.MovementSpeed
import com.eifel.bionisation4.common.laboratory.gene.species.potion.Weakness
import com.eifel.bionisation4.common.laboratory.gene.species.type.Aggressive
import com.eifel.bionisation4.common.laboratory.gene.species.type.AttackSpread
import com.eifel.bionisation4.common.laboratory.gene.species.type.Sunburn
import com.eifel.bionisation4.common.laboratory.gene.species.type.WaterFear
import net.minecraft.world.entity.LivingEntity

class Rabies(): AbstractEffect(InternalConstants.VIRUS_RABIES_ID, "Rabies", EffectType.VIRUS) {

    init {
        isInfinite = true
        canChangePower = false
        isSyncable = false
        isHidden = true
        this.effectGenes.add(AttackSpread())
        this.effectGenes.add(MovementSpeed().setPower(4))
        this.effectGenes.add(WaterFear())
        this.effectGenes.add(Sunburn())
        this.effectGenes.add(Aggressive())
        this.effectGenes.add(Weakness().setPower(2))
        this.effectGenes.add(DamageResistance().setPower(2))
        deactivateGenes()
    }

    override fun onTick(entity: LivingEntity, isLastTick: Boolean) {
        super.onTick(entity, isLastTick)
        when(timeTicker){
            100 -> activateGene(InternalConstants.GENE_ATTACK_SPREAD_ID)
            1200 -> {
                isHidden = false
                activateGene(InternalConstants.GENE_MOVEMENT_SPEED_ID)
                activateGene(InternalConstants.GENE_WATER_FEAR_ID)
                activateGene(InternalConstants.GENE_AGGRESSIVE_ID)
                activateGene(InternalConstants.GENE_WEAKNESS_ID)
                activateGene(InternalConstants.GENE_DAMAGE_RESISTANCE_ID)
            }
            32000 -> activateGene(InternalConstants.GENE_SUNBURN_ID)
        }
    }

    override fun getCopy() = Rabies()
}