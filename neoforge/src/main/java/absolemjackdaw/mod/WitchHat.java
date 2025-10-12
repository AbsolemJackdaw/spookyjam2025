package absolemjackdaw.mod;

import absolemjackdaw.items.WitchHatItems;
import absolemjackdaw.loot.WitchHatLootModifiers;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@Mod(WitchHatCommon.MODID)
public class WitchHat {
    public WitchHat(IEventBus eventBus, ModContainer container) {
        WitchHatItems.ITEMS.register(eventBus);
        WitchHatLootModifiers.GLOBAL_LOOTMODIFIERS.register(eventBus);
    }
}
