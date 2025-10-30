package absolemjackdaw.mod;

import absolemjackdaw.items.ScryerItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

public class Scryers implements ModInitializer {
    @Override
    public void onInitialize() {
        ScryerItems.register();
        var witchTable = EntityType.WITCH.getDefaultLootTable();

        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
            var itemReference = BuiltInRegistries.ITEM.get(ScryersCommon.ITEM_HAT_KEY).asItem();
            if (witchTable.equals(key) && source.isBuiltin()) {
                var pool = LootPool.lootPool()
                        .add(LootItem.lootTableItem(itemReference))
                        .setRolls(ConstantValue.exactly(1))
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))
                        .when(LootItemRandomChanceCondition.randomChance(0.1f));
                tableBuilder.pool(pool.build());
            }
        });
    }
}
