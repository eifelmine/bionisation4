package com.eifel.bionisation4.common.laboratory.virus

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.util.EffectType
import com.eifel.bionisation4.common.laboratory.gene.species.potion.Confusion
import com.eifel.bionisation4.common.laboratory.gene.species.potion.Weakness
import net.minecraft.entity.LivingEntity

class Aer(): AbstractEffect(InternalConstants.VIRUS_AER_ID, "Aer", EffectType.VIRUS) {

    init {
        isInfinite = true
        canChangePower = true
        isSyncable = false
        isHidden = false

        effectGenes.add(Weakness())
        effectGenes.add(Confusion().setActive(false))
    }

    override fun onTick(entity: LivingEntity, isLastTick: Boolean) {
        super.onTick(entity, isLastTick)
        if(entity.level.isRaining)
            activateGene(InternalConstants.GENE_CONFUSION_ID)
        else
            deactivateGene(InternalConstants.GENE_CONFUSION_ID)
    }

    override fun getCopy() = Aer()
}