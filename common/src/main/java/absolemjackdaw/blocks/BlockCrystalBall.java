package absolemjackdaw.blocks;

import absolemjackdaw.mod.ScryersCommon;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

public class BlockCrystalBall extends Block {

    public BlockCrystalBall() {
        super(Properties.of().noOcclusion().strength(1.0f).sound(SoundType.AMETHYST));
    }

    @Override
    public boolean isEnabled(FeatureFlagSet enabledFeatures) {
        return super.isEnabled(enabledFeatures);
    }
}
