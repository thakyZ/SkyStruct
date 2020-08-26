package xyz.nekogaming.mods.structure.skystruct.world.gen.structure;

import net.minecraft.structure.Structure;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.WorldView;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.StructureConfig;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import org.apache.logging.log4j.Level;
import xyz.nekogaming.mods.structure.skystruct.SkyStruct;
import xyz.nekogaming.mods.structure.skystruct.structure.EnderTowerStructurePiece;
import xyz.nekogaming.mods.structure.skystruct.utils.CommonUtils;

import javax.annotation.Nullable;

public class EnderTowerStructure extends StructureFeature<DefaultFeatureConfig> {
    protected int spawnrate;
    protected int minHeight;
    protected int maxHeight;

    public EnderTowerStructure(int spawnrate, int minHeight, int maxHeight) {
        super(DefaultFeatureConfig.CODEC);
        this.spawnrate = spawnrate;
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
    }

    @Override
    public StructureStartFactory<DefaultFeatureConfig> getStructureStartFactory() {
        return EnderTowerStructure.Start::new;
    }

    @Nullable
    @Override
    public BlockPos locateStructure(WorldView worldView, StructureAccessor structureAccessor, BlockPos blockPos, int i, boolean skipExistingChunks, long l, StructureConfig structureConfig) {
        return CommonUtils.locateStructureFast(worldView, "tower", structureAccessor, blockPos, skipExistingChunks, l, structureConfig, this);
    }

    public static class Start extends StructureStart<DefaultFeatureConfig> {
        public Start(StructureFeature<DefaultFeatureConfig> structureFeature, int i, int j, BlockBox blockBox, int k, long l) {
            super(structureFeature, i, j, blockBox, k, l);
        }

        public void init(ChunkGenerator chunkGenerator, StructureManager structureManager, int i, int j, Biome biome, DefaultFeatureConfig featureConfig) {
            SkyStruct.LOGGER.log(Level.INFO, "Initialized generating the Ender Tower");
            Identifier ENDER_TOWER = new Identifier(SkyStruct.MODID + ":tower/endertower");
            Structure structure = structureManager.getStructureOrBlank(ENDER_TOWER);
            BlockPos StructureCenterOffset = new BlockPos(structure.getSize().getX() / 2, 0, structure.getSize().getZ() / 2);
            BlockPos ChunkCenter = (new ChunkPos(i, j)).getCenterBlockPos();
            int height = chunkGenerator.getHeight(ChunkCenter.getX(), ChunkCenter.getZ(), Heightmap.Type.WORLD_SURFACE) - 5;
            BlockPos StructureCorner = new BlockPos(ChunkCenter.getX() + 2, height, ChunkCenter.getZ() + 2);
            this.children.add(new EnderTowerStructurePiece(StructureCorner, ENDER_TOWER, structure, StructureCenterOffset));
            this.setBoundingBoxFromChildren();
        }
    }
}
