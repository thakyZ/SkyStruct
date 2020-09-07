package xyz.nekogaming.mods.structure.skystruct.mixins;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.LakeFeature;
import net.minecraft.world.gen.feature.SingleStateFeatureConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.nekogaming.mods.structure.skystruct.init.Features;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Mixin(LakeFeature.class)
public class LakeFeatureMixin {
    @Inject(at = @At("HEAD"), method = "generate", cancellable = true)
    public void fixStructures(StructureWorldAccess structureWorldAccess, ChunkGenerator chunkGenerator, Random random, BlockPos blockPos, SingleStateFeatureConfig singleStateFeatureConfig, CallbackInfoReturnable<Boolean> info) {
        List<Chunk> chunksToScan = new ArrayList<>(9);
        chunksToScan.add(structureWorldAccess.getChunk(blockPos));
        chunksToScan.add(structureWorldAccess.getChunk(blockPos.add(16, 0, 16)));
        chunksToScan.add(structureWorldAccess.getChunk(blockPos.add(-16, 0, -16)));
        chunksToScan.add(structureWorldAccess.getChunk(blockPos.add(0, 0, 16)));
        chunksToScan.add(structureWorldAccess.getChunk(blockPos.add(16, 0, 0)));
        chunksToScan.add(structureWorldAccess.getChunk(blockPos.add(-16, 0, 0)));
        chunksToScan.add(structureWorldAccess.getChunk(blockPos.add(0, 0, -16)));
        chunksToScan.add(structureWorldAccess.getChunk(blockPos.add(16, 0, -16)));
        chunksToScan.add(structureWorldAccess.getChunk(blockPos.add(-16, 0, 16)));
        for (Chunk chunk : chunksToScan) {
            if (!chunk.getStructureReferences(Features.ENDER_TOWER_CONFIG.feature).isEmpty()) {
                info.setReturnValue(false);
                break;
            }
        }
    }
}
