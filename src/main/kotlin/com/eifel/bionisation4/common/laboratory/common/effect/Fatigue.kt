package com.eifel.bionisation4.common.laboratory.common.effect

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.util.EffectType
import com.eifel.bionisation4.common.extensions.expire
import com.eifel.bionisation4.common.extensions.getBioTicker
import com.eifel.bionisation4.common.extensions.getImmunity
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack

class Fatigue(): AbstractEffect(InternalConstants.EFFECT_FATIGUE_ID, "Fatigue", EffectType.COMMON) {

    init {
        isInfinite = true
        canChangePower = false
        isSyncable = false
    }

    override fun onTick(entity: LivingEntity, isLastTick: Boolean) {
        super.onTick(entity, isLastTick)
        entity.addEffect(MobEffectInstance(MobEffects.WEAKNESS, 100, effectPower))
        if(entity is Player) {
            if (entity.getBioTicker() % 100 == 0) {
                entity.drop(entity.inventory.getSelected(), true)
                entity.inventory.setItem(entity.inventory.selected, ItemStack.EMPTY)
            }
        }
        if(entity.getImmunity() > 30)
            entity.expire(this.effectID)
    }

    override fun getCopy() = Fatigue()
}