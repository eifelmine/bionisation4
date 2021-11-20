package com.eifel.bionisation4.common.storage.capability.entity

import com.eifel.bionisation4.Info
import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.common.config.ConfigProperties
import com.eifel.bionisation4.common.network.NetworkManager
import com.eifel.bionisation4.common.network.message.mob.PacketMobPropertySync
import com.eifel.bionisation4.common.network.message.mob.PacketMobSimpleEffectStates
import com.eifel.bionisation4.util.nbt.NBTUtils
import net.minecraft.entity.LivingEntity
import net.minecraft.nbt.CompoundNBT
import net.minecraft.util.ResourceLocation
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import net.minecraftforge.fml.network.PacketDistributor

class BioMob(): IBioMob {

    companion object StaticInit {
        val PROP = ResourceLocation( "${Info.MOD_ID}_modmobdata")
    }

    val effects = mutableListOf<AbstractEffect>()
    val pending = mutableListOf<AbstractEffect>()

    private var immunity = 100
    private var blood = 100

    fun onUpdate(entity: LivingEntity){
        if(!entity.level.isClientSide){
            if(pending.isNotEmpty()) {
                effects.addAll(pending)
                NetworkManager.INSTANCE.send(
                    PacketDistributor.NEAR.with(PacketDistributor.TargetPoint.p(entity.x, entity.y, entity.z, 25.0, entity.level.dimension())), PacketMobSimpleEffectStates(
                        pending.map { it.effectID }.toIntArray(), 1, entity.id)
                )
                pending.clear()
            }
            val it = effects.iterator()
            while(it.hasNext()){
                val effect = it.next()
                if(effect.isExpired){
                    it.remove()
                    NetworkManager.INSTANCE.send(
                        PacketDistributor.NEAR.with(PacketDistributor.TargetPoint.p(entity.x, entity.y, entity.z, 25.0, entity.level.dimension())), PacketMobSimpleEffectStates(
                            intArrayOf(effect.effectID), 0, entity.id)
                    )
                }else
                    effect.perform(entity)
            }
            if(entity.tickCount % ConfigProperties.defaultEffectSyncPeriod.get() == 0){
                NetworkManager.INSTANCE.send(
                    PacketDistributor.NEAR.with(PacketDistributor.TargetPoint.p(entity.x, entity.y, entity.z, 25.0, entity.level.dimension())), PacketMobSimpleEffectStates(
                    effects.filter { it.isSyncable && !it.isHidden }.map { it.effectID }.toIntArray(), 1, entity.id)
                )
            }
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
        NetworkManager.INSTANCE.send(
            PacketDistributor.NEAR.with(PacketDistributor.TargetPoint.p(entity.x, entity.y, entity.z, 25.0, entity.level.dimension())), PacketMobPropertySync(
                immunity, 0, entity.id)
        )
    }

    fun modifyBlood(entity: LivingEntity, value: Int) {
        blood += value
        when{
            blood > 100 -> blood = 100
            blood < 0 -> blood = 0
        }
        NetworkManager.INSTANCE.send(
            PacketDistributor.NEAR.with(PacketDistributor.TargetPoint.p(entity.x, entity.y, entity.z, 25.0, entity.level.dimension())), PacketMobPropertySync(
                blood, 1, entity.id)
        )
    }

    fun setImmunity(entity: LivingEntity, value: Int) {
        immunity = value
        when{
            immunity > 100 -> immunity = 100
            immunity < 0 -> immunity = 0
        }
        NetworkManager.INSTANCE.send(
            PacketDistributor.NEAR.with(PacketDistributor.TargetPoint.p(entity.x, entity.y, entity.z, 25.0, entity.level.dimension())), PacketMobPropertySync(
                immunity, 0, entity.id)
        )
    }

    fun setBlood(entity: LivingEntity, value: Int) {
        blood = value
        when{
            blood > 100 -> blood = 100
            blood < 0 -> blood = 0
        }
        NetworkManager.INSTANCE.send(
            PacketDistributor.NEAR.with(PacketDistributor.TargetPoint.p(entity.x, entity.y, entity.z, 25.0, entity.level.dimension())), PacketMobPropertySync(
                blood, 1, entity.id)
        )
    }

    override fun writeToNBT(): CompoundNBT {
        val nbtData = CompoundNBT()
        nbtData.putInt(InternalConstants.PROP_IMMUNITY_KEY, immunity)
        nbtData.putInt(InternalConstants.PROP_BLOOD_KEY, blood)
        NBTUtils.objectsToNBT(nbtData, effects, InternalConstants.PROP_EFFECT_KEY)
        return nbtData
    }

    override fun readFromNBT(nbtBase: CompoundNBT) {
        immunity = nbtBase.getInt(InternalConstants.PROP_IMMUNITY_KEY)
        blood = nbtBase.getInt(InternalConstants.PROP_BLOOD_KEY)
        NBTUtils.nbtToEffects(nbtBase, effects, InternalConstants.PROP_EFFECT_KEY)
    }
}