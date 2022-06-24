package com.eifel.bionisation4.util.lab

import com.eifel.bionisation4.api.laboratory.registry.EffectRegistry
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.common.extensions.*
import net.minecraft.core.BlockPos
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.level.Level
import net.minecraft.world.phys.AABB

object EffectUtils {

    fun spreadEffect(effect: AbstractEffect, world: Level, pos: BlockPos, radius: Double, predicate: (Entity) -> Boolean = { e -> e is LivingEntity }) =
        world.getEntities(null, AABB(pos).inflate(radius)) { predicate(it) }
            .toTypedList<LivingEntity>().forEach {
                if(!it.hasBioArmor(true))
                    it.addEffect(effect.getCopy())
            }

    fun applyToEntities(world: Level, pos: BlockPos, radius: Double, predicate: (Entity) -> Boolean = { e -> e is LivingEntity }, action: (Entity) -> Unit = {_ ->}) =
        world.getEntities(null, AABB(pos).inflate(radius)) { predicate(it) }
            .toTypedList<LivingEntity>().forEach {
                action(it)
            }

    fun symbiosisCheckAndTrigger(entity: LivingEntity){
        val symbiosis = EffectRegistry.getSymbiosis()
        if(symbiosis.isNotEmpty()) {
            symbiosis.forEach { data ->
                if (entity.isEffectActive(data.first) && entity.isEffectActive(data.second)) {
                    entity.expire(data.first)
                    entity.expire(data.second)
                    entity.addEffect(EffectRegistry.getMobEffectInstance(data.third).getCopy())
                }
            }
        }
    }

    fun getPowerFromImmunity(immunity: Int): Int = if (immunity < 20) 4 else if (immunity < 40) 3 else if (immunity < 60) 2 else if (immunity < 80) 1 else if (immunity < 90) 0 else 0
}