package com.example.cauldroncolors.client;

import com.example.cauldroncolors.CauldronColors;
import com.example.cauldroncolors.block.CauldronColor;
import com.example.cauldroncolors.block.ColoredWaterCauldronBlock;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;

public class CauldronColorsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ColorProviderRegistry.BLOCK.register(
            (state, world, pos, tintIndex) ->
                getColor(state.getValue(ColoredWaterCauldronBlock.COLOR)),
            CauldronColors.COLORED_WATER_CAULDRON
        );

        ColorProviderRegistry.BLOCK.register(
            (state, world, pos, tintIndex) -> 0x3F76E4,
            CauldronColors.COPPER_CAULDRON
        );
    }

    private static int getColor(CauldronColor color) {
        return switch (color) {
            case WHITE -> 0xFFFFFF;
            case TRUE_BLUE -> 0x3F76E4;
            case TRUE_BROWN -> 0x835432;
            case MAGENTA -> 0xC74EBD;
            case LIGHT_BLUE -> 0x3AB3DA;
            case YELLOW -> 0xFED83D;
            case LIME -> 0x80C71F;
            case PINK -> 0xF38BAA;
            case GRAY -> 0x474F52;
            case LIGHT_GRAY -> 0x9D9D97;
            case CYAN -> 0x169C9C;
            case PURPLE -> 0x8932B8;
            case GREEN -> 0x5E7C16;
            case RED -> 0xB02E26;
            case ORANGE -> 0xF9801D;
            case BLACK -> 0x1D1D21;
        };
    }
}

