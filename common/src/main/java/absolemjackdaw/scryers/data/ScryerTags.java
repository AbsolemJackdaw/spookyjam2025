package absolemjackdaw.scryers.data;

import absolemjackdaw.scryers.Scryers;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ScryerTags {
    public static class Items {
        public static final TagKey<Item> SKULLS = TagKey.create(Registries.ITEM, Scryers.id("skulls"));
        public static final TagKey<Item> WITCH_HATS = TagKey.create(Registries.ITEM, Scryers.id("witch_hats"));
    }
}
