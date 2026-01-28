package com.junnio.mixin;

import com.junnio.util.DamageTags;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
abstract class Invincible {
    @Inject(
            method = "hurtServer", // or the appropriate hurt method in your target mappings
            at = @At("HEAD"),
            cancellable = true
    )
    private void polym$invulnToTaggedDamage(ServerLevel level, DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity self = (LivingEntity)(Object)this;
        if (self instanceof ServerPlayer player && isYourSpecialPlayer(player)) {
            if (source.typeHolder().is(DamageTags.INVULN_FOR_MY_PLAYER)) {
                cir.setReturnValue(false); // “damage not applied”
            }
        }
    }
    //Junnio id
//    private static final UUID SPECIAL_UUID =
//            UUID.fromString("705e0a2e-2893-42c8-b31e-f4f6ea6486a1");
//    private static boolean isYourSpecialPlayer(ServerPlayer player) {
//        return player.getUUID().equals(SPECIAL_UUID);
//    }
    private static final String TAG = "chosen_one";
    private static boolean isYourSpecialPlayer(ServerPlayer player) {
        return player.getTags().contains(TAG);
    }
}
