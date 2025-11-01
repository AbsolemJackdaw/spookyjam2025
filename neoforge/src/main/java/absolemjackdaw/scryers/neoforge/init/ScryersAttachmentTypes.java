package absolemjackdaw.scryers.neoforge.init;

import absolemjackdaw.scryers.Scryers;
import absolemjackdaw.scryers.playerdata.ScryData;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class ScryersAttachmentTypes {

    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, Scryers.MODID);

    public static final Supplier<AttachmentType<ScryData>> SCRY_PLAYER_DATA = ATTACHMENT_TYPES.register("scry_data", () -> AttachmentType.builder(ScryData::empty).serialize(ScryData.CODEC).build());
}
