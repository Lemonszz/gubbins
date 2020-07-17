package party.lemons.gubbins.gen;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.feature.FeatureConfig;

import java.util.function.Predicate;

public class GubbinsOreFeatureConfig implements FeatureConfig
{
	public final Predicate<BlockState> target;
	public  int size;
	public final BlockState state;

	public GubbinsOreFeatureConfig(Predicate<BlockState> target, BlockState state, int size) {
		this.size = size;
		this.state = state;
		this.target = target;
	}
	public static final Codec<GubbinsOreFeatureConfig> codec = Codec.unit(()->new GubbinsOreFeatureConfig(b->false, Blocks.AIR.getDefaultState(), 0));
}