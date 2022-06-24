package com.eifel.bionisation4.util.ai

import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.Mob
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal
import java.util.function.Predicate

class NearestTargetGoal<T : LivingEntity> (val entity: Mob, clazz: Class<T>, val radius: Int, canSee: Boolean, canReach: Boolean, predicate: Predicate<LivingEntity>?) : NearestAttackableTargetGoal<T>(entity, clazz, radius, canSee, canReach, predicate) {

    val MAX_DURATION = 250
    var duration = MAX_DURATION

    override fun canUse(): Boolean {
        updateDuration()
        if(duration == 0){
            findTarget()
            return target != null
        }
        return false
    }

    fun updateDuration(){
        duration--
        if(duration < 0)
            duration = MAX_DURATION
    }
}