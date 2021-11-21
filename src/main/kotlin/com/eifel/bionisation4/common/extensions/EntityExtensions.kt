package com.eifel.bionisation4.common.extensions

import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.common.storage.capability.entity.BioMob
import com.eifel.bionisation4.common.storage.capability.entity.BioMobProvider
import net.minecraft.entity.LivingEntity

inline fun <reified E : BioMob> LivingEntity.doWithCap(crossinline action: (BioMob) -> Unit)  = this.getCapability(BioMobProvider.BIO_MOB_CAPABILITY).ifPresent { cap ->
    action(cap as E)
}

inline fun LivingEntity.getImmunity(): Int = this.getCapability(BioMobProvider.BIO_MOB_CAPABILITY).orElse(null)?.let {
    (it as BioMob).immunity
} ?: 0

inline fun LivingEntity.getBlood(): Int = this.getCapability(BioMobProvider.BIO_MOB_CAPABILITY).orElse(null)?.let {
    (it as BioMob).blood
} ?: 0

inline fun LivingEntity.getBioTicker(): Int = this.getCapability(BioMobProvider.BIO_MOB_CAPABILITY).orElse(null)?.let {
    (it as BioMob).ticker
} ?: 0

inline fun LivingEntity.setImmunity(value: Int) = this.getCapability(BioMobProvider.BIO_MOB_CAPABILITY).orElse(null)?.let {
    (it as BioMob).setImmunity(this, value)
}

inline fun LivingEntity.setBlood(value: Int) = this.getCapability(BioMobProvider.BIO_MOB_CAPABILITY).orElse(null)?.let {
    (it as BioMob).setBlood(this, value)
}

inline fun LivingEntity.modifyImmunity(value: Int) = this.getCapability(BioMobProvider.BIO_MOB_CAPABILITY).orElse(null)?.let {
    (it as BioMob).modifyImmunity(this, value)
}

inline fun LivingEntity.modifyBlood(value: Int) = this.getCapability(BioMobProvider.BIO_MOB_CAPABILITY).orElse(null)?.let {
    (it as BioMob).modifyBlood(this, value)
}

inline fun LivingEntity.addEffect(effect: AbstractEffect) = this.getCapability(BioMobProvider.BIO_MOB_CAPABILITY).orElse(null)?.let {
    (it as BioMob).addEffect(effect)
}

inline fun LivingEntity.expire(effect: AbstractEffect) = this.getCapability(BioMobProvider.BIO_MOB_CAPABILITY).orElse(null)?.let {
    (it as BioMob).expire(effect)
}

inline fun LivingEntity.expire(effect: Int) = this.getCapability(BioMobProvider.BIO_MOB_CAPABILITY).orElse(null)?.let {
    (it as BioMob).expire(effect)
}

inline fun LivingEntity.expire(effect: String) = this.getCapability(BioMobProvider.BIO_MOB_CAPABILITY).orElse(null)?.let {
    (it as BioMob).expire(effect)
}

inline fun LivingEntity.isEffectActive(effect: AbstractEffect) = this.getCapability(BioMobProvider.BIO_MOB_CAPABILITY).orElse(null)?.let {
    (it as BioMob).isActive(effect)
} ?: false

inline fun LivingEntity.isEffectActive(effect: Int) = this.getCapability(BioMobProvider.BIO_MOB_CAPABILITY).orElse(null)?.let {
    (it as BioMob).isActive(effect)
} ?: false

inline fun LivingEntity.isEffectActive(effect: String) = this.getCapability(BioMobProvider.BIO_MOB_CAPABILITY).orElse(null)?.let {
    (it as BioMob).isActive(effect)
} ?: false

inline fun LivingEntity.updateToClient() = this.getCapability(BioMobProvider.BIO_MOB_CAPABILITY).orElse(null)?.let {
    (it as BioMob).sendAllEffects(this)
}
