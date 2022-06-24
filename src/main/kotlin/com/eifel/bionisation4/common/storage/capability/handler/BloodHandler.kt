package com.eifel.bionisation4.common.storage.capability.handler

import net.minecraft.world.entity.LivingEntity


object BloodHandler {

    fun checkBloodLevel(entity: LivingEntity, level: Int){
        if(level <= 30)
            entity.kill()
    }
}