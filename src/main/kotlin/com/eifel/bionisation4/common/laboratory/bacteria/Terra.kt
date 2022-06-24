package com.eifel.bionisation4.common.laboratory.bacteria

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.util.EffectType
import com.eifel.bionisation4.common.laboratory.gene.species.potion.DigSpeed
import com.eifel.bionisation4.common.laboratory.gene.species.potion.MovementSlowdown
import com.eifel.bionisation4.common.laboratory.gene.species.potion.MovementSpeed
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.level.block.Blocks

class Terra(): AbstractEffect(InternalConstants.BACTERIA_TERRA_ID, "Terra", EffectType.BACTERIA) {

    init {
        isInfinite = false
        effectDuration = 24000
        canChangePower = true
        isSyncable = false
        isHidden = false

        effectGenes.add(MovementSpeed())
        effectGenes.add(DigSpeed())
        effectGenes.add(MovementSlowdown())

        deactivateGenes()
    }

    override fun onTick(entity: LivingEntity, isLastTick: Boolean) {
        super.onTick(entity, isLastTick)
        if(entity.isOnGround) {
            val block = entity.level.getBlockState(entity.blockPosition().offset(0, -1, 0)).block
            if (block == Blocks.GRASS_BLOCK || block == Blocks.GRASS || block == Blocks.STONE || block == Blocks.DIRT) {
                activateGene(InternalConstants.GENE_MOVEMENT_SPEED_ID)
                activateGene(InternalConstants.GENE_DIG_SPEED_ID)
                deactivateGene(InternalConstants.GENE_MOVEMENT_SLOWDOWN_ID)
            } else {
                deactivateGene(InternalConstants.GENE_MOVEMENT_SPEED_ID)
                deactivateGene(InternalConstants.GENE_DIG_SPEED_ID)
                activateGene(InternalConstants.GENE_MOVEMENT_SLOWDOWN_ID)
            }
        }else
            deactivateGenes()
    }

    override fun getCopy() = Terra()
}