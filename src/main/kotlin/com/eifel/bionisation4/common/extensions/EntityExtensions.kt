package com.eifel.bionisation4.common.extensions

import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.common.storage.capability.entity.BioStat
import com.eifel.bionisation4.common.storage.capability.entity.BioStatProvider
import net.minecraft.entity.LivingEntity

inline fun LivingEntity.doWithCap(crossinline action: (BioStat) -> Unit)  = this.getCapability(BioStatProvider.BIO_STAT_CAPABILITY).ifPresent { cap ->
    action(cap as BioStat)
}

inline fun LivingEntity.getImmunity(): Int = this.getCapability(BioStatProvider.BIO_STAT_CAPABILITY).orElse(null)?.let {
    (it as BioStat).immunity
} ?: 0

inline fun LivingEntity.getBlood(): Int = this.getCapability(BioStatProvider.BIO_STAT_CAPABILITY).orElse(null)?.let {
    (it as BioStat).blood
} ?: 0

inline fun LivingEntity.getBioTicker(): Int = this.getCapability(BioStatProvider.BIO_STAT_CAPABILITY).orElse(null)?.let {
    (it as BioStat).ticker
} ?: 0

inline fun LivingEntity.setImmunity(value: Int) = this.getCapability(BioStatProvider.BIO_STAT_CAPABILITY).orElse(null)?.let {
    (it as BioStat).setImmunity(this, value)
}

inline fun LivingEntity.setBlood(value: Int) = this.getCapability(BioStatProvider.BIO_STAT_CAPABILITY).orElse(null)?.let {
    (it as BioStat).setBlood(this, value)
}

inline fun LivingEntity.modifyImmunity(value: Int) = this.getCapability(BioStatProvider.BIO_STAT_CAPABILITY).orElse(null)?.let {
    (it as BioStat).modifyImmunity(this, value)
}

inline fun LivingEntity.modifyBlood(value: Int) = this.getCapability(BioStatProvider.BIO_STAT_CAPABILITY).orElse(null)?.let {
    (it as BioStat).modifyBlood(this, value)
}

inline fun LivingEntity.addEffect(effect: AbstractEffect) = this.getCapability(BioStatProvider.BIO_STAT_CAPABILITY).orElse(null)?.let {
    (it as BioStat).addEffect(effect)
}

inline fun LivingEntity.expire(effect: AbstractEffect) = this.getCapability(BioStatProvider.BIO_STAT_CAPABILITY).orElse(null)?.let {
    (it as BioStat).expire(effect)
}

inline fun LivingEntity.expire(effect: Int) = this.getCapability(BioStatProvider.BIO_STAT_CAPABILITY).orElse(null)?.let {
    (it as BioStat).expire(effect)
}

inline fun LivingEntity.expire(effect: String) = this.getCapability(BioStatProvider.BIO_STAT_CAPABILITY).orElse(null)?.let {
    (it as BioStat).expire(effect)
}

inline fun LivingEntity.isEffectActive(effect: AbstractEffect) = this.getCapability(BioStatProvider.BIO_STAT_CAPABILITY).orElse(null)?.let {
    (it as BioStat).isActive(effect)
} ?: false

inline fun LivingEntity.isEffectActive(effect: Int) = this.getCapability(BioStatProvider.BIO_STAT_CAPABILITY).orElse(null)?.let {
    (it as BioStat).isActive(effect)
} ?: false

inline fun LivingEntity.isEffectActive(effect: String) = this.getCapability(BioStatProvider.BIO_STAT_CAPABILITY).orElse(null)?.let {
    (it as BioStat).isActive(effect)
} ?: false

inline fun LivingEntity.updateToClient() = this.getCapability(BioStatProvider.BIO_STAT_CAPABILITY).orElse(null)?.let {
    (it as BioStat).sendAllEffects(this)
}

inline fun LivingEntity.getEffects() = this.getCapability(BioStatProvider.BIO_STAT_CAPABILITY).orElse(null)?.let {
    (it as BioStat).effects
} ?: mutableListOf()
