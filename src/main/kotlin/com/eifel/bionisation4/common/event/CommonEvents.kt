package com.eifel.bionisation4.common.event

import com.eifel.bionisation4.common.config.ConfigProperties
import com.eifel.bionisation4.common.extensions.doWithCap
import com.eifel.bionisation4.common.storage.capability.entity.BioMob
import com.eifel.bionisation4.common.storage.capability.entity.BioMobProvider
import com.eifel.bionisation4.common.storage.capability.player.BioPlayer
import com.eifel.bionisation4.common.storage.capability.player.BioPlayerProvider
import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraftforge.event.AttachCapabilitiesEvent
import net.minecraftforge.event.entity.living.LivingEvent
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
        if(!event.isWasDeath || (event.isWasDeath && ConfigProperties.saveAfterDeath.get())){
            event.player.doWithCap<BioPlayer> { newCap ->
                event.original.doWithCap<BioPlayer> { oldCap ->
                    newCap.readFromNBT(oldCap.writeToNBT())
                }
            }
        }
    }

    @JvmStatic
    @SubscribeEvent
    fun onPlayerLogIn(event: PlayerEvent.PlayerLoggedInEvent) {
        event.player.doWithCap<BioPlayer> { cap ->
            cap.sendAllEffects(event.player)
        }
    }

    @JvmStatic
    @SubscribeEvent
    fun onPlayerChangedDimension(event: PlayerEvent.PlayerChangedDimensionEvent) {
        event.player.doWithCap<BioPlayer> { cap ->
            cap.sendAllEffects(event.player)
        }
    }

    @JvmStatic
    @SubscribeEvent
    fun onEntityTick(event: LivingEvent.LivingUpdateEvent) {
        when(event.entity){
            //for players
            is PlayerEntity -> {
                val player = event.entity as PlayerEntity
                player.doWithCap<BioPlayer> { cap ->
                    cap.onUpdate(player)
                }
            }
            //for others
            is LivingEntity -> {
                val entity = event.entityLiving
                entity.doWithCap<BioMob> { cap ->
                    cap.onUpdate(entity)
                }
            }
        }
    }
}