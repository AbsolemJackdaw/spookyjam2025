package absolemjackdaw.items;

import absolemjackdaw.mod.WitchHatCommon;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class WitchHatItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.createItems(WitchHatCommon.MODID);
    public static final Supplier<Item> ITEM_HAT = ITEMS.register(WitchHatCommon.ITEM_NAME, () -> WitchHatCommon.ITEM_HAT);
}
