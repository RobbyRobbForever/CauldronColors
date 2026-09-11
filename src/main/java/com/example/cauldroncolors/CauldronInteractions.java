package com.example.cauldroncolors;

public final class CauldronInteractions {
    private CauldronInteractions() {
    }

    public static void register() {
        WaterCauldronInteractions.register();
        ColoredWaterCauldronInteractions.register();
        CopperCauldronInteractions.register();
    }
}