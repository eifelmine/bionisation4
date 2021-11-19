package com.eifel.bionisation4.common.event

import com.eifel.bionisation4.common.storage.capability.entity.BioMob
import com.eifel.bionisation4.common.storage.capability.entity.BioMobProvider
import com.eifel.bionisation4.common.storage.capability.player.BioPlayer
import com.eifel.bionisation4.common.storage.capability.player.BioPlayerProvider
import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraftforge.event.AttachCapabilitiesEvent
import net.minecraftforge.event.entity.living.LivingEvent
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
    fun onEntityTick(event: LivingEvent.LivingUpdateEvent) {
        when(event.entity){
            //for players
            is PlayerEntity -> {
                val player = event.entity as PlayerEntity

            }
            //for others
            is LivingEntity -> {
                val entity = event.entityLiving

            }
        }
    }
}