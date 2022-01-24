package com.eifel.bionisation4.api.laboratory.registry

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.util.Utils
import com.eifel.bionisation4.common.extensions.*
import com.eifel.bionisation4.common.laboratory.bacteria.*
import com.eifel.bionisation4.common.laboratory.common.effect.*
import com.eifel.bionisation4.common.laboratory.virus.Aer
import net.minecraft.block.Blocks
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.monster.*
import net.minecraft.entity.monster.piglin.PiglinEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Items
import net.minecraft.util.DamageSource
import net.minecraft.world.biome.Biome
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent
import net.minecraftforge.event.entity.living.LivingEvent
import net.minecraftforge.event.entity.living.LivingHurtEvent
import net.minecraftforge.event.world.BlockEvent
import net.minecraftforge.eventbus.api.Event
import kotlin.reflect.KClass

object EffectTriggers {

    val REGISTERED_TRIGGERS = mutableListOf<IEffectTrigger<out Event>>()

    fun init() {
        addTrigger(object: IEffectTrigger<LivingEvent.LivingUpdateEvent>{

            override fun getId() = 0
            override fun getEventClass(): KClass<LivingEvent.LivingUpdateEvent> = LivingEvent.LivingUpdateEvent::class
            override fun trigger(event: LivingEvent.LivingUpdateEvent) {
                when(event.entityLiving){
                    is PlayerEntity -> {
                        val player = event.entityLiving as PlayerEntity
                        //sunstroke
                        if (player.isInBiome(Biome.Category.DESERT) && Utils.chance(EffectRegistry.getEffectChance(InternalConstants.EFFECT_SUNSTROKE_ID)))
                            if(player.inventory.armor[3].isEmpty)
                                player.addEffect(Sunstroke())

                        if (player.getBioTicker() % 1200 == 0 && player.level.getBlockState(player.blockPosition().offset(0, -1, 0)).block == Blocks.MYCELIUM && Utils.chance(EffectRegistry.getEffectChance(InternalConstants.BACTERIA_MYCELIUM_ID)))
                            if(player.inventory.armor[0].isEmpty)
                                player.addEffect(Mycelium())
                    }
                }
                //fatigue
                if(event.entityLiving.getImmunity() < 30)
                    event.entityLiving.addEffect(Fatigue())
                //lack of blood
                if(event.entityLiving.getBlood() < 40)
                    event.entityLiving.addEffect(LackOfBlood())
                //cold
                if ((event.entityLiving.isInBiome(Biome.Category.ICY) || event.entityLiving.isInBiome(Biome.Category.TAIGA)) && Utils.chance(EffectRegistry.getEffectChance(InternalConstants.EFFECT_COLD_ID)) && !event.entityLiving.hasArmor(true) && event.entityLiving.getImmunity() < 80)
                    event.entityLiving.addEffect(Cold())
                //aer
                if(event.entityLiving.getImmunity() < 40 && event.entityLiving.getBioTicker() % 1200 == 0 && Utils.chance(EffectRegistry.getEffectChance(InternalConstants.VIRUS_AER_ID)))
                    event.entityLiving.addEffect(Aer())
                //swamp
                if(event.entityLiving.getBioTicker() % 3600 == 0 && event.entityLiving.isInBiome(Biome.Category.SWAMP) && Utils.chance(EffectRegistry.getEffectChance(InternalConstants.BACTERIA_SWAMP_ID)) && !event.entityLiving.hasBioArmor(true))
                    event.entityLiving.addEffect(Swamp())
                //water
                if(event.entityLiving.getImmunity() < 80 && event.entityLiving.getBioTicker() % 1200 == 0 && Utils.chance(EffectRegistry.getEffectChance(InternalConstants.BACTERIA_WATER_ID)))
                    event.entityLiving.addEffect(Water())
            }
        })
        addTrigger(object: IEffectTrigger<LivingEntityUseItemEvent.Finish>{

            override fun getId() = 1
            override fun getEventClass(): KClass<LivingEntityUseItemEvent.Finish> = LivingEntityUseItemEvent.Finish::class
            override fun trigger(event: LivingEntityUseItemEvent.Finish) {
                when(event.entityLiving){
                    is PlayerEntity -> {
                        val player = event.entityLiving as PlayerEntity
                        when(event.resultStack.item){
                            //sunstroke
                            Items.GLASS_BOTTLE -> player.expire(InternalConstants.EFFECT_SUNSTROKE_ID)
                            //black
                            Items.SPIDER_EYE -> {
                                if(Utils.chance(EffectRegistry.getEffectChance(InternalConstants.BACTERIA_BLACK_ID)))
                                    player.addEffect(Black())
                            }
                            //sea
                            Items.TROPICAL_FISH -> {
                                if(Utils.chance(EffectRegistry.getEffectChance(InternalConstants.BACTERIA_SEA_ID)))
                                    player.addEffect(Sea())
                            }
                            //food poisoning
                            in FoodPoisoning.poison -> if(Utils.chance(EffectRegistry.getEffectChance(InternalConstants.EFFECT_FOOD_POISONING_ID))) player.addEffect(FoodPoisoning())
                        }
                    }
                }
            }
        })
        addTrigger(object: IEffectTrigger<LivingEntityUseItemEvent.Tick>{

            override fun getId() = 2
            override fun getEventClass(): KClass<LivingEntityUseItemEvent.Tick> = LivingEntityUseItemEvent.Tick::class
            override fun trigger(event: LivingEntityUseItemEvent.Tick) {
                when(event.entityLiving){
                    is PlayerEntity -> {
                        val player = event.entityLiving as PlayerEntity
                        //skull virus
                        if(player.isEffectActive(InternalConstants.VIRUS_SKULL_ID))
                            event.isCanceled = true
                    }
                }
            }
        })
        addTrigger(object: IEffectTrigger<LivingHurtEvent>{

            override fun getId() = 3
            override fun getEventClass(): KClass<LivingHurtEvent> = LivingHurtEvent::class
            override fun trigger(event: LivingHurtEvent) {
                event.entityLiving?.let { victim ->
                    //bleeding
                    if(Utils.chance(EffectRegistry.getEffectChance(InternalConstants.EFFECT_BLEEDING_ID)))
                        victim.addEffect(Bleeding())
                    //fracture
                    if(event.source == DamageSource.FALL && Utils.chance(EffectRegistry.getEffectChance(InternalConstants.EFFECT_FRACTURE_ID)))
                        victim.addEffect(Fracture())
                    //lack of air
                    if(event.source.directEntity is DrownedEntity && Utils.chance(EffectRegistry.getEffectChance(InternalConstants.EFFECT_LACK_OF_AIR_ID)))
                        victim.addEffect(LackOfAir())
                    //nether atmosphere
                    if(event.source.directEntity is PiglinEntity && Utils.chance(EffectRegistry.getEffectChance(InternalConstants.EFFECT_NETHER_ATMOSPHERE_ID)))
                        victim.addEffect(NetherAtmosphere())
                    //alienation
                    if(event.source.directEntity is WitherSkeletonEntity && Utils.chance(EffectRegistry.getEffectChance(InternalConstants.EFFECT_ALIENATION_ID)))
                        victim.addEffect(Alienation())
                    //nightmares
                    if(event.source.directEntity is PhantomEntity && Utils.chance(EffectRegistry.getEffectChance(InternalConstants.EFFECT_NIGHTMARES_ID)))
                        victim.addEffect(Nightmares())
                    if(event.source.directEntity is LivingEntity) {
                        val attacker = event.source.directEntity as LivingEntity
                        //black
                        if (victim is SpiderEntity && Utils.chance(EffectRegistry.getEffectChance(InternalConstants.BACTERIA_BLACK_ID)))
                            if(attacker.mainHandItem.isEmpty)
                                attacker.addEffect(Black())
                        //ender
                        if (victim is EndermanEntity && Utils.chance(EffectRegistry.getEffectChance(InternalConstants.BACTERIA_ENDER_ID)))
                            if(attacker.mainHandItem.isEmpty)
                                attacker.addEffect(Ender())
                    }
                    //glowing
                    if(event.source.directEntity is BlazeEntity && Utils.chance(EffectRegistry.getEffectChance(InternalConstants.BACTERIA_GLOWING_ID)))
                        victim.addEffect(Glowing())
                    //glowing
                    if(event.source == DamageSource.CACTUS && Utils.chance(EffectRegistry.getEffectChance(InternalConstants.BACTERIA_CACTUS_ID)))
                        victim.addEffect(Cactus())
                    //bone
                    if(event.source.directEntity is AbstractSkeletonEntity && Utils.chance(EffectRegistry.getEffectChance(InternalConstants.BACTERIA_BONE_ID)))
                        victim.addEffect(Bone())
                }
            }
        })
        addTrigger(object: IEffectTrigger<BlockEvent.BreakEvent>{

            override fun getId() = 4
            override fun getEventClass(): KClass<BlockEvent.BreakEvent> = BlockEvent.BreakEvent::class
            override fun trigger(event: BlockEvent.BreakEvent) {
                event.player?.let { miner ->
                    when(event.state.block){
                        Blocks.GLOWSTONE -> {
                            if(Utils.chance(EffectRegistry.getEffectChance(InternalConstants.BACTERIA_GLOWING_ID) / 3))
                                miner.addEffect(Glowing())
                        }
                    }
                    if(miner.mainHandItem.isEmpty){
                        if(Utils.chance(EffectRegistry.getEffectChance(InternalConstants.BACTERIA_TERRA_ID)))
                            miner.addEffect(Terra())
                    }
                }
            }
        })
    }

    inline fun <reified T: Event> getTriggers(): List<IEffectTrigger<T>> = REGISTERED_TRIGGERS.filter{ it.getEventClass() == T::class }
        .toTypedList()
    fun <T: Event> addTrigger(trigger: IEffectTrigger<T>) = REGISTERED_TRIGGERS.add(trigger)
    fun removeTrigger(id: Int) = REGISTERED_TRIGGERS.removeIf { it.getId() == id }
}

interface IEffectTrigger<T: Event> {

    fun getId(): Int
    fun getEventClass(): KClass<T>
    fun trigger(event: T)
}

//LivingEvent.LivingUpdateEvent
//LivingHurtEvent
//LivingAttackEvent
//LivingEntityUseItemEvent.Start
//LivingEntityUseItemEvent.Stop
//LivingEntityUseItemEvent.Finish
//LivingEntityUseItemEvent.Tick
//LivingAttackEvent
//LivingDeathEvent
//PlayerInteractEvent.EntityInteract
//BlockEvent.BreakEvent