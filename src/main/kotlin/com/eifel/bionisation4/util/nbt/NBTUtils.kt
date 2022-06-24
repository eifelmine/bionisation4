package com.eifel.bionisation4.util.nbt

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.registry.EffectRegistry
import com.eifel.bionisation4.api.laboratory.species.AbstractEffect
import com.eifel.bionisation4.api.laboratory.species.Gene
import com.eifel.bionisation4.api.laboratory.util.INBTSerializable
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.ListTag
import net.minecraft.world.item.ItemStack

object NBTUtils {

    fun stringsToNBT(compound: CompoundTag, list: Collection<String>, key: String) {
        val tags = ListTag()
        list.mapTo(tags){ string -> CompoundTag().apply { putString("string", string) } }
        compound.put(key, tags)
    }

    fun nbtToStrings(compound: CompoundTag, list: MutableCollection<String>, key: String) {
        val tags = compound.getList(key, 10)
        tags.map { it as CompoundTag }.mapTo(list){tag ->
            tag.getString("string")
        }
    }

    fun objectsToNBT(compound: CompoundTag, list: Collection<INBTSerializable>, key: String) {
        val tags = ListTag()
        list.mapTo(tags){ ser ->
            ser.toNBT()
        }
        compound.put(key, tags)
    }

    fun objectToNBT(compound: CompoundTag, ser: INBTSerializable, key: String) {
        compound.put(key, ser.toNBT())
    }

    fun <T: INBTSerializable> nbtToObjects(compound: CompoundTag, list: MutableCollection<T>, key: String, clazz: Class<T>) {
        val tags = compound.getList(key, 10)
        tags.map { it as CompoundTag }.mapTo(list){ tag ->
            val obj = clazz.newInstance()
            obj.fromNBT(tag)
            obj
        }
    }

    fun <T: Gene> nbtToGenes(compound: CompoundTag, list: MutableCollection<T>, key: String) {
        val tags = compound.getList(key, 10)
        tags.map { it as CompoundTag }.mapTo(list){ tag ->
            val obj = EffectRegistry.getGeneClassById(tag.getInt(InternalConstants.GENE_ID_KEY)).newInstance()
            obj.fromNBT(tag)
            obj as T
        }
    }

    fun <T: AbstractEffect> nbtToEffects(compound: CompoundTag, list: MutableCollection<T>, key: String) {
        val tags = compound.getList(key, 10)
        tags.map { it as CompoundTag }.mapTo(list){ tag ->
            val obj = EffectRegistry.getEffectClassById(tag.getInt(InternalConstants.EFFECT_ID_KEY)).newInstance()
            obj.fromNBT(tag)
            obj as T
        }
    }

    fun <T: INBTSerializable> nbtToObject(compound: CompoundTag, key: String, clazz: Class<T>): T {
        val nbt = compound.getCompound(key)
        val obj = clazz.newInstance()
        obj.fromNBT(nbt)
        return obj
    }

    fun <T: AbstractEffect> nbtToEffect(compound: CompoundTag, key: String): T {
        val nbt = compound.getCompound(key)
        val obj = EffectRegistry.getEffectClassById(nbt.getInt(InternalConstants.EFFECT_ID_KEY)).newInstance()
        obj.fromNBT(nbt)
        return obj as T
    }

    fun <T: Gene> nbtToGene(compound: CompoundTag, key: String): T {
        val nbt = compound.getCompound(key)
        val obj = EffectRegistry.getGeneClassById(nbt.getInt(InternalConstants.GENE_ID_KEY)).newInstance()
        obj.fromNBT(nbt)
        return obj as T
    }

    fun enumToNBT(compound: CompoundTag, enum: Enum<*>, key: String) {
        compound.putString(key, enum.name)
    }

    fun enumsToNBT(compound: CompoundTag, list: Collection<Enum<*>>, key: String) {
        val tags = ListTag()
        list.mapTo(tags){ ser -> CompoundTag().apply { putString("enum", ser.name) } }
        compound.put(key, tags)
    }

    fun packToNBT(list: Collection<INBTSerializable>, key: String): CompoundTag {
        val compound = CompoundTag()
        val tags = ListTag()
        list.mapTo(tags){ ser ->
            ser.toNBT()
        }
        compound.put(key, tags)
        return compound
    }

    inline fun <reified T: Enum<*>> nbtToEnum(compound: CompoundTag, key: String): T? = enumValueOrNull(compound.getString(key))

    inline fun <reified T: Enum<*>> nbtToEnums(compound: CompoundTag, list: MutableCollection<T>, key: String) {
        val tags = compound.getList(key, 10)
        tags.map { it as CompoundTag }.mapNotNullTo(list){ tag ->
            enumValueOrNull(tag.getString("enum"))
        }
    }

    fun getNBT(stack: ItemStack): CompoundTag {
        return if(!stack.isEmpty) {
            if (stack.hasTag())
                stack.tag!!
            else {
                val tag = CompoundTag()
                stack.tag = tag
                tag
            }
        } else
            CompoundTag()
    }

    inline fun <reified T : Enum<*>> enumValueOrNull(name: String): T? = T::class.java.enumConstants.firstOrNull { it.name == name }
}