package xyz.nekogaming.mods.structure.skystruct;

import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.*;
import net.minecraft.world.biome.Biome;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xyz.nekogaming.mods.structure.skystruct.config.MainConfig;
import xyz.nekogaming.mods.structure.skystruct.init.AddFeatures;
import xyz.nekogaming.mods.structure.skystruct.init.Features;

import java.util.List;
import java.util.Map;

public class SkyStruct implements ModInitializer {
    public static final Logger LOGGER = LogManager.getLogger("SkyStruct");
    public static final String MODID = "skystruct";

    public static MainConfig MainConfig = null;

    public static Identifier id(String name) {
        return new Identifier(MODID, name);
    }

    public static void addFeaturesToBiomes(MutableRegistry<Biome> biomeReg, Biome biome, Identifier biomeID, Map<String, List<String>> allBiomelists) {
        String biomeNamespace = biomeID.getNamespace();
        String biomePath = biomeID.getPath();

        for (Map.Entry<String, List<String>> entry : allBiomelists.entrySet()) {
            for (String value : entry.getValue()) {
                SkyStruct.LOGGER.info(value);
            }
        }

        if (isBiomeAllowed("tower", biomeID, allBiomelists, MainConfig.TowersConfig.biomesIsWhitelist)) {
            AddFeatures.addTowers(biomeReg, biome, biomeNamespace, biomePath);
        }
    }

    public static boolean isBiomeAllowed(String structureType, Identifier biomeID, Map<String, List<String>> allBiomeList, boolean isWhitelist) {
        if (allBiomeList.entrySet().size() == 0) {
            return true;
        }
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

        SkyStruct.LOGGER.log(Level.INFO, "Skylands Structures has been imitialized!");
    }
}
