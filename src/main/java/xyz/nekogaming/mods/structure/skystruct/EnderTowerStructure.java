package xyz.nekogaming.mods.structure.skystruct;

import net.minecraft.structure.RuinedPortalStructurePiece;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.RuinedPortalFeature;
import net.minecraft.world.gen.feature.StructureFeature;

import java.util.Random;

public class EnderTowerStructure extends StructureFeature<DefaultFeatureConfig> {
    public EnderTowerStructure() {
        super(DefaultFeatureConfig.CODEC);
    }

    @Override
    public StructureStartFactory<DefaultFeatureConfig> getStructureStartFactory() {
        return EnderTowerStructure.Start::new;
    }

    private static int choose(Random random, int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    private static int choosePlacementHeight(Random random, int min, int max) {
        return min < max ? choose(random, min, max) : max;
    }

    private static int getPlacementHeight(Random random, int i, int j) {
        return choosePlacementHeight(random, 70, (i - j));
    }

    public static class Start extends StructureStart<DefaultFeatureConfig> {
        public Start(StructureFeature<DefaultFeatureConfig> structureFeature, int i, int j, BlockBox blockBox, int k, long l) {
            super(structureFeature, i, j, blockBox, k, l);
        }

        public void init(ChunkGenerator chunkGenerator, StructureManager structureManager, int i, int j, Biome biome, DefaultFeatureConfig featureConfig) {
            Identifier identifier2 = new Identifier(SkyStruct.MODID + ":tower/endertower");
            Structure structure = structureManager.getStructureOrBlank(identifier2);
            BlockRotation blockRotation = (BlockRotation) Util.getRandom((Object[])BlockRotation.values(), this.random);
            BlockMirror blockMirror = this.random.nextFloat() < 0.5F ? BlockMirror.NONE : BlockMirror.FRONT_BACK;
            BlockPos blockPos = new BlockPos(structure.getSize().getX() / 2, 0, structure.getSize().getZ() / 2);
            BlockPos blockPos2 = (new ChunkPos(i, j)).getCenterBlockPos();
            BlockBox blockBox = structure.method_27267(blockPos2, blockRotation, blockPos, blockMirror);
            Vec3i vec3i = blockBox.getCenter();
            int k = vec3i.getX();
            int l = vec3i.getZ();
            int m = chunkGenerator.getHeight(k, l, Heightmap.Type.WORLD_SURFACE_WG) - 1;
            int n = EnderTowerStructure.getPlacementHeight(this.random, m, blockBox.getBlockCountY());
            BlockPos blockPos3 = new BlockPos(blockPos2.getX(), n, blockPos2.getZ());
            this.children.add(new EnderTowerStructurePiece(blockPos3, identifier2, structure, blockPos));
            this.setBoundingBoxFromChildren();
        }
    }
}
