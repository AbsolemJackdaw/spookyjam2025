package absolemjackdaw.items;

import absolemjackdaw.mod.WitchHatCommon;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;

public class WitchHatItems {
    public static Item ITEM_HAT;

    public static void register() {
        ITEM_HAT = Registry.register(BuiltInRegistries.ITEM, WitchHatCommon.ITEM_KEY, WitchHatCommon.ITEM_HAT);
    }
}
