package xyz.nekogaming.mods.structure.skystruct.utils;

import javafx.util.Pair;
import net.minecraft.block.BlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import xyz.nekogaming.mods.structure.skystruct.config.MainConfig;

import java.util.List;
import java.util.Objects;

public class CommonUtils {

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

  public static boolean generateInThisDimension(String type, ResourceLocation dimensionId) {
    switch (type) {
      case "tower":
        return checkTowerDimensions(dimensionId);
      default:
        return false;
    }
  }

  protected static boolean checkTowerDimensions(ResourceLocation dimensionId) {
    List<? extends String> dimensionsList = MainConfig.TOWERS.dimensions.get();
    boolean check = dimensionsList.stream().noneMatch(blacklistedDimension -> blacklistedDimension.equals(dimensionId.toString()));
    if (MainConfig.TOWERS.dimensionsIsWhitelist.get()) {
      return !check;
    } else {
      return check;
    }
  }

  public static boolean checkDimensionInWhitelist(World world, String structure) {
    switch (structure) {
      case "tower":
        return checkTowerWhitelist(world);
    }
    return false;
  }

  private static boolean checkTowerWhitelist(World world) {
    boolean test = !MainConfig.TOWERS.dimensionsIsWhitelist.get();
    for (String dimensionKey : MainConfig.TOWERS.dimensions.get()) {
      if (Objects.requireNonNull(world.getServer()).getDynamicRegistries().getRegistry(Registry.DIMENSION_KEY).containsKey(splitStringToResourceLocation(dimensionKey))) {
        test = !test;
      }
    }
    return test;
  }

  public static ResourceLocation splitStringToResourceLocation(String value) {
    String[] split = value.split(":");
    if (split.length == 2) {
      return new ResourceLocation(split[0], split[1]);
    }
    return new ResourceLocation("minecraft", split[0]);
  }
}
