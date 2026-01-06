package com.junnio.mixin;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Phantom.class)
public class PhantomHealthMixin {
    @Inject(method = "<init>", at = @At("TAIL"))
    private void modifyHealth(EntityType<? extends Phantom> entityType, Level world, CallbackInfo ci) {
        Phantom phantom = (Phantom)(Object)this;
        AttributeInstance healthAttribute = phantom.getAttribute(Attributes.MAX_HEALTH);

        if (healthAttribute != null) {
            healthAttribute.setBaseValue(0.5);
            phantom.setHealth(1.0f);
        }
    }
}