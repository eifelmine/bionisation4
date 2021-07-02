package com.eifel.bionisation4.api.laboratory.util

import net.minecraft.entity.player.PlayerEntity

interface IGenePotion : IGene {

    fun clear(player: PlayerEntity)

    fun getPotionID() : Int
    fun getPotionDuration() : Int
    fun getPotionPower() : Int
}