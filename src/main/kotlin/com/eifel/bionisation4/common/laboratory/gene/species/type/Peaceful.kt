package com.eifel.bionisation4.common.laboratory.gene.species.type

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.species.Gene
import net.minecraft.entity.CreatureEntity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.MobEntity
import net.minecraft.entity.ai.goal.HurtByTargetGoal
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal
import kotlin.streams.toList

class Peaceful(): Gene(InternalConstants.GENE_PEACEFUL_ID, "Peaceful", true) {

    private var wasAIReplaced = false

    override fun perform(entity: LivingEntity, effect: AbstractEffect) {
        super.perform(entity, effect)
        if(!wasAIReplaced){
            when(entity){
                is CreatureEntity -> {
                    applier(entity)
                }
                is MobEntity -> {
                    applier(entity)
                }
            }
            wasAIReplaced = true
        }
    }

    private val applier: (MobEntity) -> Unit = { entity ->
        entity.targetSelector.runningGoals.toList().filter { it is NearestAttackableTargetGoal<*> || it is HurtByTargetGoal }.forEach {
            entity.targetSelector.removeGoal(it)
        }
    }

    override fun getCopy() = Peaceful()
}