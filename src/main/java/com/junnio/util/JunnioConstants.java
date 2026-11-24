package com.junnio.util;

import net.minecraft.resources.Identifier;

import java.util.UUID;

public final class JunnioConstants {
    public static final UUID JUNNIO_UUID = UUID.fromString("705e0a2e-2893-42c8-b31e-f4f6ea6486a1");
    public static final Identifier EXPERIMENTAL_MINECART_RECIPE_ID =
            Identifier.fromNamespaceAndPath("polym-env", "experimental_minecart");
    public static final String EXPERIMENTAL_TRAIN_TAG = "ExperimentalTrain";

    private JunnioConstants() {
    }
}
