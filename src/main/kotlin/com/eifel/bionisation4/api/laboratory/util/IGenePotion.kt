package com.eifel.bionisation4.api.laboratory.util

import net.minecraft.world.entity.LivingEntity

interface IGenePotion : INBTSerializable {

    fun clear(entity: LivingEntity)

    fun perform(entity: LivingEntity, power: Int)
    fun getPotionID() : Int
    fun getPotionDuration() : Int
    fun getPotionPower() : Int
}