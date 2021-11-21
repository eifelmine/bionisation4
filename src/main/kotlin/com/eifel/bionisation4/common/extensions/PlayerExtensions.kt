package com.eifel.bionisation4.common.extensions

import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.common.storage.capability.player.BioPlayer
import com.eifel.bionisation4.common.storage.capability.player.BioPlayerProvider
import net.minecraft.entity.player.PlayerEntity

inline fun <reified E : BioPlayer> PlayerEntity.doWithCap(crossinline action: (BioPlayer) -> Unit)  = this.getCapability(BioPlayerProvider.BIO_PLAYER_CAPABILITY).ifPresent { cap ->
    action(cap as E)
}

inline fun PlayerEntity.getImmunity(): Int = this.getCapability(BioPlayerProvider.BIO_PLAYER_CAPABILITY).orElse(null)?.let {
    (it as BioPlayer).immunity
} ?: 0

inline fun PlayerEntity.getBlood(): Int = this.getCapability(BioPlayerProvider.BIO_PLAYER_CAPABILITY).orElse(null)?.let {
    (it as BioPlayer).blood
} ?: 0

inline fun PlayerEntity.getBioTicker(): Int = this.getCapability(BioPlayerProvider.BIO_PLAYER_CAPABILITY).orElse(null)?.let {
    (it as BioPlayer).ticker
} ?: 0

inline fun PlayerEntity.setImmunity(value: Int) = this.getCapability(BioPlayerProvider.BIO_PLAYER_CAPABILITY).orElse(null)?.let {
    (it as BioPlayer).setImmunity(this, value)
}

inline fun PlayerEntity.setBlood(value: Int) = this.getCapability(BioPlayerProvider.BIO_PLAYER_CAPABILITY).orElse(null)?.let {
    (it as BioPlayer).setBlood(this, value)
}

inline fun PlayerEntity.modifyImmunity(value: Int) = this.getCapability(BioPlayerProvider.BIO_PLAYER_CAPABILITY).orElse(null)?.let {
    (it as BioPlayer).modifyImmunity(this, value)
}

inline fun PlayerEntity.modifyBlood(value: Int) = this.getCapability(BioPlayerProvider.BIO_PLAYER_CAPABILITY).orElse(null)?.let {
    (it as BioPlayer).modifyBlood(this, value)
}

inline fun PlayerEntity.addEffect(effect: AbstractEffect) = this.getCapability(BioPlayerProvider.BIO_PLAYER_CAPABILITY).orElse(null)?.let {
    (it as BioPlayer).addEffect(effect)
}

inline fun PlayerEntity.expire(effect: AbstractEffect) = this.getCapability(BioPlayerProvider.BIO_PLAYER_CAPABILITY).orElse(null)?.let {
    (it as BioPlayer).expire(effect)
}

inline fun PlayerEntity.expire(effect: Int) = this.getCapability(BioPlayerProvider.BIO_PLAYER_CAPABILITY).orElse(null)?.let {
    (it as BioPlayer).expire(effect)
}

inline fun PlayerEntity.expire(effect: String) = this.getCapability(BioPlayerProvider.BIO_PLAYER_CAPABILITY).orElse(null)?.let {
    (it as BioPlayer).expire(effect)
}

inline fun PlayerEntity.isEffectActive(effect: AbstractEffect) = this.getCapability(BioPlayerProvider.BIO_PLAYER_CAPABILITY).orElse(null)?.let {
    (it as BioPlayer).isActive(effect)
} ?: false

inline fun PlayerEntity.isEffectActive(effect: Int) = this.getCapability(BioPlayerProvider.BIO_PLAYER_CAPABILITY).orElse(null)?.let {
    (it as BioPlayer).isActive(effect)
} ?: false

inline fun PlayerEntity.isEffectActive(effect: String) = this.getCapability(BioPlayerProvider.BIO_PLAYER_CAPABILITY).orElse(null)?.let {
    (it as BioPlayer).isActive(effect)
} ?: false

inline fun PlayerEntity.updateToClient() = this.getCapability(BioPlayerProvider.BIO_PLAYER_CAPABILITY).orElse(null)?.let {
    (it as BioPlayer).sendAllEffects(this)
}

