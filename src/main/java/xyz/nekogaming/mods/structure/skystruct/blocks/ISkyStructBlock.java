package xyz.nekogaming.mods.structure.skystruct.blocks;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface ISkyStructBlock {
    @OnlyIn(Dist.CLIENT)
    default void clientInit() {
    }

    //default void init(PacketHandler packetHandler) {}
}
