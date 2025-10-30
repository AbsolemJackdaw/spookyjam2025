package absolemjackdaw.items;

import absolemjackdaw.mod.ScryersCommon;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;

public class ScryerItems {

    public static void register() {
        Registry.register(BuiltInRegistries.ITEM, ScryersCommon.ITEM_HAT_KEY, ScryersCommon.ITEM_NEW_HAT);
    }
}
