package com.eifel.bionisation4.api.laboratory.util

import net.minecraft.entity.LivingEntity

interface IGene : INBTSerializable {

    fun getID() : Int
    fun getName() : String
    fun perform(entity : LivingEntity)
    fun isActive() : Boolean
}