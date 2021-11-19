package com.eifel.bionisation4.common.extensions

import com.eifel.bionisation4.common.storage.capability.entity.BioMob
import com.eifel.bionisation4.common.storage.capability.entity.BioMobProvider
import net.minecraft.entity.LivingEntity

inline fun <reified E : BioMob> LivingEntity.doWithCap(crossinline action: (BioMob) -> Unit)  = this.getCapability(BioMobProvider.BIO_MOB_CAPABILITY).ifPresent { cap ->
    action(cap as BioMob)
}