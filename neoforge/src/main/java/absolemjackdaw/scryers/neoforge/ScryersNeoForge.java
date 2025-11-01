package absolemjackdaw.scryers.neoforge;

import absolemjackdaw.scryers.Scryers;
import absolemjackdaw.scryers.neoforge.init.ScryersAttachmentTypes;
import absolemjackdaw.scryers.neoforge.loot.ScryerLootModifiers;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(Scryers.MODID)
public class ScryersNeoForge {

    public ScryersNeoForge(IEventBus eventBus) {
        ScryerLootModifiers.GLOBAL_LOOTMODIFIERS.register(eventBus);
        ScryersAttachmentTypes.ATTACHMENT_TYPES.register(eventBus);
    }
}
