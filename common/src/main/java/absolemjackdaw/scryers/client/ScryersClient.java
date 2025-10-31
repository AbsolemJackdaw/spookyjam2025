package absolemjackdaw.scryers.client;

import com.google.auto.service.AutoService;
import dev.upcraft.sparkweave.api.entrypoint.ClientEntryPoint;
import dev.upcraft.sparkweave.api.platform.ModContainer;

@AutoService(ClientEntryPoint.class)
public class ScryersClient implements ClientEntryPoint {
    @Override
    public void onInitializeClient(ModContainer mod) {
    }
}
