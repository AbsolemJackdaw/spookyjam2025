package absolemjackdaw.items;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Equipable;
import net.minecraft.world.item.Item;

public class HatWrapper extends Item implements Equipable {
    public HatWrapper(Properties properties) {
        super(properties);
    }


    @Override
    public EquipmentSlot getEquipmentSlot() {
        return EquipmentSlot.HEAD;
    }
}
