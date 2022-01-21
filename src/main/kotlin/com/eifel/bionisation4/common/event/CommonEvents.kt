package com.eifel.bionisation4.common.event

import com.eifel.bionisation4.api.laboratory.registry.EffectRegistry
import com.eifel.bionisation4.api.laboratory.registry.EffectTriggers
import com.eifel.bionisation4.api.util.Utils
import com.eifel.bionisation4.common.config.ConfigProperties
import com.eifel.bionisation4.common.extensions.*
import com.eifel.bionisation4.common.storage.capability.entity.BioStat
import com.eifel.bionisation4.common.storage.capability.entity.BioStatProvider
import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraftforge.event.AttachCapabilitiesEvent
import net.minecraftforge.event.entity.living.*
import net.minecraftforge.event.entity.player.PlayerEvent
import net.minecraftforge.event.entity.player.PlayerInteractEvent
import net.minecraftforge.event.world.BlockEvent
import net.minecraftforge.eventbus.api.SubscribeEvent

object CommonEvents {

    @JvmStatic
    @SubscribeEvent
    fun onAttachCapabilitiesEvent(event: AttachCapabilitiesEvent<Entity>) {
         if(event.`object` is LivingEntity)
             event.addCapability(BioStat.PROP, BioStatProvider())
    }

    @JvmStatic
    @SubscribeEvent
    fun onPlayerClone(event: PlayerEvent.Clone) {
        if (!event.player.level.isClientSide) {
            if (!event.isWasDeath || (event.isWasDeath && ConfigProperties.saveAfterDeath.get())) {
                event.player.doWithCap { newCap ->
                    event.original.doWithCap { oldCap ->
                        newCap.readFromNBT(oldCap.writeToNBT())
                    }
                }
            }
        }
    }

    @JvmStatic
    @SubscribeEvent
    fun onEntitySpawn(event: LivingSpawnEvent.SpecialSpawn) {
        if(!event.entityLiving.level.isClientSide){
            val occ = EffectRegistry.getOccasions()
            if(occ.containsKey(event.entityLiving.type)){
                val data = occ[event.entityLiving.type]!!
                data.forEach { (t, u) ->
                    if(Utils.chance(u)){
                        event.entityLiving.addEffect(EffectRegistry.getEffectInstance(t).getCopy())
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
                EffectTriggers.getTriggers<LivingHurtEvent>().forEach { it.trigger(event) }
                val damageSource = event.source
                (damageSource.directEntity as? LivingEntity)?.let { source ->
                    victim.doWithCap { cap ->
                        cap.effects.forEach {
                            it.onHurt(event, victim, source)
                        }
                    }
                }
            }
        }
    }

    @JvmStatic
    @SubscribeEvent
    fun onEntityBeingHurt(event: LivingAttackEvent) {
        val target = event.entityLiving
        target?.let { victim ->
            if(!victim.level.isClientSide) {
                EffectTriggers.getTriggers<LivingAttackEvent>().forEach { it.trigger(event) }
                val damageSource = event.source
                (damageSource.directEntity as? LivingEntity)?.let { source ->
                    source.doWithCap { cap ->
                        cap.effects.forEach {
                            it.onAttack(event, victim, source)
                        }
                    }
                }
            }
        }
    }

    @JvmStatic
    @SubscribeEvent
    fun onEntityAttack(event: LivingAttackEvent) {
        val target = event.entityLiving
        target?.let { victim ->
            if(!victim.level.isClientSide) {
                EffectTriggers.getTriggers<LivingAttackEvent>().forEach { it.trigger(event) }
            }
        }
    }

    @JvmStatic
    @SubscribeEvent
    fun onPlayerBreakBlock(event: BlockEvent.BreakEvent) {
        val source = event.player
        source?.let { player ->
            if(!player.level.isClientSide) {
                EffectTriggers.getTriggers<BlockEvent.BreakEvent>().forEach { it.trigger(event) }
            }
        }
    }

    @JvmStatic
    @SubscribeEvent
    fun onPlayerInteractEntity(event: PlayerInteractEvent.EntityInteract) {
        val source = event.player
        source?.let { player ->
            if(!player.level.isClientSide) {
                EffectTriggers.getTriggers<PlayerInteractEvent.EntityInteract>().forEach { it.trigger(event) }
            }
        }
    }

    @JvmStatic
    @SubscribeEvent
    fun onEntityDeath(event: LivingDeathEvent) {
        val target = event.entityLiving
        target?.let { victim ->
            if(!victim.level.isClientSide) {
                EffectTriggers.getTriggers<LivingDeathEvent>().forEach { it.trigger(event) }
                victim.doWithCap { cap ->
                    cap.effects.forEach {
                        it.onDeath(event, victim)
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
            EffectTriggers.getTriggers<LivingEvent.LivingUpdateEvent>().forEach { it.trigger(event) }
            when (event.entity) {
                is LivingEntity -> {
                    val entity = event.entityLiving
                    entity.doWithCap { cap ->
                        cap.onUpdate(entity)
                        if (cap.ticker % 12000 == 0)
                            cap.modifyBlood(entity, 2)
                    }
                }
            }
        }
    }

    @JvmStatic
    @SubscribeEvent
    fun onItemUse(event: LivingEntityUseItemEvent.Start) {
        val source = event.entityLiving
        source?.let { entity ->
            if (!entity.level.isClientSide)
                EffectTriggers.getTriggers<LivingEntityUseItemEvent.Start>().forEach { it.trigger(event) }
        }
    }

    @JvmStatic
    @SubscribeEvent
    fun onItemUse(event: LivingEntityUseItemEvent.Stop) {
        val source = event.entityLiving
        source?.let { entity ->
            if (!entity.level.isClientSide)
                EffectTriggers.getTriggers<LivingEntityUseItemEvent.Stop>().forEach { it.trigger(event) }
        }
    }

    @JvmStatic
    @SubscribeEvent
    fun onItemUse(event: LivingEntityUseItemEvent.Finish) {
        val source = event.entityLiving
        source?.let { entity ->
            if (!entity.level.isClientSide)
                EffectTriggers.getTriggers<LivingEntityUseItemEvent.Finish>().forEach { it.trigger(event) }
        }
    }

    @JvmStatic
    @SubscribeEvent
    fun onItemUse(event: LivingEntityUseItemEvent.Tick) {
        val source = event.entityLiving
        source?.let { entity ->
            if (!entity.level.isClientSide)
                EffectTriggers.getTriggers<LivingEntityUseItemEvent.Tick>().forEach { it.trigger(event) }
        }
    }
}