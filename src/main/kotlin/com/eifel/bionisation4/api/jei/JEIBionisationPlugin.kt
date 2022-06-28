package com.eifel.bionisation4.api.jei

import com.eifel.bionisation4.Info
import com.eifel.bionisation4.api.jei.categories.CureStationRecipeCategory
import com.eifel.bionisation4.api.jei.categories.DNAModifierRecipeCategory
import com.eifel.bionisation4.api.jei.categories.VaccineCreatorRecipeCategory
import com.eifel.bionisation4.api.jei.categories.VirusReplicatorRecipeCategory
import com.eifel.bionisation4.api.jei.recipes.CureStationRecipe
import com.eifel.bionisation4.api.jei.recipes.DNAModifierRecipe
import com.eifel.bionisation4.api.jei.recipes.VaccineCreatorRecipe
import com.eifel.bionisation4.api.jei.recipes.VirusReplicatorRecipe
import com.eifel.bionisation4.common.block.BlockRegistry
import mezz.jei.api.IModPlugin
import mezz.jei.api.JeiPlugin
import mezz.jei.api.recipe.RecipeType
import mezz.jei.api.registration.IRecipeCatalystRegistration
import mezz.jei.api.registration.IRecipeCategoryRegistration
import mezz.jei.api.registration.IRecipeRegistration
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.ItemStack

@JeiPlugin
class JEIBionisationPlugin() : IModPlugin {

    val cureStationRecipeType = RecipeType.create("bionisation4", "curestation", CureStationRecipe::class.java)
    val dnaModifierRecipeType = RecipeType.create("bionisation4", "dnamodifier", DNAModifierRecipe::class.java)
    val vaccineCreatorRecipeType = RecipeType.create("bionisation4", "vaccinecreator", VaccineCreatorRecipe::class.java)
    val virusReplicatorRecipeType = RecipeType.create("bionisation4", "virusreplicator", VirusReplicatorRecipe::class.java)

    override fun getPluginUid() = ResourceLocation(Info.MOD_ID, "jeibionisation4")

    override fun registerCategories(registry: IRecipeCategoryRegistration) {
        registry.addRecipeCategories(CureStationRecipeCategory(registry.jeiHelpers.guiHelper, cureStationRecipeType))
        registry.addRecipeCategories(VaccineCreatorRecipeCategory(registry.jeiHelpers.guiHelper, vaccineCreatorRecipeType))
        registry.addRecipeCategories(DNAModifierRecipeCategory(registry.jeiHelpers.guiHelper, dnaModifierRecipeType))
        registry.addRecipeCategories(VirusReplicatorRecipeCategory(registry.jeiHelpers.guiHelper, virusReplicatorRecipeType))
    }

    override fun registerRecipes(registration: IRecipeRegistration) {
        RecipeDataHolder.initRecipes()
        registration.addRecipes(cureStationRecipeType, RecipeDataHolder.CURE_STATION_RECIPES)
        registration.addRecipes(vaccineCreatorRecipeType, RecipeDataHolder.VACCINE_CREATOR_RECIPES)
        registration.addRecipes(dnaModifierRecipeType, RecipeDataHolder.DNA_MODIFIER_RECIPES)
        registration.addRecipes(virusReplicatorRecipeType, RecipeDataHolder.VIRUS_REPLICATOR_RECIPES)
    }

    override fun registerRecipeCatalysts(registration: IRecipeCatalystRegistration) {
        registration.addRecipeCatalyst(ItemStack(BlockRegistry.CURE_STATION.get()), cureStationRecipeType)
        registration.addRecipeCatalyst(ItemStack(BlockRegistry.VACCINE_CREATOR.get()), vaccineCreatorRecipeType)
        registration.addRecipeCatalyst(ItemStack(BlockRegistry.DNA_MODIFIER.get()), dnaModifierRecipeType)
        registration.addRecipeCatalyst(ItemStack(BlockRegistry.VIRUS_REPLICATOR.get()), virusReplicatorRecipeType)
    }
}