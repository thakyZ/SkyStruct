package xyz.nekogaming.mods.structure.skystruct;

import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xyz.nekogaming.mods.structure.skystruct.config.MainConfig;
import xyz.nekogaming.mods.structure.skystruct.init.AddFeatures;
import xyz.nekogaming.mods.structure.skystruct.init.Features;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SkyStruct implements ModInitializer {
    public static final Logger LOGGER = LogManager.getLogger("SkyStruct");
    public static final String MODID = "skystruct";

    public static MainConfig MainConfig = null;

    public static Identifier id(String name) {
        return new Identifier(MODID, name);
    }

    public static void addFeaturesToBiomes(Biome biome, Identifier biomeID, Map<String, List<String>> allBiomeList) {
        String biomeNamespace = biomeID.getNamespace();
        String biomePath = biomeID.getPath();

        if (isBiomeAllowed("tower", biomeID, allBiomeList, MainConfig.TowersConfig.biomesIsWhitelist)) {
            AddFeatures.addTowers(biome, biomeNamespace, biomePath);
        }
    }

    public static boolean isBiomeAllowed(String structureType, Identifier biomeID, Map<String, List<String>> allBiomeList, boolean isWhitelist) {
        boolean check = allBiomeList.get(structureType).stream().noneMatch(blacklistedBiome -> blacklistedBiome.equals(biomeID.toString()));
        if (isWhitelist) {
            return !check;
        } else {
            return check;
        }
    }

    @Override
    public void onInitialize() {
        AutoConfig.register(MainConfig.class, JanksonConfigSerializer::new);
        MainConfig = AutoConfig.getConfigHolder(MainConfig.class).getConfig();

        Features.registerFeatures();

        //Gets filtered biome IDs for each structure type
        //Done here so the map can be garbage collected later
        Map<String, List<String>> allBiomesLists = new HashMap<>();
        allBiomesLists.put("tower", Arrays.asList(MainConfig.TowersConfig.biomes.enderTowerBiomes.split(",")));

        for (Biome biome : Registry.BIOME) {
            addFeaturesToBiomes(biome, Registry.BIOME.getId(biome), allBiomesLists);
        }

        RegistryEntryAddedCallback.event(Registry.BIOME).register((i, identifier, biome) -> addFeaturesToBiomes(biome, identifier, allBiomesLists));
        SkyStruct.LOGGER.log(Level.INFO, "Skylands Structures has been imitialized!");
    }
}
