package com.eifel.bionisation4.common.event

import com.eifel.bionisation4.api.laboratory.registry.EffectRegistry
import com.eifel.bionisation4.api.laboratory.registry.EffectTriggers
import com.eifel.bionisation4.api.util.Utils
import com.eifel.bionisation4.common.config.ConfigProperties
import com.eifel.bionisation4.common.extensions.*
import com.eifel.bionisation4.common.laboratory.virus.Wild
import com.eifel.bionisation4.common.storage.capability.entity.BioStat
import com.eifel.bionisation4.common.storage.capability.entity.BioStatProvider
import net.minecraft.world.Containers
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.LivingEntity
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent
import net.minecraftforge.event.AttachCapabilitiesEvent
import net.minecraftforge.event.entity.living.*
import net.minecraftforge.event.entity.player.PlayerEvent
import net.minecraftforge.event.entity.player.PlayerInteractEvent
import net.minecraftforge.event.level.BlockEvent
import net.minecraftforge.eventbus.api.SubscribeEvent

object CommonEvents {

    var spawnedEntities = 1

    @JvmStatic
    @SubscribeEvent
    fun onRegisterCapabilities(event: RegisterCapabilitiesEvent) {
        event.register(BioStat::class.java)
    }

    @JvmStatic
    @SubscribeEvent
    fun onAttachCapabilitiesEvent(event: AttachCapabilitiesEvent<Entity>) {
         if(event.`object` is LivingEntity)
             event.addCapability(BioStat.PROP, BioStatProvider())
    }

    @JvmStatic
    @SubscribeEvent
    fun onPlayerClone(event: PlayerEvent.Clone) {
        if (!event.entity.level.isClientSide) {
            if (!event.isWasDeath || (event.isWasDeath && ConfigProperties.saveAfterDeath.get())) {
                event.entity.doWithCap { newCap ->
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
        if(!event.entity.level.isClientSide){
            spawnedEntities++
            val occasionsDefault = EffectRegistry.getOccasions()
            if(occasionsDefault.containsKey(event.entity.type)){
                val data = occasionsDefault[event.entity.type]!!
                data.forEach { (t, u) ->
                    if(Utils.chance(u))
                        event.entity.addEffect(EffectRegistry.getMobEffectInstance(t).getCopy())
                }
            }
            val occasionsClass = EffectRegistry.getOccasionsClass()
            occasionsClass.keys.filter { it.isAssignableFrom(event.entity.javaClass) }.forEach {
                val data = occasionsClass[it]!!
                data.forEach { (t, u) ->
                    if(Utils.chance(u))
                        event.entity.addEffect(EffectRegistry.getMobEffectInstance(t).getCopy())
                }
            }
            if(spawnedEntities % ConfigProperties.randomVirusMobCount.get() == 0 && ConfigProperties.randomVirusCreation.get()){
                if(Utils.chance(ConfigProperties.randomVirusSpawnChance.get())) {
                    val wild = Wild().apply { loadRandomProperties() }
                    if(wild.effectGenes.isNotEmpty())
                        event.entity.addEffect(wild)
                }
            }
        }
    }

    @JvmStatic
    @SubscribeEvent
    fun onPlayerLogIn(event: PlayerEvent.PlayerLoggedInEvent) {
        if(!event.entity.level.isClientSide)
            event.entity.updateToClient()
    }

    @JvmStatic
    @SubscribeEvent
    fun onPlayerRespawn(event: PlayerEvent.PlayerRespawnEvent) {
        if(!event.entity.level.isClientSide) {
            event.entity.setBlood(100)
            event.entity.setImmunity(100)
            event.entity.updateToClient()
        }
    }

    @JvmStatic
    @SubscribeEvent
    fun onEntityBeingHurt(event: LivingHurtEvent) {
        val target = event.entity
        target?.let { victim ->
            if(!victim.level.isClientSide) {
                EffectTriggers.getTriggers<LivingHurtEvent>().forEach { it.trigger(event) }
                victim.doWithCap { cap ->
                    cap.effects.forEach {
                        it.onHurt(event, victim)
                    }
                }
            }
        }
    }

    @JvmStatic
    @SubscribeEvent
    fun onEntityBeingHurt(event: LivingAttackEvent) {
        val target = event.entity
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
        val target = event.entity
        target?.let { victim ->
            if(!victim.level.isClientSide)
                EffectTriggers.getTriggers<LivingAttackEvent>().forEach { it.trigger(event) }
        }
    }

    @JvmStatic
    @SubscribeEvent
    fun onPlayerBreakBlock(event: BlockEvent.BreakEvent) {
        val source = event.player
        source?.let { player ->
            if(!player.level.isClientSide)
                EffectTriggers.getTriggers<BlockEvent.BreakEvent>().forEach { it.trigger(event) }
        }
    }

    @JvmStatic
    @SubscribeEvent
    fun onPlayerInteractEntity(event: PlayerInteractEvent.EntityInteract) {
        val source = event.entity
        source?.let { player ->
            if(!player.level.isClientSide)
                EffectTriggers.getTriggers<PlayerInteractEvent.EntityInteract>().forEach { it.trigger(event) }
        }
    }

    @JvmStatic
    @SubscribeEvent
    fun onEntityDeath(event: LivingDeathEvent) {
        val target = event.entity
        target?.let { victim ->
            if(!victim.level.isClientSide) {
                EffectTriggers.getTriggers<LivingDeathEvent>().forEach { it.trigger(event) }
                victim.doWithCap { cap ->
                    cap.effects.forEach {
                        it.onDeath(event, victim)
                    }
                }
                if(EffectRegistry.getDrops().containsKey(victim.type)){
                    val data = EffectRegistry.getDrops()[victim.type]!!
                    if(Utils.chance(data.first))
                        Containers.dropItemStack(victim.level, victim.x, victim.y, victim.z, data.second.copy())
                }
            }
        }
    }

    @JvmStatic
    @SubscribeEvent
    fun onPlayerChangedDimension(event: PlayerEvent.PlayerChangedDimensionEvent) {
        if(!event.entity.level.isClientSide)
            event.entity.updateToClient()
    }

    @JvmStatic
    @SubscribeEvent
    fun onEntityTick(event: LivingEvent.LivingTickEvent) {
        if(!event.entity.level.isClientSide) {
            EffectTriggers.getTriggers<LivingEvent.LivingTickEvent>().forEach { it.trigger(event) }
            when (event.entity) {
                is LivingEntity -> {
                    val entity = event.entity
                    entity.doWithCap { cap ->
                        cap.onUpdate(entity)
                        if (cap.ticker % 12000 == 0)
                            cap.modifyBlood(entity, 2)
                        //run clean list
                        val type = entity.type
                        if(EffectRegistry.getCleanTypes().contains(type))
                            entity.getEffects().forEach { cap.expire(it) }
                    }
                }
            }
        }
    }

    @JvmStatic
    @SubscribeEvent
    fun onItemUse(event: LivingEntityUseItemEvent.Start) {
        val source = event.entity
        source?.let { entity ->
            if (!entity.level.isClientSide)
                EffectTriggers.getTriggers<LivingEntityUseItemEvent.Start>().forEach { it.trigger(event) }
        }
    }

    @JvmStatic
    @SubscribeEvent
    fun onItemUse(event: LivingEntityUseItemEvent.Stop) {
        val source = event.entity
        source?.let { entity ->
            if (!entity.level.isClientSide)
                EffectTriggers.getTriggers<LivingEntityUseItemEvent.Stop>().forEach { it.trigger(event) }
        }
    }

    @JvmStatic
    @SubscribeEvent
    fun onItemUse(event: LivingEntityUseItemEvent.Finish) {
        val source = event.entity
        source?.let { entity ->
            if (!entity.level.isClientSide)
                EffectTriggers.getTriggers<LivingEntityUseItemEvent.Finish>().forEach { it.trigger(event) }
        }
    }

    @JvmStatic
    @SubscribeEvent
    fun onItemUse(event: LivingEntityUseItemEvent.Tick) {
        val source = event.entity
        source?.let { entity ->
            if (!entity.level.isClientSide)
                EffectTriggers.getTriggers<LivingEntityUseItemEvent.Tick>().forEach { it.trigger(event) }
        }
    }
}