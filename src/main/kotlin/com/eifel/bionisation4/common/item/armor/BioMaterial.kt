package com.eifel.bionisation4.common.item.armor

import com.eifel.bionisation4.api.util.Utils
import net.minecraft.inventory.EquipmentSlotType
import net.minecraft.item.IArmorMaterial
import net.minecraft.item.crafting.Ingredient
import net.minecraft.util.LazyValue
import net.minecraft.util.SoundEvent
import net.minecraft.util.SoundEvents
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import java.util.function.Supplier

enum class BioMaterial: IArmorMaterial {

    BIO_ARMOR("bioarmor", 7, intArrayOf( 2, 5, 6, 2 ), 12,
        SoundEvents.ARMOR_EQUIP_LEATHER, 1.0f, 0.0f, { Ingredient.of(com.eifel.bionisation4.common.item.ItemRegistry.CLOTH.get()) });

    private var MAX_DAMAGE_ARRAY = intArrayOf(13, 15, 16, 11)
    private var armorName: String = ""
    private var maxDamageFactor = 0
    private var damageReductionAmountArray: IntArray
    private var enchantability = 0
    private var soundEvent: SoundEvent
    private var toughness = 0f
    private var knockbackResistance = 0f
    private var repairMaterial: LazyValue<Ingredient>

    constructor(name: String, maxDamageFactor: Int, damageReductionAmountArray: IntArray, enchantability: Int,soundEvent: SoundEvent, toughness: Float, knockbackResistance: Float, repairMaterial: Supplier<Ingredient>) {
        this.armorName = name
        this.maxDamageFactor = maxDamageFactor
        this.damageReductionAmountArray = damageReductionAmountArray
        this.enchantability = enchantability
        this.soundEvent = soundEvent
        this.toughness = toughness
        this.knockbackResistance = knockbackResistance
        this.repairMaterial = LazyValue(repairMaterial)
    }

    override fun getDurabilityForSlot(slot: EquipmentSlotType) = MAX_DAMAGE_ARRAY[slot.index] * this.maxDamageFactor
    override fun getDefenseForSlot(slot: EquipmentSlotType) = this.damageReductionAmountArray[slot.index]
    override fun getEnchantmentValue() = this.enchantability
    override fun getEquipSound() = this.soundEvent
    override fun getRepairIngredient() = this.repairMaterial.get()
    @OnlyIn(Dist.CLIENT)
    override fun getName() = Utils.getModIDString(this.armorName)
    override fun getToughness() = this.toughness
    override fun getKnockbackResistance() = this.knockbackResistance
}
