package absolemjackdaw.mod;

import absolemjackdaw.items.HatWrapper;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.AbstractSkullBlock;
import net.minecraft.world.level.block.SkullBlock;

public class ScryersCommon {
    public static final String MODID = "scryers";
    public static final String ENTITY_MINION = "witch_minion";
    public static final String ITEM_NAME = "witchhat";
    public static final ResourceKey<Item> ITEM_KEY = ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MODID, ITEM_NAME));
    public static final ResourceKey<EntityType<?>> ENTITY_MINION_KEY = ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(MODID, "minion"));

    public static final HatWrapper ITEM_NEW_HAT = new HatWrapper(new Item.Properties().setId(ScryersCommon.ITEM_KEY).stacksTo(1).equippable(EquipmentSlot.HEAD).durability(500));

    public static boolean isWitch(Player player) {
        return player.getOffhandItem().getItem() instanceof BlockItem item && item.getBlock() instanceof AbstractSkullBlock && player.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof HatWrapper;
    }

    public static SkullBlock.Type getWitchType(Player p) {
        if (isWitch(p) && p.getOffhandItem().getItem() instanceof BlockItem item && item.getBlock() instanceof AbstractSkullBlock block)
            return block.getType();
        return () -> "empty";
    }
}
