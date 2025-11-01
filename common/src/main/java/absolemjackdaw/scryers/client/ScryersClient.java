package absolemjackdaw.scryers.client;

import absolemjackdaw.scryers.init.ScryersMenus;
import absolemjackdaw.scryers.menus.screens.CrystalBallTeleportScreen;
import com.google.auto.service.AutoService;
import dev.upcraft.sparkweave.api.client.event.RegisterMenuScreensEvent;
import dev.upcraft.sparkweave.api.entrypoint.ClientEntryPoint;
import dev.upcraft.sparkweave.api.platform.ModContainer;

@AutoService(ClientEntryPoint.class)
public class ScryersClient implements ClientEntryPoint {
    @Override
    public void onInitializeClient(ModContainer mod) {
        RegisterMenuScreensEvent.EVENT.register(event -> event.register(ScryersMenus.TELEPORT_MENU, CrystalBallTeleportScreen::new));
    }
}
