package com.eifel.bionisation4.api.laboratory.registry

import com.eifel.bionisation4.common.extensions.toTypedList
import net.minecraftforge.event.entity.living.LivingEvent
import net.minecraftforge.eventbus.api.Event
import kotlin.reflect.KClass

object EffectTriggers {

    val REGISTERED_TRIGGERS = mutableListOf<IEffectTrigger<out Event>>()

    fun init() {
        addTrigger(object: IEffectTrigger<LivingEvent.LivingUpdateEvent>{

            override fun getId() = 0
            override fun getEventClass(): KClass<LivingEvent.LivingUpdateEvent> = LivingEvent.LivingUpdateEvent::class
            override fun trigger(event: LivingEvent.LivingUpdateEvent) {

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