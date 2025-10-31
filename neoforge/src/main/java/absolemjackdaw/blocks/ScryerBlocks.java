package absolemjackdaw.blocks;

import absolemjackdaw.items.ScryerItems;
import absolemjackdaw.mod.ScryersCommon;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;

public class ScryerBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ScryersCommon.MODID);
    public static final DeferredBlock<BlockCrystalBall> CRYSTAL_BALL = blockWithItem(ScryersCommon.CRYSTAL_BALL_NAME, BlockCrystalBall::new, BlockBehaviour.Properties.of().noOcclusion().strength(1.0f).sound(SoundType.AMETHYST), new Item.Properties());

    private static <T extends Block> DeferredBlock<T> blockWithItem(String name, Function<BlockBehaviour.Properties, T> factory) {
        return blockWithItem(name, factory, BlockBehaviour.Properties.of(), new Item.Properties());
    }

    private static <T extends Block> DeferredBlock<T> blockWithItem(String name, Function<BlockBehaviour.Properties, T> factory, BlockBehaviour.Properties blockProperties, Item.Properties itemProperties) {
        var block = BLOCKS.registerBlock(name, factory, blockProperties);
        ScryerItems.ITEMS.register(name, () -> new BlockItem(block.get(), itemProperties));
        return block;
    }
}
