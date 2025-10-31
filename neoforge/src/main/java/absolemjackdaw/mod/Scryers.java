package absolemjackdaw.mod;

import absolemjackdaw.blocks.ScryerBlocks;
import absolemjackdaw.items.ScryerItems;
import absolemjackdaw.loot.ScryerLootModifiers;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@Mod(ScryersCommon.MODID)
public class Scryers {
    public Scryers(IEventBus eventBus, ModContainer container) {
        ScryerItems.ITEMS.register(eventBus);
        ScryerLootModifiers.GLOBAL_LOOTMODIFIERS.register(eventBus);
        ScryerBlocks.BLOCKS.register(eventBus);
    }
}
