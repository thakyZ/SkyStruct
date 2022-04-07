package xyz.nekogaming.mods.structure.skystruct.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

import java.util.function.Supplier;

public abstract class AbstractEntityBlock<E extends TileEntity> extends SkyStructBlock {
    protected final Supplier<TileEntityType<? extends E>> tileEntityType;

    protected AbstractEntityBlock(AbstractBlock.Properties builder, Supplier<TileEntityType<? extends E>> tileEntityTypeSupplier) {
        super(builder);
        this.tileEntityType = tileEntityTypeSupplier;
    }
}
