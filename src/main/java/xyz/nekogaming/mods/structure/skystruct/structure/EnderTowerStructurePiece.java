package xyz.nekogaming.mods.structure.skystruct.structure;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.structure.SimpleStructurePiece;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.processor.BlockIgnoreStructureProcessor;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import org.apache.logging.log4j.Level;
import xyz.nekogaming.mods.structure.skystruct.SkyStruct;
import xyz.nekogaming.mods.structure.skystruct.init.Features;
import xyz.nekogaming.mods.structure.skystruct.utils.CommonUtils;

import java.util.Random;

public class EnderTowerStructurePiece extends SimpleStructurePiece {
    private final Identifier template;

    public EnderTowerStructurePiece(BlockPos pos, Identifier template, Structure structure, BlockPos center) {
        super(Features.ENDER_TOWER_PIECE, 0);
        this.pos = pos;
        this.template = template;
        this.processProperties(structure, center);
    }

    public EnderTowerStructurePiece(StructureManager manager, CompoundTag tag) {
        super(Features.ENDER_TOWER_PIECE, tag);
        this.template = new Identifier(SkyStruct.MODID + ":tower/endertower");
        Structure structure = manager.getStructureOrBlank(this.template);
        this.processProperties(structure, new BlockPos(structure.getSize().getX() / 2, 0, structure.getSize().getZ() / 2));
    }

    private void processProperties(Structure structure, BlockPos center) {
        StructurePlacementData structurePlacementData = (new StructurePlacementData()).setRotation(BlockRotation.NONE).setMirror(BlockMirror.NONE).setPosition(center).addProcessor(BlockIgnoreStructureProcessor.IGNORE_STRUCTURE_BLOCKS);
        this.setStructureData(structure, this.pos, structurePlacementData);
    }

    @Override
    public boolean generate(StructureWorldAccess structureWorldAccess, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox boundingBox, ChunkPos chunkPos, BlockPos blockPos) {
        boolean returnVar = false;
        SkyStruct.LOGGER.log(Level.INFO, String.format("BlockPos: [~, %d, ~] || Config: [%d, %d] || Box: [%d] || Calc: [%d] || dim: [%s]", blockPos.getY(), SkyStruct.MainConfig.TowersConfig.minHeight.enderTowerMinHeight, SkyStruct.MainConfig.TowersConfig.maxHeight.enderTowerMaxHeight, this.structure.getSize().getY(), (SkyStruct.MainConfig.TowersConfig.maxHeight.enderTowerMaxHeight - this.structure.getSize().getY()), structureWorldAccess.toServerWorld().getRegistryKey().getValue()));
        if (CommonUtils.generateInThisDimension("tower", structureWorldAccess.toServerWorld().getRegistryKey().getValue())) {
            if (blockPos.getY() > SkyStruct.MainConfig.TowersConfig.minHeight.enderTowerMinHeight && blockPos.getY() < (SkyStruct.MainConfig.TowersConfig.maxHeight.enderTowerMaxHeight - this.structure.getSize().getY())) {
                SkyStruct.LOGGER.log(Level.INFO, "Generating the Ender Tower Piece");
                BlockPos topPos = structureWorldAccess.getTopPosition(Heightmap.Type.WORLD_SURFACE, blockPos);
                returnVar = super.generate(structureWorldAccess, structureAccessor, chunkGenerator, random, boundingBox, chunkPos, topPos);
            }
        }
        return returnVar;
    }

    @Override
    protected void handleMetadata(String metadata, BlockPos pos, ServerWorldAccess serverWorldAccess, Random random, BlockBox boundingBox) {
    }
}
