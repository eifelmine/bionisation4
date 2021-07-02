package com.eifel.bionisation4.api.laboratory.util

import net.minecraft.entity.player.PlayerEntity

interface IGene : INBTSerializable {

    fun getID() : Int
    fun getName() : String
    //todo add capability
    fun perform(player : PlayerEntity)
    fun isActive() : Boolean
}