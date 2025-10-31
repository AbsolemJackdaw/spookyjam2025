package absolemjackdaw.mod;

import absolemjackdaw.blocks.ScryerBlocks;
import absolemjackdaw.items.ScryerItems;
import absolemjackdaw.loot.ScryerLootModifiers;
import absolemjackdaw.playerdata.ScryData;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

@Mod(ScryersCommon.MODID)
public class Scryers {
    private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, ScryersCommon.MODID);

    public static final Supplier<AttachmentType<ScryData>> SCRY_PLAYER_DATA = ATTACHMENT_TYPES.register(
            "scry_data", () -> AttachmentType.builder(ScryData::new).build()
    );

    public Scryers(IEventBus eventBus, ModContainer container) {
        ScryerItems.ITEMS.register(eventBus);
        ScryerLootModifiers.GLOBAL_LOOTMODIFIERS.register(eventBus);
        ScryerBlocks.BLOCKS.register(eventBus);
        ATTACHMENT_TYPES.register(eventBus);
    }
}
