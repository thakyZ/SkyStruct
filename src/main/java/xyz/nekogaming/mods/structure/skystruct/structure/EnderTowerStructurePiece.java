package xyz.nekogaming.mods.structure.skystruct.structure;

import com.qouteall.immersive_portals.ModMain;
import net.minecraft.block.BlockState;
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
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import xyz.nekogaming.mods.structure.skystruct.SkyStruct;

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
        this.template = new Identifier(SkyStruct.MODID + ":tower/endertower");
        Structure structure = manager.getStructureOrBlank(this.template);
        this.processProperties(structure, new BlockPos(structure.getSize().getX() / 2, 0, structure.getSize().getZ() / 2));
    }

    private void processProperties(Structure structure, BlockPos center) {
        StructurePlacementData structurePlacementData = (new StructurePlacementData()).setRotation(BlockRotation.NONE).setMirror(BlockMirror.NONE).setPosition(center).addProcessor(BlockIgnoreStructureProcessor.IGNORE_STRUCTURE_BLOCKS);
        this.setStructureData(structure, this.pos, structurePlacementData);
    }

    public boolean generate(ServerWorldAccess serverWorldAccess, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox boundingBox, ChunkPos chunkPos, BlockPos blockPos) {
        boolean returnVar = false;
        if (ModMain.isAlternateDimension(serverWorldAccess.getWorld())) {
            if (blockPos.getY() > 0) {
                BlockPos topPos = serverWorldAccess.getTopPosition(Heightmap.Type.WORLD_SURFACE, blockPos);
                returnVar = super.generate(serverWorldAccess, structureAccessor, chunkGenerator, random, boundingBox, chunkPos, topPos);

                BlockPos redstoneWirePos = blockPos.add(-2, 2, -3);
                BlockPos redstoneBlockPos = blockPos.add(-2, 3, -3);
                BlockPos commandBlockOnePos = blockPos.add(-2, 1, -2);
                BlockPos commandBlockTwoPos = blockPos.add(-2, 2, -3);

                //try
                //{
                //serverWorldAccess.setBlockState(redstoneWirePos, Blocks.REDSTONE_BLOCK.getDefaultState(), 2);

                //serverWorldAccess.setBlockState(redstoneBlockPos, Blocks.REDSTONE_BLOCK.getDefaultState(), 2);

                //serverWorldAccess.setBlockState(redstoneWirePos, Blocks.REDSTONE_WIRE.getDefaultState().with(WIRE_CONNECTION_NORTH, WireConnection.SIDE).with(WIRE_CONNECTION_EAST, WireConnection.SIDE).with(WIRE_CONNECTION_SOUTH, WireConnection.SIDE).with(WIRE_CONNECTION_WEST, WireConnection.SIDE).with(POWER, 15), 2);
                //}
                //catch (Exception ex)
                //{
                //    SkyStruct.LOGGER.error("Failed to set block");
                //    returnVar = false;
                //}
                //finally {
                    /*BlockState redstoneBlock = serverWorldAccess.getBlockState(redstoneWirePos);
                    redstoneBlock.updateNeighbors(serverWorldAccess, redstoneWirePos, 2, 512);
                    serverWorldAccess.updateNeighbors(redstoneWirePos, redstoneBlock.getBlock());*/

                BlockState redstoneBlock = serverWorldAccess.getBlockState(redstoneBlockPos);
                redstoneBlock.updateNeighbors(serverWorldAccess, redstoneBlockPos, 2, 512);
                serverWorldAccess.updateNeighbors(redstoneBlockPos, redstoneBlock.getBlock());

                BlockState redstoneWire = serverWorldAccess.getBlockState(redstoneWirePos);
                redstoneWire.updateNeighbors(serverWorldAccess, redstoneWirePos, 2, 512);
                serverWorldAccess.updateNeighbors(redstoneWirePos, redstoneWire.getBlock());

                BlockState commandBlockOne = serverWorldAccess.getBlockState(commandBlockOnePos);
                commandBlockOne.updateNeighbors(serverWorldAccess, commandBlockOnePos, 2, 512);
                serverWorldAccess.updateNeighbors(commandBlockTwoPos, commandBlockOne.getBlock());

                BlockState commandBlockTwo = serverWorldAccess.getBlockState(commandBlockTwoPos);
                commandBlockTwo.updateNeighbors(serverWorldAccess, commandBlockTwoPos, 2, 512);
                serverWorldAccess.updateNeighbors(commandBlockTwoPos, commandBlockOne.getBlock());
                //}
            }
        }
        return returnVar;
    }

    protected void handleMetadata(String metadata, BlockPos pos, WorldAccess world, Random random, BlockBox boundingBox) {
    }
}
