package absolemjackdaw.scryers.neoforge;

import absolemjackdaw.scryers.Scryers;
import absolemjackdaw.scryers.neoforge.init.ScryersAttachmentTypes;
import absolemjackdaw.scryers.neoforge.loot.ScryerLootModifiers;
import absolemjackdaw.scryers.playerdata.ScryData;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

@EventBusSubscriber
@Mod(Scryers.MODID)
public class ScryersNeoForge {

    public ScryersNeoForge(IEventBus eventBus) {
        ScryerLootModifiers.GLOBAL_LOOTMODIFIERS.register(eventBus);
        ScryersAttachmentTypes.ATTACHMENT_TYPES.register(eventBus);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onTeleport(PlayerEvent.PlayerChangedDimensionEvent event) {
        if(event.getEntity() instanceof ServerPlayer player) {
            ScryData.onVisitDimension(event.getTo(), player);
        }
    }
}
