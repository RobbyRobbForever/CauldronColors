package com.example.cauldroncolors;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import java.util.OptionalInt;

public final class CauldronInteractionUtil {
    private CauldronInteractionUtil() {
    }

    public enum InteractionOutcome {
        APPLIED,
        NOT_APPLICABLE,
        BLOCKED
    }

    public static InteractionResult toInteractionResult(
            InteractionOutcome outcome
    ) {
        return switch (outcome) {
            case APPLIED -> InteractionResult.SUCCESS;
            case NOT_APPLICABLE -> InteractionResult.PASS;
            case BLOCKED -> InteractionResult.CONSUME;
        };
    }

    public static void giveOrDrop(Player player, ItemStack stack) {
        if (!player.getInventory().add(stack)) {
            player.drop(stack, false);
        }
    }

    public static SoundEvent getVanillaSound(String path) {
        Identifier soundIdentifier =
                Identifier.withDefaultNamespace(path);

        return BuiltInRegistries.SOUND_EVENT.getValue(soundIdentifier);
    }
    public static OptionalInt tryApplyOneUnit(
            int currentAmount,
            int boundary,
            int delta
    ) {
        if (delta == 0
                || (delta > 0 && currentAmount >= boundary)
                || (delta < 0 && currentAmount <= boundary)) {
            return OptionalInt.empty();
        }

        int nextAmount = Math.max(0, Math.min(boundary, currentAmount + delta));
        return OptionalInt.of(nextAmount);
    }
    public static int levelForEggsDyed(int eggsDyed) {
        if (eggsDyed < 16) {
            return 3;
        }
        if (eggsDyed < 32) {
            return 2;
        }
        return 1;
    }
}