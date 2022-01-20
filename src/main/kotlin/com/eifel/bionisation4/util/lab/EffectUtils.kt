package com.eifel.bionisation4.util.lab

import com.eifel.bionisation4.api.laboratory.registry.EffectRegistry
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.common.extensions.addEffect
import com.eifel.bionisation4.common.extensions.expire
import com.eifel.bionisation4.common.extensions.isEffectActive
import com.eifel.bionisation4.common.extensions.toTypedList
import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.util.math.AxisAlignedBB
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

object EffectUtils {

    fun spreadEffect(effect: AbstractEffect, world: World, pos: BlockPos, radius: Double, predicate: (Entity) -> Boolean = { e -> e is LivingEntity }) =
        world.getEntities(null, AxisAlignedBB(pos).inflate(radius)) { predicate(it) }
            .toTypedList<LivingEntity>().forEach {
                it.addEffect(effect.getCopy())
            }

    fun applyToEntities(world: World, pos: BlockPos, radius: Double, predicate: (Entity) -> Boolean = { e -> e is LivingEntity }, action: (Entity) -> Unit = {_ ->}) =
        world.getEntities(null, AxisAlignedBB(pos).inflate(radius)) { predicate(it) }
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
                    entity.addEffect(EffectRegistry.getEffectInstance(data.third).getCopy())
                }
            }
        }
    }

    fun getPowerFromImmunity(immunity: Int): Int = if (immunity < 20) 4 else if (immunity < 40) 3 else if (immunity < 60) 2 else if (immunity < 80) 1 else if (immunity < 90) 0 else 0
}