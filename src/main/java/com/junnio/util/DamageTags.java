package com.junnio.util;

import com.junnio.Polymenv;
import net.minecraft.core.registries.Registries;
//? if >=1.21.11 {
import net.minecraft.resources.Identifier;
//?} else {
/*import net.minecraft.resources.ResourceLocation;
*///?}
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;

public final class DamageTags {
    public static final TagKey<DamageType> INVULN_FOR_MY_PLAYER =
            //? if >=1.21.11 {
            TagKey.create(Registries.DAMAGE_TYPE, Identifier.fromNamespaceAndPath(Polymenv.MOD_ID, "invuln_for_my_player"));
            //?} else {
            /*TagKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(Polymenv.MOD_ID, "invuln_for_my_player"));
            *///?}

    private DamageTags() {}
}
