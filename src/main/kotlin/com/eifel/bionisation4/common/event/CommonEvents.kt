package com.eifel.bionisation4.common.event

import com.eifel.bionisation4.common.config.ConfigProperties
import com.eifel.bionisation4.common.extensions.doWithCap
import com.eifel.bionisation4.common.extensions.setBlood
import com.eifel.bionisation4.common.extensions.setImmunity
import com.eifel.bionisation4.common.extensions.updateToClient
import com.eifel.bionisation4.common.storage.capability.entity.BioMob
import com.eifel.bionisation4.common.storage.capability.entity.BioMobProvider
import com.eifel.bionisation4.common.storage.capability.player.BioPlayer
import com.eifel.bionisation4.common.storage.capability.player.BioPlayerProvider
import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraftforge.event.AttachCapabilitiesEvent
import net.minecraftforge.event.entity.living.LivingDeathEvent
import net.minecraftforge.event.entity.living.LivingEvent
import net.minecraftforge.event.entity.living.LivingHurtEvent
import net.minecraftforge.event.entity.player.PlayerEvent
import net.minecraftforge.eventbus.api.SubscribeEvent

object CommonEvents {

    @JvmStatic
    @SubscribeEvent
    fun onAttachCapabilitiesEvent(event: AttachCapabilitiesEvent<Entity>) {
        when(event.`object`){
            is PlayerEntity -> event.addCapability(BioPlayer.PROP, BioPlayerProvider())
            is LivingEntity -> event.addCapability(BioMob.PROP, BioMobProvider())
        }
    }

    @JvmStatic
    @SubscribeEvent
    fun onPlayerClone(event: PlayerEvent.Clone) {
        if (!event.player.level.isClientSide) {
            if (!event.isWasDeath || (event.isWasDeath && ConfigProperties.saveAfterDeath.get())) {
                event.player.doWithCap<BioPlayer> { newCap ->
                    event.original.doWithCap<BioPlayer> { oldCap ->
                        newCap.readFromNBT(oldCap.writeToNBT())
                    }
                }
            }
        }
    }

    @JvmStatic
    @SubscribeEvent
    fun onPlayerLogIn(event: PlayerEvent.PlayerLoggedInEvent) {
        if(!event.player.level.isClientSide)
            event.player.updateToClient()
    }

    @JvmStatic
    @SubscribeEvent
    fun onPlayerRespawn(event: PlayerEvent.PlayerRespawnEvent) {
        if(!event.player.level.isClientSide) {
            event.player.setBlood(100)
            event.player.setImmunity(100)
            event.player.updateToClient()
        }
    }

    @JvmStatic
    @SubscribeEvent
    fun onEntityBeingHurt(event: LivingHurtEvent) {
        val target = event.entityLiving
        target?.let { victim ->
            if(!victim.level.isClientSide) {
                val damageSource = event.source
                (damageSource.directEntity as? LivingEntity)?.let { source ->
                    when (victim) {
                        is PlayerEntity -> {
                            victim.doWithCap<BioPlayer> { cap ->
                                cap.effects.forEach {
                                    it.onAttack(victim, source)
                                }
                            }
                        }
                        is LivingEntity -> {
                            victim.doWithCap<BioMob> { cap ->
                                cap.effects.forEach {
                                    it.onAttack(victim, source)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @JvmStatic
    @SubscribeEvent
    fun onEntityDeath(event: LivingDeathEvent) {
        val target = event.entityLiving
        target?.let { victim ->
            if(!victim.level.isClientSide) {
                when (victim) {
                    is PlayerEntity -> {
                        victim.doWithCap<BioPlayer> { cap ->
                            cap.effects.forEach {
                                it.onDeath(victim)
                            }
                        }
                    }
                    is LivingEntity -> {
                        victim.doWithCap<BioMob> { cap ->
                            cap.effects.forEach {
                                it.onDeath(victim)
                            }
                        }
                    }
                }
            }
        }
    }

    @JvmStatic
    @SubscribeEvent
    fun onPlayerChangedDimension(event: PlayerEvent.PlayerChangedDimensionEvent) {
        if(!event.player.level.isClientSide)
            event.player.updateToClient()
    }

    @JvmStatic
    @SubscribeEvent
    fun onEntityTick(event: LivingEvent.LivingUpdateEvent) {
        if(!event.entity.level.isClientSide) {
            when (event.entity) {
                //for players
                is PlayerEntity -> {
                    val player = event.entity as PlayerEntity
                    player.doWithCap<BioPlayer> { cap ->
                        cap.onUpdate(player)
                        if (cap.ticker % 12000 == 0)
                            cap.modifyBlood(player, 2)
                    }
                }
                //for others
                is LivingEntity -> {
                    val entity = event.entityLiving
                    entity.doWithCap<BioMob> { cap ->
                        cap.onUpdate(entity)
                        if (cap.ticker % 12000 == 0)
                            cap.modifyBlood(entity, 2)
                    }
                }
            }
        }
    }
}