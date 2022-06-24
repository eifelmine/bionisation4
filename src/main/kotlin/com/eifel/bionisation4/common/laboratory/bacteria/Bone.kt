package com.eifel.bionisation4.common.laboratory.bacteria

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.util.EffectType
import com.eifel.bionisation4.common.laboratory.gene.species.potion.MovementSpeed
import com.eifel.bionisation4.common.laboratory.gene.species.type.ImmunityDamage
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items


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
        if(entity is Player && entity.inventory.getSelected().item == Items.BOW) {
            entity.drop(entity.inventory.getSelected(), true)
            entity.inventory.setItem(entity.inventory.selected, ItemStack.EMPTY)
        }
    }

    override fun getCopy() = Bone()
}