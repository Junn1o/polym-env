package com.junnio.util;

import com.junnio.Polymenv;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;

public final class DamageTags {
    public static final TagKey<DamageType> INVULN_FOR_MY_PLAYER =
            TagKey.create(Registries.DAMAGE_TYPE, Identifier.fromNamespaceAndPath(Polymenv.MOD_ID, "invuln_for_my_player"));

    private DamageTags() {}
}
