package com.junnio.mixin;

import com.junnio.util.ExperimentalTrainAccessor;
import com.junnio.util.JunnioConstants;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.minecart.AbstractMinecart;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractMinecart.class)
public class AbstractMinecartCreateMixin {
    @Inject(method = "createMinecart", at = @At("RETURN"))
    private static void polym$transferExperimentalTrainTag(
            Level level,
            double x,
            double y,
            double z,
            EntityType<?> entityType,
            EntitySpawnReason spawnReason,
            ItemStack stack,
            Player player,
            CallbackInfoReturnable<AbstractMinecart> cir
    ) {
        AbstractMinecart minecart = cir.getReturnValue();
        if (minecart == null || stack == null) {
            return;
        }

        CustomData customData = stack.get(DataComponents.CUSTOM_DATA);
        if (customData != null && customData.copyTag().getBooleanOr(JunnioConstants.EXPERIMENTAL_TRAIN_TAG, false)) {
            ((ExperimentalTrainAccessor) minecart).setExperimentalTrain(true);
        }
    }
}
