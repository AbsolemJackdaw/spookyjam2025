package absolemjackdaw.scryers.fabric;

import absolemjackdaw.scryers.init.ScryersItems;
import absolemjackdaw.scryers.playerdata.ScryData;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityWorldChangeEvents;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

public class ScryersFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
            if (key == EntityType.WITCH.getDefaultLootTable() && source.isBuiltin()) {
                var pool = LootPool.lootPool()
                        .add(LootItem.lootTableItem(ScryersItems.WITCH_HAT.get()))
                        .setRolls(ConstantValue.exactly(1))
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))
                        .when(LootItemRandomChanceCondition.randomChance(0.1f));
                tableBuilder.withPool(pool);
            }
        });

        ServerEntityWorldChangeEvents.AFTER_PLAYER_CHANGE_WORLD.register((player, origin, destination) -> {
            ScryData.onVisitDimension(destination.dimension(), player);
        });
    }
}
