package com.eifel.bionisation4.common.laboratory.bacteria

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.util.EffectType
import com.eifel.bionisation4.common.laboratory.gene.species.potion.MovementSpeed
import com.eifel.bionisation4.common.laboratory.gene.species.type.ImmunityDamage
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Items

class Bone(): AbstractEffect(InternalConstants.BACTERIA_BONE_ID, "Bone", EffectType.BACTERIA) {

    init {
        isInfinite = true
        canChangePower = true
        isSyncable = true
        isHidden = false

        effectGenes.add(MovementSpeed())
        effectGenes.add(ImmunityDamage().setImmunity(1).setDelay(3600))
    }

    override fun onTick(entity: LivingEntity, isLastTick: Boolean) {
        super.onTick(entity, isLastTick)
        if(entity is PlayerEntity && entity.mainHandItem.item == Items.BOW)
            entity.drop(true)
    }

    override fun getCopy() = Bone()
}