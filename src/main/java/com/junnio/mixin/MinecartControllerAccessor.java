package com.junnio.mixin;

import net.minecraft.world.entity.vehicle.minecart.AbstractMinecart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(net.minecraft.world.entity.vehicle.minecart.MinecartBehavior.class)
public interface MinecartControllerAccessor {
    @Accessor("minecart")
    AbstractMinecart getMinecart();
}
