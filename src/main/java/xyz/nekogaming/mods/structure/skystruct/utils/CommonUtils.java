package xyz.nekogaming.mods.structure.skystruct.utils;

import com.mojang.datafixers.util.Pair;
import net.minecraft.block.BlockState;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.StructureConfig;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import xyz.nekogaming.mods.structure.skystruct.SkyStruct;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

public class CommonUtils {
    public static BlockPos locateStructureFast(WorldView worldView, String structureType, StructureAccessor structureAccessor, BlockPos blockPos, boolean skipExistingChunks, long seed, StructureConfig structureConfig, StructureFeature<DefaultFeatureConfig> structure) {
        int spacing = structureConfig.getSpacing();
        int chunkX = blockPos.getX() >> 4;
        int chunkZ = blockPos.getZ() >> 4;
        int currentRadius = 0;
        BlockPos.Mutable mutable = new BlockPos.Mutable();

        if (CommonUtils.generateInThisDimension(structureType, ((World) worldView).getRegistryKey().getValue())) {
            for (ChunkRandom chunkRandom = new ChunkRandom(); currentRadius <= 100000 / structureConfig.getSpacing(); ++currentRadius) {
                for (int xRadius = -currentRadius; xRadius <= currentRadius; ++xRadius) {
                    boolean xEdge = xRadius == -currentRadius || xRadius == currentRadius;

                    for (int zRadius = -currentRadius; zRadius <= currentRadius; ++zRadius) {
                        boolean zEdge = zRadius == -currentRadius || zRadius == currentRadius;
                        if (xEdge || zEdge) {
                            int trueChunkX = chunkX + spacing * xRadius;
                            int trueChunkZ = chunkZ + spacing * zRadius;
                            if (worldView.getBiome(mutable.set(trueChunkX << 4, 1, trueChunkZ << 4)).hasStructureFeature(structure)) {
                                ChunkPos chunkPos = structure.method_27218(structureConfig, seed, chunkRandom, trueChunkX, trueChunkZ);
                                Chunk chunk = worldView.getChunk(chunkPos.x, chunkPos.z, ChunkStatus.STRUCTURE_STARTS);
                                StructureStart<?> structureStart = structureAccessor.getStructureStart(ChunkSectionPos.from(chunk.getPos(), 0), structure, chunk);
                                if (structureStart != null && structureStart.hasChildren()) {
                                    if (skipExistingChunks && structureStart.isInExistingChunk()) {
                                        structureStart.incrementReferences();
                                        return structureStart.getPos();
                                    }

                                    if (!skipExistingChunks) {
                                        return structureStart.getPos();
                                    }
                                }
                            }
                        } else {
                            zRadius = currentRadius - 1;
                        }
                    }
                }
            }
        }

        return null;
    }

    @Nullable
    public static Pair<BlockPos, BlockState> getChunkBlock(BlockPos origin, BlockPos offset, BlockState expectedState, Chunk chunk) {
        BlockPos i = origin.add(offset);
        BlockPos pos = null;
        BlockState state = null;

        for (int y = 0; y < (255 - offset.getY() - 1); y++) {
            BlockState gotState = chunk.getBlockState(new BlockPos(i.getX(), i.getY() + y, i.getZ()));
            if (gotState == expectedState) {
                state = gotState;
                pos = new BlockPos(i.getX(), i.getY() + y, i.getZ());
                break;
            }
        }

        if (pos != null && state != null) {
            return new Pair<>(pos, state);
        }
        return null;
    }

    public static boolean generateInThisDimension(String type, Identifier dimensionId) {
        switch (type) {
            case "tower":
                return checkTowerDimensions(dimensionId);
            default:
                return false;
        }
    }

    protected static boolean checkTowerDimensions(Identifier dimensionId) {
        List<String> dimensionsList = Arrays.asList(SkyStruct.MainConfig.TowersConfig.dimensions.enderTowerDimensions.split(","));
        boolean check = dimensionsList.stream().noneMatch(blacklistedDimension -> blacklistedDimension.equals(dimensionId.toString()));
        if (SkyStruct.MainConfig.TowersConfig.dimensionsIsWhitelist) {
            return !check;
        } else {
            return check;
        }
    }
}
