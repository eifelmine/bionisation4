package com.eifel.bionisation4.common.laboratory.gene.species.type

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.species.Gene
import com.eifel.bionisation4.util.ai.AnimalMeleeAttack
import net.minecraft.entity.CreatureEntity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.MobEntity
import net.minecraft.entity.ai.goal.*
import net.minecraft.entity.passive.AnimalEntity
import net.minecraft.nbt.CompoundNBT
import kotlin.streams.toList

class Aggressive(): Gene(InternalConstants.GENE_AGGRESSIVE_ID, "Aggressive", true) {

    private var wasAIReplaced = false
    var radius = 15

    override fun perform(entity: LivingEntity, effect: AbstractEffect) {
        super.perform(entity, effect)
        if(!wasAIReplaced){
            when(entity){
                is AnimalEntity -> {
                    val aggressiveGoal = NearestAttackableTargetGoal(entity, LivingEntity::class.java, radius, false, false) { it is LivingEntity }
                    entity.isAggressive = true
                    entity.goalSelector.addGoal(1, LookAtGoal(entity, LivingEntity::class.java, radius.toFloat()))
                    entity.goalSelector.addGoal(3, LeapAtTargetGoal(entity, 0.8f))
                    entity.goalSelector.addGoal(1, LookRandomlyGoal(entity))
                    entity.goalSelector.addGoal(2, AnimalMeleeAttack(entity))
                    entity.targetSelector.addGoal(1, HurtByTargetGoal(entity))
                    entity.targetSelector.addGoal(2, aggressiveGoal)
                }
                is CreatureEntity -> {
                    val aggressiveGoal = NearestAttackableTargetGoal(entity, LivingEntity::class.java, radius, false, false) { it is LivingEntity }
                    entity.targetSelector.runningGoals.toList().filter { it is NearestAttackableTargetGoal<*> || it is AvoidEntityGoal<*> }.forEach {
                        entity.targetSelector.removeGoal(it)
                    }
                    entity.targetSelector.addGoal(4, aggressiveGoal)
                }
                is MobEntity -> {
                    val aggressiveGoal = NearestAttackableTargetGoal(entity, LivingEntity::class.java, radius, false, false) { it is LivingEntity }
                    entity.targetSelector.runningGoals.toList().filter { it is NearestAttackableTargetGoal<*> || it is AvoidEntityGoal<*> }.forEach {
                        entity.targetSelector.removeGoal(it)
                    }
                    entity.targetSelector.addGoal(4, aggressiveGoal)
                }
            }
            wasAIReplaced = true
        }
    }

    fun setRadius(radius: Int): Aggressive {
        this.radius = radius
        return this
    }

    override fun toNBT(): CompoundNBT {
        val data = super.toNBT()
        data.putInt(InternalConstants.GENE_RADIUS_KEY, radius)
        return data
    }

    override fun fromNBT(nbtData: CompoundNBT) {
        super.fromNBT(nbtData)
        this.radius = nbtData.getInt(InternalConstants.GENE_RADIUS_KEY)
    }

    override fun getCopy() = Aggressive()
}