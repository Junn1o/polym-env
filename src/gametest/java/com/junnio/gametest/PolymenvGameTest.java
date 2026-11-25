package com.junnio.gametest;

import com.junnio.util.DamageTags;
import com.junnio.util.JunnioConstants;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTestHelper;
//? if >=1.21.11 {
import net.minecraft.network.protocol.game.ServerboundPlayerLoadedPacket;
//?}
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.level.GameType;
//? if >=1.21.5 {
import net.fabricmc.fabric.api.gametest.v1.GameTest;
//?} else {
/*import net.fabricmc.fabric.api.gametest.v1.FabricGameTest;
import net.minecraft.gametest.framework.GameTest;
*///?}

//? if >=1.21.5 {
public class PolymenvGameTest {
//?} else {
/*public class PolymenvGameTest implements FabricGameTest {
*///?}

    //? if >=1.21.5 {
    @GameTest
    //?} else {
    /*@GameTest(template = EMPTY_STRUCTURE)
    *///?}
    public void phantomHasReducedHealth(GameTestHelper helper) {
        Phantom phantom = helper.spawn(EntityType.PHANTOM, 1, 2, 1);
        // The mixin sets 0.5, but MAX_HEALTH is clamped to its attribute minimum of 1.0
        check(phantom.getMaxHealth() == 1.0f, "Phantom max health should be 1.0, was " + phantom.getMaxHealth());
        check(phantom.getHealth() <= 1.0f, "Phantom health should be <= 1.0, was " + phantom.getHealth());
        helper.succeed();
    }

    //? if >=1.21.5 {
    @GameTest
    //?} else {
    /*@GameTest(template = EMPTY_STRUCTURE)
    *///?}
    public void damageTagContainsExpectedTypes(GameTestHelper helper) {
        assertInTag(helper, DamageTypes.PLAYER_ATTACK);
        assertInTag(helper, DamageTypes.MACE_SMASH);
        assertInTag(helper, DamageTypes.ARROW);
        assertInTag(helper, DamageTypes.GENERIC_KILL);
        //? if >=1.21.11 {
        assertInTag(helper, DamageTypes.SPEAR);
        //?}
        check(!isInTag(helper, DamageTypes.FELL_OUT_OF_WORLD), "fell_out_of_world must not be in the tag");
        helper.succeed();
    }

    //? if >=1.21.5 {
    @GameTest
    //?} else {
    /*@GameTest(template = EMPTY_STRUCTURE)
    *///?}
    public void chosenOneIgnoresTaggedDamage(GameTestHelper helper) {
        ServerPlayer player = survivalPlayer(helper);
        player.addTag(JunnioConstants.CHOSEN_ONE_TAG);
        float before = player.getHealth();

        boolean hurt = player.hurtServer(helper.getLevel(), helper.getLevel().damageSources().genericKill(), 5.0f);

        check(!hurt, "hurtServer should return false for tagged damage on a chosen_one player");
        check(player.getHealth() == before, "Health changed from " + before + " to " + player.getHealth());
        helper.succeed();
    }

    //? if >=1.21.5 {
    @GameTest
    //?} else {
    /*@GameTest(template = EMPTY_STRUCTURE)
    *///?}
    public void chosenOneStillTakesUntaggedDamage(GameTestHelper helper) {
        ServerPlayer player = survivalPlayer(helper);
        player.addTag(JunnioConstants.CHOSEN_ONE_TAG);
        float before = player.getHealth();

        player.hurtServer(helper.getLevel(), helper.getLevel().damageSources().fellOutOfWorld(), 5.0f);

        check(player.getHealth() < before, "chosen_one player should still take untagged damage");
        helper.succeed();
    }

    //? if >=1.21.5 {
    @GameTest
    //?} else {
    /*@GameTest(template = EMPTY_STRUCTURE)
    *///?}
    public void normalPlayerTakesTaggedDamage(GameTestHelper helper) {
        ServerPlayer player = survivalPlayer(helper);
        float before = player.getHealth();

        player.hurtServer(helper.getLevel(), helper.getLevel().damageSources().genericKill(), 5.0f);

        check(player.getHealth() < before, "Player without chosen_one tag should take tagged damage");
        helper.succeed();
    }

    private static ServerPlayer survivalPlayer(GameTestHelper helper) {
        ServerPlayer player = helper.makeMockServerPlayerInLevel();
        player.setGameMode(GameType.SURVIVAL);
        // Players are invulnerable until the client reports it has loaded the world
        //? if >=1.21.11 {
        player.connection.handleAcceptPlayerLoad(new ServerboundPlayerLoadedPacket());
        //?} else {
        /*player.setClientLoaded(true);
        *///?}
        return player;
    }

    private static boolean isInTag(GameTestHelper helper, ResourceKey<DamageType> key) {
        return helper.getLevel().registryAccess()
                .lookupOrThrow(Registries.DAMAGE_TYPE)
                .getOrThrow(key)
                .is(DamageTags.INVULN_FOR_MY_PLAYER);
    }

    private static void assertInTag(GameTestHelper helper, ResourceKey<DamageType> key) {
        check(isInTag(helper, key), key + " should be in " + DamageTags.INVULN_FOR_MY_PLAYER.location());
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new IllegalStateException(message);
        }
    }
}
