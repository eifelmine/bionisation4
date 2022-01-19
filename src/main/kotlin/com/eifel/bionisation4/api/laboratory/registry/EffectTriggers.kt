package com.eifel.bionisation4.api.laboratory.registry

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.util.Utils
import com.eifel.bionisation4.common.extensions.*
import com.eifel.bionisation4.common.laboratory.common.effect.*
import net.minecraft.entity.monster.DrownedEntity
import net.minecraft.entity.monster.PhantomEntity
import net.minecraft.entity.monster.WitherSkeletonEntity
import net.minecraft.entity.monster.piglin.PiglinEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Items
import net.minecraft.util.DamageSource
import net.minecraft.world.biome.Biome
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent
import net.minecraftforge.event.entity.living.LivingEvent
import net.minecraftforge.event.entity.living.LivingHurtEvent
import net.minecraftforge.eventbus.api.Event
import kotlin.reflect.KClass

object EffectTriggers {

    val REGISTERED_TRIGGERS = mutableListOf<IEffectTrigger<out Event>>()

    fun init() {
        addTrigger(object: IEffectTrigger<LivingEvent.LivingUpdateEvent>{

            override fun getId() = 0
            override fun getEventClass(): KClass<LivingEvent.LivingUpdateEvent> = LivingEvent.LivingUpdateEvent::class
            override fun trigger(event: LivingEvent.LivingUpdateEvent) {
                if(event.entityLiving is PlayerEntity){
                    if(event.entityLiving.getBioTicker() % 1200 == 0) {
                        //sunstroke
                        if (event.entityLiving.isInBiome(Biome.Category.DESERT) && Utils.chance(EffectRegistry.getEffectChance(InternalConstants.EFFECT_SUNSTROKE_ID)))
                            event.entityLiving.addEffect(Sunstroke())
                        //cold
                        if ((event.entityLiving.isInBiome(Biome.Category.ICY) || event.entityLiving.isInBiome(Biome.Category.TAIGA)) && Utils.chance(EffectRegistry.getEffectChance(InternalConstants.EFFECT_COLD_ID)) && !event.entityLiving.hasArmor(true) && event.entityLiving.getImmunity() < 80)
                            event.entityLiving.addEffect(Cold())
                    }
                    //fatigue
                    if(event.entityLiving.getImmunity() < 30)
                        event.entityLiving.addEffect(Fatigue())
                    //lack of blood
                    if(event.entityLiving.getBlood() < 40)
                        event.entityLiving.addEffect(LackOfBlood())
                }
            }
        })
        addTrigger(object: IEffectTrigger<LivingEntityUseItemEvent.Finish>{

            override fun getId() = 1
            override fun getEventClass(): KClass<LivingEntityUseItemEvent.Finish> = LivingEntityUseItemEvent.Finish::class
            override fun trigger(event: LivingEntityUseItemEvent.Finish) {
                if(event.entityLiving is PlayerEntity){
                    println(event.resultStack.item)
                    when(event.resultStack.item){
                        //sunstroke
                        Items.GLASS_BOTTLE -> event.entityLiving.expire(InternalConstants.EFFECT_SUNSTROKE_ID)
                        //food poisoning
                        in FoodPoisoning.poison -> if(Utils.chance(EffectRegistry.getEffectChance(InternalConstants.EFFECT_FOOD_POISONING_ID))) event.entityLiving.addEffect(FoodPoisoning())
                    }
                }
            }
        })
        addTrigger(object: IEffectTrigger<LivingHurtEvent>{

            override fun getId() = 2
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
//LivingEntityUseItemEvent.Start
//LivingEntityUseItemEvent.Stop
//LivingEntityUseItemEvent.Finish
//LivingEntityUseItemEvent.Tick
//LivingAttackEvent
//LivingDeathEvent
//PlayerInteractEvent.EntityInteract
//BlockEvent.BreakEvent