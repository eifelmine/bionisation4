package com.eifel.bionisation4.util.nbt

import com.eifel.bionisation4.api.constant.InternalConstants
import com.eifel.bionisation4.api.laboratory.registry.EffectRegistry
import com.eifel.bionisation4.api.laboratory.util.INBTSerializable
import net.minecraft.nbt.CompoundNBT
import net.minecraft.nbt.ListNBT

object NBTUtils {

    fun stringsToNBT(compound: CompoundNBT, list: Collection<String>, key: String) {
        val tags = ListNBT()
        list.mapTo(tags){ string ->
            val nbt = CompoundNBT()
            nbt.putString("string", string)
            nbt
        }
        compound.put(key, tags)
    }

    fun nbtToStrings(compound: CompoundNBT, list: MutableCollection<String>, key: String) {
        val tags = compound.getList(key, 9)
        tags.map { it as CompoundNBT }.mapTo(list){tag ->
            tag.getString("string")
        }
    }

    fun objectsToNBT(compound: CompoundNBT, list: Collection<INBTSerializable>, key: String) {
        val tags = ListNBT()
        list.mapTo(tags){ ser ->
            ser.toNBT()
        }
        compound.put(key, tags)
    }

    fun objectToNBT(compound: CompoundNBT, ser: INBTSerializable, key: String) {
        compound.put(key, ser.toNBT())
    }

    fun <T: INBTSerializable> nbtToObjects(compound: CompoundNBT, list: MutableCollection<T>, key: String, clazz: Class<T>) {
        val tags = compound.getList(key, 9)
        tags.map { it as CompoundNBT }.mapTo(list){ tag ->
            val obj = clazz.newInstance()
            obj.fromNBT(tag)
            obj
        }
    }

    fun <T: INBTSerializable> nbtToGenes(compound: CompoundNBT, list: MutableCollection<T>, key: String) {
        val tags = compound.getList(key, 9)
        tags.map { it as CompoundNBT }.mapTo(list){ tag ->
            val obj = EffectRegistry.getGeneClassById(tag.getInt(InternalConstants.GENE_ID_KEY)).newInstance()
            obj.fromNBT(tag)
            obj as T
        }
    }

    fun <T: INBTSerializable> nbtToEffects(compound: CompoundNBT, list: MutableCollection<T>, key: String) {
        val tags = compound.getList(key, 9)
        tags.map { it as CompoundNBT }.mapTo(list){ tag ->
            val obj = EffectRegistry.getEffectClassById(tag.getInt(InternalConstants.EFFECT_ID_KEY)).newInstance()
            obj.fromNBT(tag)
            obj as T
        }
    }

    fun <T: INBTSerializable> nbtToObject(compound: CompoundNBT, key: String, clazz: Class<T>): T {
        val nbt = compound.getCompound(key)
        val obj = clazz.newInstance()
        obj.fromNBT(nbt)
        return obj
    }

    fun enumToNBT(compound: CompoundNBT, enum: Enum<*>, key: String) {
        compound.putString(key, enum.name)
    }

    fun enumsToNBT(compound: CompoundNBT, list: Collection<Enum<*>>, key: String) {
        val tags = ListNBT()
        list.mapTo(tags){ ser ->
            val nbt = CompoundNBT()
            nbt.putString("enum", ser.name)
            nbt
        }
        compound.put(key, tags)
    }

    inline fun <reified T: Enum<*>> nbtToEnum(compound: CompoundNBT, key: String): T? = enumValueOrNull(compound.getString(key))

    inline fun <reified T: Enum<*>> nbtToEnums(compound: CompoundNBT, list: MutableCollection<T>, key: String) {
        val tags = compound.getList(key, 9)
        tags.map { it as CompoundNBT }.mapNotNullTo(list){ tag ->
            enumValueOrNull(tag.getString("enum"))
        }
    }

    inline fun <reified T : Enum<*>> enumValueOrNull(name: String): T? = T::class.java.enumConstants.firstOrNull { it.name == name }
}