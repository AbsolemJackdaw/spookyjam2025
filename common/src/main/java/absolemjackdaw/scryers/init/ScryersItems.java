package absolemjackdaw.scryers.init;

import absolemjackdaw.scryers.Scryers;
import absolemjackdaw.scryers.items.WitchHatItem;
import dev.upcraft.sparkweave.api.item.CreativeTabHelper;
import dev.upcraft.sparkweave.api.registry.RegistryHandler;
import dev.upcraft.sparkweave.api.registry.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

public class ScryersItems {

    public static final RegistryHandler<Item> ITEMS = RegistryHandler.create(Registries.ITEM, Scryers.MODID);
    public static final RegistryHandler<CreativeModeTab> CREATIVE_TABS = RegistryHandler.create(Registries.CREATIVE_MODE_TAB, Scryers.MODID);

    public static final ResourceKey<CreativeModeTab> CREATIVE_MODE_TAB_RESOURCE_KEY = ResourceKey.create(Registries.CREATIVE_MODE_TAB, Scryers.id("items"));

    public static final RegistrySupplier<WitchHatItem> WITCH_HAT = ITEMS.register("witch_hat", () -> new WitchHatItem(new Item.Properties().durability(500)));

    public static final RegistrySupplier<CreativeModeTab> MOD_CREATIVE_TAB = CREATIVE_TABS.register("items", () -> CreativeTabHelper.newBuilder(CREATIVE_MODE_TAB_RESOURCE_KEY).icon(() -> ScryersItems.WITCH_HAT.get().getDefaultInstance()).displayItems((itemDisplayParameters, output) -> {
        CreativeTabHelper.addRegistryEntries(itemDisplayParameters, output, ScryersItems.ITEMS);
        CreativeTabHelper.addRegistryEntries(itemDisplayParameters, output, ScryersBlocks.BLOCKS);
    }).build());
}
