package com.eifel.bionisation4.api.jei.categories

import com.eifel.bionisation4.Info
import com.eifel.bionisation4.api.jei.recipes.DNAModifierRecipe
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

class DNAModifierRecipeCategory(val helper: IGuiHelper) : IRecipeCategory<DNAModifierRecipe> {

    companion object {
        val UUID = ResourceLocation(Info.MOD_ID, "jei_dna_modifier")
    }

    private var GUI: IDrawableStatic = helper.createDrawable(ResourceLocation(Info.MOD_ID, "textures/gui/gui_dnamodifier.png"), 49, 14, 124, 69)
    private var ICON: IDrawable = helper.createDrawableIngredient(ItemStack(BlockRegistry.DNA_MODIFIER.get()))

    override fun getUid() = UUID
    override fun getRecipeClass() = DNAModifierRecipe::class.java
    override fun getTitle() = TranslationUtils.getTranslatedText("jei", "title", "dna_modifier")
    override fun getBackground() = GUI
    override fun getIcon() = ICON

    override fun setIngredients(recipe: DNAModifierRecipe, ingredients: IIngredients) {
        ingredients.setInput(VanillaTypes.ITEM, recipe.input)
    }

    override fun setRecipe(recipeLayout: IRecipeLayout, recipeWrapper: DNAModifierRecipe, ingredients: IIngredients) {
        val isg = recipeLayout.itemStacks
        isg.init(0, true, 30, 2)
        isg[0] = recipeWrapper.input
        isg.init(2, false, 30, 46)
        isg[2] = recipeWrapper.output
        isg.init(3, true, 102, 46)
        isg[3] = ItemStack(Items.COAL)
    }

    override fun draw(recipe: DNAModifierRecipe, matrixStack: MatrixStack, mouseX: Double, mouseY: Double) {
        super.draw(recipe, matrixStack, mouseX, mouseY)
        Minecraft.getInstance().font.draw(matrixStack, I18n.get("gui.jei.category.smelting.time.seconds", ConfigProperties.defaultDNAModifierProcessTime.get() / 20), 88f, 8f, 0x000000)
    }
}