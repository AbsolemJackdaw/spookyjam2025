package absolemjackdaw.mod;

import absolemjackdaw.items.WitchHatItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

public class WitchHat implements ModInitializer {
    @Override
    public void onInitialize() {
        WitchHatItems.register();
        var witchTable = EntityType.WITCH.getDefaultLootTable();

        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
            witchTable.ifPresent(table -> {
                if (table.equals(key) && source.isBuiltin()) {
                    var pool = LootPool.lootPool()
                            .add(LootItem.lootTableItem(WitchHatItems.ITEM_HAT))
                            .setRolls(ConstantValue.exactly(1))
                            .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))
                            .when(LootItemRandomChanceCondition.randomChance(0.1f));

                    tableBuilder.pool(pool.build());
                }
            });
        });
    }
}
