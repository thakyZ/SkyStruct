package xyz.nekogaming.mods.structure.skystruct.config;

import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;
import me.sargunvohra.mcmods.autoconfig1u.annotation.ConfigEntry;
import xyz.nekogaming.mods.structure.skystruct.SkyStruct;

@Config(name = SkyStruct.MODID)
public class MainConfig implements ConfigData {
    @ConfigEntry.Category("Towers")
    @ConfigEntry.Gui.TransitiveObject
    public final TowersConfig TowersConfig = new TowersConfig();
}
