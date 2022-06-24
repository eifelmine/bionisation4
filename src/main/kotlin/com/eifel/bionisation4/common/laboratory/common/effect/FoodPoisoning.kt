package com.eifel.bionisation4.common.laboratory.common.effect

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.util.EffectType
import com.eifel.bionisation4.common.extensions.expire
import com.eifel.bionisation4.common.extensions.getBioTicker
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.Items

class FoodPoisoning(): AbstractEffect(InternalConstants.EFFECT_FOOD_POISONING_ID, "Food Poisoning", EffectType.COMMON) {

    companion object {
        val poison = mutableListOf(Items.POISONOUS_POTATO, Items.ROTTEN_FLESH, Items.PORKCHOP, Items.BEEF, Items.RABBIT, Items.CHICKEN, Items.MUTTON, Items.SPIDER_EYE, Items.POTATO, Items.CARROT, Items.BEETROOT)
    }

    init {
        isInfinite = false
        effectDuration = 72000
        canChangePower = false
        isSyncable = false
    }

    override fun onTick(entity: LivingEntity, isLastTick: Boolean) {
        super.onTick(entity, isLastTick)
        if (entity.getBioTicker() % 1200 == 0){
            entity.addEffect(MobEffectInstance(MobEffects.CONFUSION, 300, effectPower))
            entity.addEffect(MobEffectInstance(MobEffects.POISON, 100, effectPower))
        }
        if(entity.hasEffect(MobEffects.REGENERATION))
            entity.expire(this.effectID)
    }

    override fun getCopy() = FoodPoisoning()
}