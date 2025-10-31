package absolemjackdaw.items;

import absolemjackdaw.mod.ScryersCommon;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ScryerItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ScryersCommon.MODID);
    public static final DeferredItem<HatWrapper> ITEM_HAT = ITEMS.register(ScryersCommon.ITEM_HAT_NAME, () -> ScryersCommon.ITEM_NEW_HAT);

}
