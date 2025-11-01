package absolemjackdaw.scryers.init;

import absolemjackdaw.scryers.Scryers;
import absolemjackdaw.scryers.menus.CrystalBallTeleportMenu;
import absolemjackdaw.scryers.service.ScryersPlatformService;
import dev.upcraft.sparkweave.api.registry.RegistryHandler;
import dev.upcraft.sparkweave.api.registry.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;

public class ScryersMenus {

    public static final RegistryHandler<MenuType<?>> MENUS = RegistryHandler.create(Registries.MENU, Scryers.MODID);

    public static final RegistrySupplier<MenuType<CrystalBallTeleportMenu>> TELEPORT_MENU = MENUS.register("teleport_menu", ScryersPlatformService.SERVICE::createTeleportMenuType);
}
