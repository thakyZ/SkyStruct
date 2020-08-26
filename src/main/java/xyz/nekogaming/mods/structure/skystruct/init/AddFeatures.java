package xyz.nekogaming.mods.structure.skystruct.init;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.FeatureConfig;
import xyz.nekogaming.mods.structure.skystruct.SkyStruct;

public class AddFeatures {
  public static void addTowers(Biome biome, String biomeNamespace, String biomePath) {
    if (SkyStruct.MainConfig.TowersConfig.spawnrate.enderTowerSpawnrate != 0 && biome.getCategory() != Biome.Category.OCEAN && biome.getCategory() != Biome.Category.RIVER && (biomeNamespace.equals("minecraft") || SkyStruct.MainConfig.TowersConfig.toModdedBiomes)) {
      biome.addStructureFeature(Features.ENDER_TOWER.configure(FeatureConfig.DEFAULT));
    }
  }
}
