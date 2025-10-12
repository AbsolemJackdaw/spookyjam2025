package absolemjackdaw.items;

import absolemjackdaw.mod.ScryersCommon;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ScryerItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.createItems(ScryersCommon.MODID);
    public static final Supplier<Item> ITEM_HAT = ITEMS.register(ScryersCommon.ITEM_NAME, () -> ScryersCommon.ITEM_NEW_HAT);
}
