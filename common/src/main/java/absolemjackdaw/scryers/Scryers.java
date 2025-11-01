package absolemjackdaw.scryers;

import absolemjackdaw.scryers.data.ScryerTags;
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
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.AbstractSkullBlock;
import net.minecraft.world.level.block.SkullBlock;

import java.util.Optional;

@AutoService(MainEntryPoint.class)
public class Scryers implements MainEntryPoint {
    public static final String MODID = "scryers";

    public static Optional<SkullBlock.Type> getWitchType(LivingEntity entity) {
        if(hasWitchHat(entity)) {
            if(entity.getMainHandItem().getItem() instanceof BlockItem item && item.getBlock() instanceof AbstractSkullBlock block) {
                return Optional.of(block.getType());
            }
            else if(entity.getOffhandItem().getItem() instanceof BlockItem item && item.getBlock() instanceof AbstractSkullBlock block) {
                return Optional.of(block.getType());
            }
        }

        return Optional.empty();
    }

    public static boolean hasWitchHat(LivingEntity entity) {
        return entity.getItemBySlot(EquipmentSlot.HEAD).is(ScryerTags.Items.WITCH_HATS);
    }

    public static boolean hasSkull(LivingEntity entity, ItemStack stack) {
        return stack.is(ScryerTags.Items.SKULLS);
    }

    public static boolean isWitch(LivingEntity entity) {
        return hasWitchHat(entity) && entity.isHolding(itemStack -> hasSkull(entity, itemStack));
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
