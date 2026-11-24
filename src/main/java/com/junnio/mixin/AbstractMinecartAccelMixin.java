package com.junnio.mixin;

import com.junnio.util.ExperimentalTrainAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.vehicle.minecart.AbstractMinecart;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractMinecart.class)
public abstract class AbstractMinecartAccelMixin implements ExperimentalTrainAccessor {
    @Unique
    private static final double ACCEL_FACTOR = 0.05D;

    @Unique
    private Vec3 polym$preMoveVelocity = Vec3.ZERO;

    @Shadow
    public abstract Vec3 getDeltaMovement();

    @Shadow
    public abstract void setDeltaMovement(Vec3 motion);

    @Inject(method = "moveAlongTrack", at = @At("HEAD"))
    private void polym$captureVelocityBeforeMove(ServerLevel level, CallbackInfo ci) {
        this.polym$preMoveVelocity = this.getDeltaMovement();
    }

    @Inject(method = "moveAlongTrack", at = @At("TAIL"))
    private void polym$smoothTrainAcceleration(ServerLevel level, CallbackInfo ci) {
        if (!this.isExperimentalTrain()) {
            return;
        }

        Vec3 newVelocity = this.getDeltaMovement();
        double newSpeed = newVelocity.length();
        if (newSpeed <= 1.0E-7D) {
            return;
        }

        double oldSpeed = this.polym$preMoveVelocity.length();
        double blendedSpeed = newSpeed > oldSpeed
                ? oldSpeed + (newSpeed - oldSpeed) * ACCEL_FACTOR
                : newSpeed;

        this.setDeltaMovement(newVelocity.scale(blendedSpeed / newSpeed));
    }
}
