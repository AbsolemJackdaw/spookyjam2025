package absolemjackdaw.scryers.fabric.client;

import absolemjackdaw.scryers.Scryers;
import absolemjackdaw.scryers.fabric.client.model.HatModelWrapper;
import absolemjackdaw.scryers.init.ScryersItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.minecraft.client.resources.model.ModelResourceLocation;

public class ScryersFabricClient implements ClientModInitializer {

    private static final ModelResourceLocation WITCHHAT_HEAD_MODEL = new ModelResourceLocation(Scryers.id("witch_hat_head"), "head");
    private static final ModelResourceLocation WITCHHAT_INVENTORY_MODEL = ModelResourceLocation.inventory(ScryersItems.WITCH_HAT.getId());

    @Override
    public void onInitializeClient() {
        ModelLoadingPlugin.register(pluginCtx -> {
            pluginCtx.addModels(WITCHHAT_HEAD_MODEL.id());

            pluginCtx.modifyModelAfterBake().register((bakedModel, ctx) -> {
                if(WITCHHAT_INVENTORY_MODEL.equals(ctx.topLevelId())) {
                    var headModel = ctx.baker().bake(WITCHHAT_HEAD_MODEL.id(), ctx.settings());
                    return new HatModelWrapper(bakedModel, headModel);
                }

                return bakedModel;
            });
        });
    }
}
