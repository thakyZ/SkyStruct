package xyz.nekogaming.mods.structure.skystruct.config;

import com.google.common.collect.ImmutableList;
import net.minecraft.world.DimensionType;
import net.minecraft.world.biome.Biomes;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import xyz.nekogaming.mods.structure.skystruct.SkyStruct;

import java.util.List;

@SuppressWarnings("WeakerAccess")
@EventBusSubscriber(modid = SkyStruct.MODID, bus = Bus.MOD)
public class MainConfig {
  public static class Towers {
    public final BooleanValue toModdedBiomes;// = true;

    public final ConfigValue<List<? extends String>> biomes;

    public final BooleanValue biomesIsWhitelist;// = false;

    public final ConfigValue<List<? extends String>> dimensions;

    public final BooleanValue dimensionsIsWhitelist;// = false;

    public final IntValue spawnrate;// = 90;

    public final IntValue maxHeight;//  = 255;

    public final IntValue minHeight;// = 30;

    Towers(ForgeConfigSpec.Builder builder) {
      builder.comment("Ender Tower configuration settings")
              .push("towersconfig");

      toModdedBiomes = builder
              .comment("Add the towers to modded biomes of the same categories and types.")
              .translation("text.autoconfig.skystruct.option.TowersConfig.toModdedBiomes")
              .define("toModdedBiomes", true);

      biomes = builder
              .comment("Add the ID/resource location of the biome you don't want\nthe towers to spawn in. Separate each ID with a comma ,\nExample: \"minecraft:ice_spikes,example_mod:example_biome\"")
              .translation("text.autoconfig.skystruct.option.TowersConfig.biomes.enderTowerBiomes")
              .defineList("biomes", ImmutableList.of(Biomes.END_HIGHLANDS.getLocation().toString()), obj -> true);

      biomesIsWhitelist = builder
              .comment("Set to true to make the biomes List as a whitelist, and false for a blacklist.")
              .translation("text.autoconfig.skystruct.option.TowersConfig.biomesIsWhitelist")
              .define("biomesIsWhitelist", false);

      dimensions = builder
              .comment("Add the ID/resource location of the dimension you don't want\nthe towers to spawn in. Separate each ID with a comma ,\nExample: \"minecraft:the_nether,example_mod:example_dim\"\nDefault: \"minecraft:the_nether,minecraft:the_end\"")
              .translation("text.autoconfig.skystruct.option.TowersConfig.dimensions.enderTowerDimensions")
              .defineList("dimension", ImmutableList.of(DimensionType.THE_END.getLocation().toString()), obj -> true);

      dimensionsIsWhitelist = builder
              .comment("Set the dimension list as a whitelist or a blacklist.")
              .translation("text.autoconfig.skystruct.option.TowersConfig.dimensionsIsWhitelist")
              .define("dimensionsIsWhitelist", false);

      spawnrate = builder
              .comment("Spawn attempts per chunk.\n0 for no towers at all and 1000 for max spawnrate.")
              .translation("text.autoconfig.skystruct.option.TowersConfig.spawnrate.enderTowerSpawnrate")
              .worldRestart()
              .defineInRange("spawnrate", 90, 0, 1000);

      maxHeight = builder
              .comment("Maximum Y height that this dungeon can spawn at. Default is 255.\nNote: The dungeon will spawn between min and max y height set in config.\nSetting this to below min height config will make dungeon spawn only at min height.")
              .translation("text.autoconfig.skystruct.option.TowersConfig.maxHeight.enderTowerMaxHeight")
              .worldRestart()
              .defineInRange("maxHeight", 255, 2, 255);

      minHeight = builder
              .comment("Minimum Y height that this dungeon can spawn at. Default is 2.\nNote: The dungeon will spawn between min and max y height set in config.")
              .translation("text.autoconfig.skystruct.option.TowersConfig.minHeight.enderTowerMinHeight")
              .worldRestart()
              .defineInRange("minHeight", 30, 1, 254);
    }
  }

  public static final Towers TOWERS;

  static final ForgeConfigSpec clientSpec;

  static {
    final Pair<Towers, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Towers::new);
    clientSpec = specPair.getRight();
    TOWERS = specPair.getLeft();
  }

  @SubscribeEvent
  public static void onLoad(final ModConfig.Loading configEvent) {
    LogManager.getLogger().debug("Loaded SkyStruct config file {}", configEvent.getConfig().getFileName());
  }

  @SubscribeEvent
  public static void onFileChange(final ModConfig.Reloading configEvent) {
    LogManager.getLogger().debug("SkyStruct config just got changed on the file system!");
  }
}
