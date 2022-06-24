package com.eifel.bionisation4.common.laboratory.gene.species.type

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.species.Gene
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.Mob
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal
import net.minecraft.world.entity.monster.Monster
import kotlin.streams.toList

class Peaceful(): Gene(InternalConstants.GENE_PEACEFUL_ID, "Peaceful", true) {

    private var wasAIReplaced = false

    override fun perform(entity: LivingEntity, effect: AbstractEffect) {
        super.perform(entity, effect)
        if(!wasAIReplaced){
            when(entity){
                is Monster -> {
                    applier(entity)
                }
                is Mob -> {
                    applier(entity)
                }
            }
            wasAIReplaced = true
        }
    }

    private val applier: (Mob) -> Unit = { entity ->
        entity.targetSelector.runningGoals.toList().filter { it is NearestAttackableTargetGoal<*> || it is HurtByTargetGoal }.forEach {
            entity.targetSelector.removeGoal(it)
        }
    }

    override fun getCopy() = Peaceful()
}