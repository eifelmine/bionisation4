package com.eifel.bionisation4.common.laboratory.virus

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.util.EffectType
import com.eifel.bionisation4.api.util.Utils
import com.eifel.bionisation4.common.laboratory.gene.species.type.AttackSpread
import com.eifel.bionisation4.common.laboratory.gene.species.type.Shadow
import com.eifel.bionisation4.common.laboratory.gene.species.type.Sunburn
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.LivingEntity
import net.minecraftforge.event.entity.living.LivingDeathEvent

class Bat(): AbstractEffect(InternalConstants.VIRUS_BAT_ID, "Bat", EffectType.VIRUS) {

    init {
        isInfinite = true
        canChangePower = true
        isSyncable = true
        isHidden = false

        effectGenes.add(Sunburn())
        effectGenes.add(Shadow())
        effectGenes.add(AttackSpread())
    }

    override fun onDeath(event: LivingDeathEvent, entity: LivingEntity) {
        super.onDeath(event, entity)
        repeat(5) {
            EntityType.BAT.create(entity.level)?.let{ ent ->
                ent.setPos(entity.x + Utils.random.nextDouble(), entity.y, entity.z + Utils.random.nextDouble())
                entity.level.addFreshEntity(ent)
            }
        }
    }

    override fun getCopy() = Bat()
}