package absolemjackdaw.scryers.neoforge;

import absolemjackdaw.scryers.Scryers;
import absolemjackdaw.scryers.neoforge.loot.ScryerLootModifiers;
import absolemjackdaw.scryers.playerdata.ScryData;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

@Mod(Scryers.MODID)
public class ScryersNeoForge {
    private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, Scryers.MODID);

    public static final Supplier<AttachmentType<ScryData>> SCRY_PLAYER_DATA = ATTACHMENT_TYPES.register(
            "scry_data", () -> AttachmentType.builder(ScryData::new).build()
    );

    public ScryersNeoForge(IEventBus eventBus, ModContainer container) {
        ScryerLootModifiers.GLOBAL_LOOTMODIFIERS.register(eventBus);
        ATTACHMENT_TYPES.register(eventBus);
    }
}
