package absolemjackdaw.scryers;

import absolemjackdaw.scryers.init.ScryersBlocks;
import absolemjackdaw.scryers.init.ScryersItems;
import absolemjackdaw.scryers.init.ScryersMenus;
import absolemjackdaw.scryers.network.c2s.SelectTeleportTargetPacket;
import com.google.auto.service.AutoService;
import commonnetwork.api.Network;
import dev.upcraft.sparkweave.api.entrypoint.MainEntryPoint;
import dev.upcraft.sparkweave.api.platform.ModContainer;
import dev.upcraft.sparkweave.api.platform.services.RegistryService;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.AbstractSkullBlock;
import net.minecraft.world.level.block.SkullBlock;

@AutoService(MainEntryPoint.class)
public class Scryers implements MainEntryPoint {
    public static final String MODID = "scryers";

    public static SkullBlock.Type getWitchType(Player p) {
        if (isWitch(p) && p.getOffhandItem().getItem() instanceof BlockItem item && item.getBlock() instanceof AbstractSkullBlock block)
            return block.getType();
        return () -> "empty";
    }

    public static boolean isWitch(Player player) {
        return player.isHolding(itemStack -> itemStack.getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof AbstractSkullBlock) && player.getItemBySlot(EquipmentSlot.HEAD).is(ScryersItems.WITCH_HAT.get());
    }

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }

    @Override
    public void onInitialize(ModContainer mod) {
        var registryService = RegistryService.get();
        ScryersBlocks.BLOCKS.accept(registryService);
        ScryersItems.ITEMS.accept(registryService);
        ScryersItems.CREATIVE_TABS.accept(registryService);
        ScryersMenus.MENUS.accept(registryService);

        Network.registerPacket(SelectTeleportTargetPacket.TYPE, SelectTeleportTargetPacket.class, SelectTeleportTargetPacket.STREAM_CODEC, SelectTeleportTargetPacket::handle);
    }

    public static void sendErrorMessage(ServerPlayer player, MutableComponent message) {
        player.sendSystemMessage(message.withStyle(ChatFormatting.RED), true);
    }
}
