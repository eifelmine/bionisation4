package com.eifel.bionisation4.common.extensions

import com.eifel.bionisation4.common.storage.capability.player.BioPlayer
import com.eifel.bionisation4.common.storage.capability.player.BioPlayerProvider
import net.minecraft.entity.player.PlayerEntity

inline fun <reified E : BioPlayer> PlayerEntity.doWithCap(crossinline action: (BioPlayer) -> Unit)  = this.getCapability(BioPlayerProvider.BIO_PLAYER_CAPABILITY).ifPresent { cap ->
    action(cap as BioPlayer)
}