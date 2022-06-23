package com.eifel.bionisation4.common.laboratory.gene.species.type

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.species.Gene
import com.eifel.bionisation4.util.ai.NearestTargetGoal
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.ai.goal.AvoidEntityGoal
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal
import net.minecraft.entity.monster.MonsterEntity
import net.minecraft.entity.passive.AnimalEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.nbt.CompoundNBT
import kotlin.streams.toList

class Aggressive(): Gene(InternalConstants.GENE_AGGRESSIVE_ID, "Aggressive", true) {

    private var wasAIReplaced = false
    var radius = 15

    override fun perform(entity: LivingEntity, effect: AbstractEffect) {
        super.perform(entity, effect)
        if(!wasAIReplaced){
            when(entity){
                is MonsterEntity -> {
                    if(entity.targetSelector.runningGoals.anyMatch { it.goal is NearestTargetGoal<*> })
                        return
                    aggressiveApplier(entity)
                }
            }
            wasAIReplaced = true
        }
    }

    fun setRadius(radius: Int): Aggressive {
        this.radius = radius
        return this
    }

    override fun toNBT() = super.toNBT().apply {
        putInt(InternalConstants.GENE_RADIUS_KEY, radius)
    }

    override fun fromNBT(nbtData: CompoundNBT) {
        super.fromNBT(nbtData)
        this.radius = nbtData.getInt(InternalConstants.GENE_RADIUS_KEY)
    }

    private val aggressiveApplier: (MonsterEntity) -> Unit = {  entity ->
        val aggressiveGoalMonster = NearestTargetGoal(entity, MonsterEntity::class.java, radius, canSee = true, canReach = true) { it is MonsterEntity }
        val aggressiveGoalPlayer = NearestTargetGoal(entity, PlayerEntity::class.java, radius, canSee = true, canReach = true) { it is PlayerEntity }
        val aggressiveGoalAnimal = NearestTargetGoal(entity, AnimalEntity::class.java, radius, canSee = true, canReach = true) { it is AnimalEntity }
        entity.targetSelector.runningGoals.toList().filter { it is NearestAttackableTargetGoal<*> || it is AvoidEntityGoal<*> }.forEach {
            entity.targetSelector.removeGoal(it)
        }
        entity.targetSelector.addGoal(4, aggressiveGoalMonster)
        entity.targetSelector.addGoal(4, aggressiveGoalPlayer)
        entity.targetSelector.addGoal(4, aggressiveGoalAnimal)
    }

    override fun getCopy() = Aggressive()
}