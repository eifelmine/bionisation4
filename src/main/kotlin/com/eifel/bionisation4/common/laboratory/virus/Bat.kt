package com.eifel.bionisation4.common.laboratory.virus

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.util.EffectType
import com.eifel.bionisation4.api.util.Utils
import com.eifel.bionisation4.common.extensions.addEffect
import com.eifel.bionisation4.common.laboratory.gene.species.type.Aggressive
import com.eifel.bionisation4.common.laboratory.gene.species.type.AttackSpread
import com.eifel.bionisation4.common.laboratory.gene.species.type.Shadow
import com.eifel.bionisation4.common.laboratory.gene.species.type.Sunburn
import net.minecraft.entity.EntityType
import net.minecraft.entity.LivingEntity
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
        effectGenes.add(Aggressive())
    }

    override fun onDeath(event: LivingDeathEvent, entity: LivingEntity) {
        super.onDeath(event, entity)
        entity.level.explode(entity, entity.blockPosition().x.toDouble(), entity.blockPosition().y.toDouble(), entity.blockPosition().z.toDouble(), 2.0f, net.minecraft.world.Explosion.Mode.BREAK)
        repeat(5) {
            EntityType.BAT.create(entity.level)?.let{ ent ->
                ent.setPos(entity.x + Utils.random.nextDouble(), entity.y, entity.z + Utils.random.nextDouble())
                ent.addEffect(Bat())
                entity.level.addFreshEntity(ent)
            }
        }
    }

    override fun getCopy() = Bat()
}