package com.eifel.bionisation4.common.storage.capability.entity

import com.eifel.bionisation4.Info
import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.common.config.ConfigProperties
import com.eifel.bionisation4.common.network.NetworkManager
import com.eifel.bionisation4.common.network.message.mob.PacketMobPropertySync
import com.eifel.bionisation4.common.network.message.mob.PacketMobSimpleEffectStates
import com.eifel.bionisation4.common.network.message.player.PacketPlayerPropertySync
import com.eifel.bionisation4.common.network.message.player.PacketPlayerSimpleEffectStates
import com.eifel.bionisation4.common.storage.capability.handler.BloodHandler
import com.eifel.bionisation4.util.lab.EffectUtils
import com.eifel.bionisation4.util.nbt.NBTUtils
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.nbt.CompoundNBT
import net.minecraft.util.ResourceLocation
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import net.minecraftforge.fml.network.PacketDistributor

class BioStat(): IBioStat {

    companion object StaticInit {
        val PROP = ResourceLocation("${Info.MOD_ID}_modstatdata")
    }

    val effects = mutableListOf<AbstractEffect>()
    val pending = mutableListOf<AbstractEffect>()

    var immunity = 100
    var blood = 100
    var ticker = 0

    fun onUpdate(entity: LivingEntity){
        if(pending.isNotEmpty()) {
            effects.addAll(pending)
            if(!entity.level.isClientSide) {
                if (entity is ServerPlayerEntity)
                    NetworkManager.INSTANCE.send(
                        PacketDistributor.PLAYER.with { entity }, PacketPlayerSimpleEffectStates(
                            pending.map { it.effectID }.toIntArray(), 1
                        )
                    )
                else
                    NetworkManager.INSTANCE.send(
                        PacketDistributor.NEAR.with(
                            PacketDistributor.TargetPoint.p(
                                entity.x,
                                entity.y,
                                entity.z,
                                25.0,
                                entity.level.dimension()
                            )
                        ), PacketMobSimpleEffectStates(
                            pending.map { it.effectID }.toIntArray(), 1, entity.id
                        )
                    )
            }
            pending.clear()
        }
        if(!entity.level.isClientSide){
            val it = effects.iterator()
            while(it.hasNext()){
                val effect = it.next()
                if(effect.isExpired){
                    it.remove()
                    if(entity is ServerPlayerEntity)
                        NetworkManager.INSTANCE.send(PacketDistributor.PLAYER.with{ entity }, PacketPlayerSimpleEffectStates(
                            intArrayOf(effect.effectID), 0)
                        )
                    else
                        NetworkManager.INSTANCE.send(
                            PacketDistributor.NEAR.with(PacketDistributor.TargetPoint.p(entity.x, entity.y, entity.z, 25.0, entity.level.dimension())), PacketMobSimpleEffectStates(
                                intArrayOf(effect.effectID), 0, entity.id)
                        )
                }else
                    effect.perform(entity)
            }
            if(entity.tickCount % ConfigProperties.defaultEffectSyncPeriod.get() == 0){
                if(entity is ServerPlayerEntity)
                    NetworkManager.INSTANCE.send(PacketDistributor.NEAR.with(PacketDistributor.TargetPoint.p(entity.x, entity.y, entity.z, 25.0, entity.level.dimension())), PacketPlayerSimpleEffectStates(
                        effects.filter { it.isSyncable && !it.isHidden }.map { it.effectID }.toIntArray(), 1)
                    )
                else
                    NetworkManager.INSTANCE.send(
                        PacketDistributor.NEAR.with(PacketDistributor.TargetPoint.p(entity.x, entity.y, entity.z, 25.0, entity.level.dimension())), PacketMobSimpleEffectStates(
                            effects.filter { it.isSyncable && !it.isHidden }.map { it.effectID }.toIntArray(), 1, entity.id)
                    )
            }
            //symbiosis
            EffectUtils.symbiosisCheckAndTrigger(entity)
            BloodHandler.checkBloodLevel(entity, blood)
            ticker++
        }
    }

    fun sendAllEffects(entity: LivingEntity) {
        if(!entity.level.isClientSide) {
            if(entity is ServerPlayerEntity) {
                NetworkManager.INSTANCE.send(
                    PacketDistributor.PLAYER.with { entity }, PacketPlayerSimpleEffectStates(
                        effects.map { it.effectID }.toIntArray(), 1
                    )
                )
                NetworkManager.INSTANCE.send(
                    PacketDistributor.PLAYER.with { entity },
                    PacketPlayerPropertySync(immunity, 0)
                )
                NetworkManager.INSTANCE.send(
                    PacketDistributor.PLAYER.with { entity },
                    PacketPlayerPropertySync(blood, 1)
                )
            }else {
                NetworkManager.INSTANCE.send(
                    PacketDistributor.NEAR.with(
                        PacketDistributor.TargetPoint.p(
                            entity.x,
                            entity.y,
                            entity.z,
                            25.0,
                            entity.level.dimension()
                        )
                    ), PacketMobSimpleEffectStates(
                        effects.map { it.effectID }.toIntArray(), 1, entity.id
                    )
                )
            }
        }
    }

    fun syncMobProperties(entity: LivingEntity) {
        if(!entity.level.isClientSide) {
            NetworkManager.INSTANCE.send(
                PacketDistributor.NEAR.with(
                    PacketDistributor.TargetPoint.p(
                        entity.x,
                        entity.y,
                        entity.z,
                        25.0,
                        entity.level.dimension()
                    )
                ), PacketMobPropertySync(
                    immunity, 0, entity.id
                )
            )
            NetworkManager.INSTANCE.send(
                PacketDistributor.NEAR.with(
                    PacketDistributor.TargetPoint.p(
                        entity.x,
                        entity.y,
                        entity.z,
                        25.0,
                        entity.level.dimension()
                    )
                ), PacketMobPropertySync(
                    blood, 1, entity.id
                )
            )
        }
    }

    fun addEffect(effect: AbstractEffect){
        if(!isActive(effect))
            this.pending.add(effect)
    }

    fun expire(effect: AbstractEffect) = this.effects.filter { it.isSame(effect) }.forEach { it.isExpired = true }
    fun expire(id: Int) = this.effects.filter { it.isSame(id) }.forEach { it.isExpired = true }
    fun expire(name: String) = this.effects.filter { it.isSame(name) }.forEach { it.isExpired = true }

    @OnlyIn(Dist.CLIENT)
    fun remove(effect: AbstractEffect) = this.effects.removeIf { it.isSame(effect) }
    @OnlyIn(Dist.CLIENT)
    fun remove(id: Int) = this.effects.removeIf { it.isSame(id) }
    @OnlyIn(Dist.CLIENT)
    fun remove(name: String) = this.effects.removeIf { it.isSame(name) }

    fun isActive(effect: AbstractEffect) = this.effects.any { it.isSame(effect) }
    fun isActive(id: Int) = this.effects.any { it.isSame(id) }
    fun isActive(name: String) = this.effects.any { it.isSame(name) }

    fun modifyImmunity(entity: LivingEntity, value: Int) {
        immunity += value
        when{
            immunity > 100 -> immunity = 100
            immunity < 0 -> immunity = 0
        }
        if(!entity.level.isClientSide) {
            if(entity is ServerPlayerEntity)
                NetworkManager.INSTANCE.send(
                    PacketDistributor.PLAYER.with { entity },
                    PacketPlayerPropertySync(immunity, 0)
                )
            else
                NetworkManager.INSTANCE.send(
                    PacketDistributor.NEAR.with(PacketDistributor.TargetPoint.p(entity.x, entity.y, entity.z, 25.0, entity.level.dimension())), PacketMobPropertySync(
                        immunity, 0, entity.id)
                )
        }
    }

    fun modifyBlood(entity: LivingEntity, value: Int) {
        blood += value
        when{
            blood > 100 -> blood = 100
            blood < 0 -> blood = 0
        }
        if(!entity.level.isClientSide) {
            if(entity is ServerPlayerEntity)
                NetworkManager.INSTANCE.send(
                    PacketDistributor.PLAYER.with { entity },
                    PacketPlayerPropertySync(blood, 1)
                )
            else
                NetworkManager.INSTANCE.send(
                    PacketDistributor.NEAR.with(PacketDistributor.TargetPoint.p(entity.x, entity.y, entity.z, 25.0, entity.level.dimension())), PacketMobPropertySync(
                        blood, 1, entity.id)
                )
        }
    }

    fun setImmunity(entity: LivingEntity, value: Int) {
        immunity = value
        when{
            immunity > 100 -> immunity = 100
            immunity < 0 -> immunity = 0
        }
        if(!entity.level.isClientSide) {
            if(entity is ServerPlayerEntity)
                NetworkManager.INSTANCE.send(
                    PacketDistributor.PLAYER.with { entity },
                    PacketPlayerPropertySync(immunity, 0)
                )
            else
                NetworkManager.INSTANCE.send(
                    PacketDistributor.NEAR.with(PacketDistributor.TargetPoint.p(entity.x, entity.y, entity.z, 25.0, entity.level.dimension())), PacketMobPropertySync(
                        immunity, 0, entity.id)
                )
        }
    }

    fun setBlood(entity: LivingEntity, value: Int) {
        blood = value
        when{
            blood > 100 -> blood = 100
            blood < 0 -> blood = 0
        }
        if(!entity.level.isClientSide) {
            if(entity is ServerPlayerEntity)
                NetworkManager.INSTANCE.send(
                    PacketDistributor.PLAYER.with { entity },
                    PacketPlayerPropertySync(blood, 1)
                )
            else
                NetworkManager.INSTANCE.send(
                    PacketDistributor.NEAR.with(PacketDistributor.TargetPoint.p(entity.x, entity.y, entity.z, 25.0, entity.level.dimension())), PacketMobPropertySync(
                        blood, 1, entity.id)
                )
        }
    }

    override fun writeToNBT(): CompoundNBT {
        val nbtData = CompoundNBT()
        nbtData.putInt(InternalConstants.PROP_IMMUNITY_KEY, immunity)
        nbtData.putInt(InternalConstants.PROP_BLOOD_KEY, blood)
        nbtData.putInt(InternalConstants.PROP_TICKER_KEY, ticker)
        NBTUtils.objectsToNBT(nbtData, effects, InternalConstants.PROP_EFFECT_KEY)
        return nbtData
    }

    override fun readFromNBT(nbtBase: CompoundNBT) {
        immunity = nbtBase.getInt(InternalConstants.PROP_IMMUNITY_KEY)
        blood = nbtBase.getInt(InternalConstants.PROP_BLOOD_KEY)
        ticker = nbtBase.getInt(InternalConstants.PROP_TICKER_KEY)
        NBTUtils.nbtToEffects(nbtBase, effects, InternalConstants.PROP_EFFECT_KEY)
    }
}