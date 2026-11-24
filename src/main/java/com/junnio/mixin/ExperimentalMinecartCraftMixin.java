package com.junnio.mixin;

import com.junnio.util.JunnioConstants;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CraftingMenu.class)
public class ExperimentalMinecartCraftMixin {
    @Inject(method = "slotChangedCraftingGrid", at = @At("TAIL"))
    private static void polym$restrictExperimentalMinecart(
            AbstractContainerMenu menu,
            ServerLevel level,
            Player player,
            CraftingContainer craftSlots,
            ResultContainer resultSlots,
            RecipeHolder<CraftingRecipe> recipe,
            CallbackInfo ci
    ) {
        if (recipe == null || !recipe.id().identifier().equals(JunnioConstants.EXPERIMENTAL_MINECART_RECIPE_ID)) {
            return;
        }

        if (player.getUUID().equals(JunnioConstants.JUNNIO_UUID)) {
            ItemStack result = resultSlots.getItem(0);
            if (!result.isEmpty()) {
                CompoundTag tag = new CompoundTag();
                tag.putBoolean(JunnioConstants.EXPERIMENTAL_TRAIN_TAG, true);
                result.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
            }
        } else {
            resultSlots.setItem(0, ItemStack.EMPTY);
        }
    }
}
