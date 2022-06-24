package com.eifel.bionisation4.common.laboratory.gene.species.type

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.species.Gene
import com.eifel.bionisation4.util.ai.NearestTargetGoal
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal
import net.minecraft.world.entity.animal.Animal
import net.minecraft.world.entity.monster.Monster
import net.minecraft.world.entity.player.Player
import kotlin.streams.toList

class Aggressive(): Gene(InternalConstants.GENE_AGGRESSIVE_ID, "Aggressive", true) {

    private var wasAIReplaced = false
    var radius = 15

    override fun perform(entity: LivingEntity, effect: AbstractEffect) {
        super.perform(entity, effect)
        if(!wasAIReplaced){
            when(entity){
                is Monster -> {
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

    override fun fromNBT(nbtData: CompoundTag) {
        super.fromNBT(nbtData)
        this.radius = nbtData.getInt(InternalConstants.GENE_RADIUS_KEY)
    }

    private val aggressiveApplier: (Monster) -> Unit = {  entity ->
        val aggressiveGoalMonster = NearestTargetGoal(entity, Monster::class.java, radius, canSee = true, canReach = true) { it is Monster }
        val aggressiveGoalPlayer = NearestTargetGoal(entity, Player::class.java, radius, canSee = true, canReach = true) { it is Player }
        val aggressiveGoalAnimal = NearestTargetGoal(entity, Animal::class.java, radius, canSee = true, canReach = true) { it is Animal }
        entity.targetSelector.runningGoals.toList().filter { it is NearestAttackableTargetGoal<*> || it is AvoidEntityGoal<*> }.forEach {
            entity.targetSelector.removeGoal(it)
        }
        entity.targetSelector.addGoal(4, aggressiveGoalMonster)
        entity.targetSelector.addGoal(4, aggressiveGoalPlayer)
        entity.targetSelector.addGoal(4, aggressiveGoalAnimal)
    }

    override fun getCopy() = Aggressive()
}