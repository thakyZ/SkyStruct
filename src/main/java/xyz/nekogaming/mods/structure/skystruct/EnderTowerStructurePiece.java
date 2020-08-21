package xyz.nekogaming.mods.structure.skystruct;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.structure.*;
import net.minecraft.structure.pool.*;
import net.minecraft.structure.processor.*;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.Identifier;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Random;

public class EnderTowerStructurePiece extends SimpleStructurePiece {
    private final Identifier template;

    public EnderTowerStructurePiece(BlockPos pos, Identifier template, Structure structure, BlockPos center) {
        super(SkyStruct.ENDER_TOWER_PIECE, 0);
        this.pos = pos;
        this.template = template;
        this.processProperties(structure, center);
    }

    public EnderTowerStructurePiece(StructureManager manager, CompoundTag tag) {
        super(SkyStruct.ENDER_TOWER_PIECE, tag);
        this.template = new Identifier(tag.getString("Template"));
        Structure structure = manager.getStructureOrBlank(this.template);
        this.processProperties(structure, new BlockPos(structure.getSize().getX() / 2, 0, structure.getSize().getZ() / 2));
    }

    private void processProperties(Structure structure, BlockPos center) {
        StructurePlacementData structurePlacementData = (new StructurePlacementData()).setRotation(BlockRotation.NONE).setMirror(BlockMirror.NONE).setPosition(center).addProcessor(BlockIgnoreStructureProcessor.IGNORE_STRUCTURE_BLOCKS);;

        this.setStructureData(structure, this.pos, structurePlacementData);
    }

    public boolean generate(ServerWorldAccess serverWorldAccess, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox boundingBox, ChunkPos chunkPos, BlockPos blockPos) {
        if (!boundingBox.contains(this.pos)) {
            return true;
        }
        else {
            boundingBox.encompass(this.structure.calculateBoundingBox(this.placementData, this.pos));
            return super.generate(serverWorldAccess, structureAccessor, chunkGenerator, random, boundingBox, chunkPos, blockPos);
        }
    }

    protected void handleMetadata(String metadata, BlockPos pos, WorldAccess world, Random random, BlockBox boundingBox) {
    }
}
