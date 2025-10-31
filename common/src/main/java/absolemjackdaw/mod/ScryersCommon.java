package absolemjackdaw.mod;

import absolemjackdaw.items.HatWrapper;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.AbstractSkullBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SkullBlock;

public class ScryersCommon {
    public static final String MODID = "scryers";
    public static final String ENTITY_MINION = "witch_minion";
    public static final String ITEM_HAT_NAME = "witchhat";
    public static final String CRYSTAL_BALL_NAME = "crystal_ball";
    public static final ResourceKey<Item> ITEM_HAT_KEY = ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MODID, ITEM_HAT_NAME));
    public static final ResourceKey<Item> ITEM_BALL_KEY = ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MODID, CRYSTAL_BALL_NAME));

    public static final ResourceKey<EntityType<?>> ENTITY_MINION_KEY = ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(MODID, "minion"));
    public static final ResourceKey<Block> BLOCK_KEY = ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(MODID, CRYSTAL_BALL_NAME));

    public static final HatWrapper ITEM_NEW_HAT = new HatWrapper(new Item.Properties().stacksTo(1).durability(500));

    public static boolean isWitch(Player player) {
        return player.getOffhandItem().getItem() instanceof BlockItem item && item.getBlock() instanceof AbstractSkullBlock && player.getItemBySlot(EquipmentSlot.HEAD).is(ITEM_NEW_HAT);
    }

    public static SkullBlock.Type getWitchType(Player p) {
        if (isWitch(p) && p.getOffhandItem().getItem() instanceof BlockItem item && item.getBlock() instanceof AbstractSkullBlock block)
            return block.getType();
        return () -> "empty";
    }

    public static boolean isScryer(Player player) {
        return player.getOffhandItem().getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof AbstractSkullBlock && player.getItemBySlot(EquipmentSlot.HEAD).is(ITEM_NEW_HAT);
    }
}
