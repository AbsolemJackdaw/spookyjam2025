package absolemjackdaw.mod;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;

public class WitchHatCommon {
    public static final String MODID = "witchhats";
    public static final String ENTITY_MINION = "witch_minion";
    public static final String ITEM_NAME = "witchhat";
    public static final ResourceKey<Item> ITEM_KEY = ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MODID, ITEM_NAME));
    public static final ResourceKey<EntityType<?>> ENTITY_MINION_KEY = ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(MODID, "minion"));

    public static final Item ITEM_HAT = new Item(new Item.Properties().setId(WitchHatCommon.ITEM_KEY).stacksTo(1).equippable(EquipmentSlot.HEAD).durability(500));
}
