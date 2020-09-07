package xyz.nekogaming.mods.structure.skystruct.config;

import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;
import me.sargunvohra.mcmods.autoconfig1u.annotation.ConfigEntry;
import me.sargunvohra.mcmods.autoconfig1u.shadowed.blue.endless.jankson.Comment;

@Config(name = "Toweres")
public class TowersConfig implements ConfigData {
    @ConfigEntry.Gui.CollapsibleObject
    @ConfigEntry.Gui.Tooltip
    @Comment("Add the towers to modded biomes of the same categories and types.")
    public final boolean toModdedBiomes = true;

    @ConfigEntry.Gui.CollapsibleObject
    public final Biomes biomes = new Biomes();

    @ConfigEntry.Gui.Tooltip
    @Comment("Make the tower biome list as a whitelist or a blacklist.")
    public final boolean biomesIsWhitelist = false;

    @ConfigEntry.Gui.CollapsibleObject
    public final Dimensions dimensions = new Dimensions();

    @ConfigEntry.Gui.Tooltip
    @Comment("Set the dimension list as a whitelist or a blacklist.")
    public final boolean dimensionsIsWhitelist = false;

    @ConfigEntry.Gui.CollapsibleObject
    public final Spawnrate spawnrate = new Spawnrate();

    @ConfigEntry.Gui.CollapsibleObject
    public final MaxHeight maxHeight = new MaxHeight();

    @ConfigEntry.Gui.CollapsibleObject
    public final MinHeight minHeight = new MinHeight();

    public static class Biomes {
        @ConfigEntry.Gui.Tooltip(count = 3)
        @Comment("Add the ID/resource location of the biome you don't want"
            + "\nthe towers to spawn in. Separate each ID with a comma ,"
            + "\n"
            + "\nExample: \"minecraft:ice_spikes,example_mod:example_biome\"")
        public final String enderTowerBiomes = "";
    }

    public static class Dimensions {
        @ConfigEntry.Gui.Tooltip(count = 4)
        @Comment("Add the ID/resource location of the dimension you don't want"
            + "\nthe towers to spawn in. Separate each ID with a comma ,"
            + "\n"
            + "\nExample: \"minecraft:the_nether,example_mod:example_dim\""
            + "\nDefault: \"minecraft:the_nether,minecraft:the_end\"")
        public final String enderTowerDimensions = "minecraft:the_nether,minecraft:the_end";
    }

    public static class Spawnrate {
        @ConfigEntry.Gui.Tooltip(count = 2)
        @Comment("Spawn attempts per chunk."
            + "\n0 for no towers at all and 1000 for max spawnrate.")
        @ConfigEntry.BoundedDiscrete(min = 0, max = 1000)
        public final int enderTowerSpawnrate = 90;
    }

    public static class MaxHeight {
        @ConfigEntry.Gui.Tooltip(count = 3)
        @Comment("Maximum Y height that this dungeon can spawn at. Default is 255."
            + "\nNote: The dungeon will spawn between min and max y height set in config."
            + "\nSetting this to below min height config will make dungeon spawn only at min height.")
        @ConfigEntry.BoundedDiscrete(min = 2, max = 255)
        public final int enderTowerMaxHeight = 255;
    }

    public static class MinHeight {
        @ConfigEntry.Gui.Tooltip(count = 2)
        @Comment("Minimum Y height that this dungeon can spawn at. Default is 2."
            + "\nNote: The dungeon will spawn between min and max y height set in config.")
        @ConfigEntry.BoundedDiscrete(min = 2, max = 255)
        public final int enderTowerMinHeight = 30;
    }
}
