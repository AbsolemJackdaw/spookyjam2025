package absolemjackdaw.items;

import absolemjackdaw.blocks.ScryerBlocks;
import absolemjackdaw.mod.ScryersCommon;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ScryerItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.createItems(ScryersCommon.MODID);
    public static final Supplier<Item> ITEM_HAT = ITEMS.register(ScryersCommon.ITEM_HAT_NAME, () -> ScryersCommon.ITEM_NEW_HAT);
//    public static final Supplier<BlockItem> CRYSTAL_BALL = ITEMS.register(ScryersCommon.CRYSTAL_BALL_NAME, () -> new BlockItem(ScryerBlocks.CRYSTAL_BALL.value(), new Item.Properties()));

}
