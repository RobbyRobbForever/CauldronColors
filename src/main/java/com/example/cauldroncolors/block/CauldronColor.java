package com.example.cauldroncolors.block;

import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.DyeColor;

import java.util.Locale;

public enum CauldronColor implements StringRepresentable {
    WHITE,
    TRUE_BLUE,
    TRUE_BROWN,
    MAGENTA,
    LIGHT_BLUE,
    YELLOW,
    LIME,
    PINK,
    GRAY,
    LIGHT_GRAY,
    CYAN,
    PURPLE,
    GREEN,
    RED,
    ORANGE,
    BLACK;

    @Override
    public String getSerializedName() {
        return name().toLowerCase(Locale.ROOT);
    }

    public static CauldronColor fromDyeColor(DyeColor dyeColor) {
        return switch (dyeColor) {
            case WHITE -> WHITE;
            case BLUE -> TRUE_BLUE;
            case BROWN -> TRUE_BROWN;
            case MAGENTA -> MAGENTA;
            case LIGHT_BLUE -> LIGHT_BLUE;
            case YELLOW -> YELLOW;
            case LIME -> LIME;
            case PINK -> PINK;
            case GRAY -> GRAY;
            case LIGHT_GRAY -> LIGHT_GRAY;
            case CYAN -> CYAN;
            case PURPLE -> PURPLE;
            case GREEN -> GREEN;
            case RED -> RED;
            case ORANGE -> ORANGE;
            case BLACK -> BLACK;
        };
    }
}
