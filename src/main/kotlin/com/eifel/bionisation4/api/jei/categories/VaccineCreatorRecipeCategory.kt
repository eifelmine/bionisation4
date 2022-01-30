package com.eifel.bionisation4.api.jei.categories

import com.eifel.bionisation4.Info
import com.eifel.bionisation4.api.jei.recipes.VaccineCreatorRecipe
import com.eifel.bionisation4.common.block.BlockRegistry
import com.eifel.bionisation4.common.config.ConfigProperties
import com.eifel.bionisation4.util.translation.TranslationUtils
import com.mojang.blaze3d.matrix.MatrixStack
import mezz.jei.api.constants.VanillaTypes
import mezz.jei.api.gui.IRecipeLayout
import mezz.jei.api.gui.drawable.IDrawable
import mezz.jei.api.gui.drawable.IDrawableStatic
import mezz.jei.api.helpers.IGuiHelper
import mezz.jei.api.ingredients.IIngredients
import mezz.jei.api.recipe.category.IRecipeCategory
import net.minecraft.client.Minecraft
import net.minecraft.client.resources.I18n
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.util.ResourceLocation

class VaccineCreatorRecipeCategory(val helper: IGuiHelper) : IRecipeCategory<VaccineCreatorRecipe> {

    companion object {
        val UUID = ResourceLocation(Info.MOD_ID, "jei_vaccine_creator")
    }

    private var GUI: IDrawableStatic = helper.createDrawable(ResourceLocation(Info.MOD_ID, "textures/gui/gui_vaccinecreator.png"), 49, 14, 124, 69)
    private var ICON: IDrawable = helper.createDrawableIngredient(ItemStack(BlockRegistry.VACCINE_CREATOR.get()))

    override fun getUid() = UUID
    override fun getRecipeClass() = VaccineCreatorRecipe::class.java
    override fun getTitle() = TranslationUtils.getTranslatedText("jei", "title", "vaccine_creator")
    override fun getBackground() = GUI
    override fun getIcon() = ICON

    override fun setIngredients(recipe: VaccineCreatorRecipe, ingredients: IIngredients) {
        ingredients.setInputs(VanillaTypes.ITEM, recipe.input.toList())
    }

    override fun setRecipe(recipeLayout: IRecipeLayout, recipeWrapper: VaccineCreatorRecipe, ingredients: IIngredients) {
        val isg = recipeLayout.itemStacks
        isg.init(0, true, 19, 2)
        isg[0] = recipeWrapper.input.first
        isg.init(1, true, 41, 2)
        isg[1] = recipeWrapper.input.second
        isg.init(2, false, 30, 46)
        isg[2] = recipeWrapper.output
        isg.init(3, true, 102, 46)
        isg[3] = ItemStack(Items.COAL)
    }

    override fun draw(recipe: VaccineCreatorRecipe, matrixStack: MatrixStack, mouseX: Double, mouseY: Double) {
        super.draw(recipe, matrixStack, mouseX, mouseY)
        Minecraft.getInstance().font.draw(matrixStack, I18n.get("gui.jei.category.smelting.time.seconds", ConfigProperties.defaultVaccineCreatorProcessTime.get() / 20), 88f, 8f, 0x000000)
    }
}