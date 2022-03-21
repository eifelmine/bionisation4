package com.eifel.bionisation4.api.jei

import com.eifel.bionisation4.Info
import com.eifel.bionisation4.api.jei.categories.CureStationRecipeCategory
import com.eifel.bionisation4.api.jei.categories.DNAModifierRecipeCategory
import com.eifel.bionisation4.api.jei.categories.VaccineCreatorRecipeCategory
import com.eifel.bionisation4.api.jei.categories.VirusReplicatorRecipeCategory
import com.eifel.bionisation4.common.block.BlockRegistry
import mezz.jei.api.IModPlugin
import mezz.jei.api.JeiPlugin
import mezz.jei.api.registration.IRecipeCatalystRegistration
import mezz.jei.api.registration.IRecipeCategoryRegistration
import mezz.jei.api.registration.IRecipeRegistration
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation

@JeiPlugin
class JEIBionisationPlugin() : IModPlugin {

    override fun getPluginUid() = ResourceLocation(Info.MOD_ID, "jei_bionisation4")

    override fun registerCategories(registry: IRecipeCategoryRegistration) {
        registry.addRecipeCategories(CureStationRecipeCategory(registry.jeiHelpers.guiHelper))
        registry.addRecipeCategories(VaccineCreatorRecipeCategory(registry.jeiHelpers.guiHelper))
        registry.addRecipeCategories(DNAModifierRecipeCategory(registry.jeiHelpers.guiHelper))
        registry.addRecipeCategories(VirusReplicatorRecipeCategory(registry.jeiHelpers.guiHelper))
    }

    override fun registerRecipes(registration: IRecipeRegistration) {
        super.registerRecipes(registration)
        registration.addRecipes(RecipeDataHolder.CURE_STATION_RECIPES, CureStationRecipeCategory.UUID)
        registration.addRecipes(RecipeDataHolder.VACCINE_CREATOR_RECIPES, VaccineCreatorRecipeCategory.UUID)
        registration.addRecipes(RecipeDataHolder.DNA_MODIFIER_RECIPES, DNAModifierRecipeCategory.UUID)
        registration.addRecipes(RecipeDataHolder.VIRUS_REPLICATOR_RECIPES, VirusReplicatorRecipeCategory.UUID)
    }

    override fun registerRecipeCatalysts(registration: IRecipeCatalystRegistration) {
        super.registerRecipeCatalysts(registration)
        registration.addRecipeCatalyst(ItemStack(BlockRegistry.CURE_STATION.get()), CureStationRecipeCategory.UUID)
        registration.addRecipeCatalyst(ItemStack(BlockRegistry.VACCINE_CREATOR.get()), VaccineCreatorRecipeCategory.UUID)
        registration.addRecipeCatalyst(ItemStack(BlockRegistry.DNA_MODIFIER.get()), DNAModifierRecipeCategory.UUID)
        registration.addRecipeCatalyst(ItemStack(BlockRegistry.VIRUS_REPLICATOR.get()), VirusReplicatorRecipeCategory.UUID)
    }
}