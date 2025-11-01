package absolemjackdaw.scryers.fabric.init;

import absolemjackdaw.scryers.Scryers;
import absolemjackdaw.scryers.fabric.components.ScryerComponent;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;
import org.ladysnake.cca.api.v3.entity.RespawnCopyStrategy;

public class ScryersComponents implements EntityComponentInitializer {

    public static final ComponentKey<ScryerComponent> SCRY_DATA = ComponentRegistry.getOrCreate(Scryers.id("scry_data"), ScryerComponent.class);

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(SCRY_DATA, ScryerComponent::new, RespawnCopyStrategy.CHARACTER);
    }
}
