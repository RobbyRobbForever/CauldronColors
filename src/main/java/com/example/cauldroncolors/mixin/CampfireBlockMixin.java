package com.example.cauldroncolors.mixin;

import com.example.cauldroncolors.CauldronColors;
import com.example.cauldroncolors.block.CopperCauldronBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CampfireBlock.class)
public abstract class CampfireBlockMixin {
    @Inject(method = "makeParticles", at = @At("HEAD"), cancellable = true)
    private static void cauldroncolors$suppressSmoke(
            Level level,
            BlockPos pos,
            boolean isSignal,
            boolean spawnExtraSmoke,
            CallbackInfo callbackInfo
    ) {
        BlockState blockAbove = level.getBlockState(pos.above());

        boolean isCopperCauldron = blockAbove.is(CauldronColors.COPPER_CAULDRON);
        boolean isLavaFilledCopperCauldron =
                isCopperCauldron && blockAbove.getValue(CopperCauldronBlock.LAVA);

        if (blockAbove.is(BlockTags.CAULDRONS)
                || blockAbove.is(CauldronColors.COLORED_WATER_CAULDRON)
                || (isCopperCauldron && !isLavaFilledCopperCauldron)) {
            callbackInfo.cancel();
        }
    }
}