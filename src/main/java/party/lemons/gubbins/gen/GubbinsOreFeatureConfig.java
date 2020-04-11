package party.lemons.gubbins.gen;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.feature.FeatureConfig;

import java.util.function.Predicate;

public class GubbinsOreFeatureConfig implements FeatureConfig
{
	public final Predicate<BlockState> target;
	public final int size;
	public final BlockState state;

	public GubbinsOreFeatureConfig(Predicate<BlockState> target, BlockState state, int size) {
		this.size = size;
		this.state = state;
		this.target = target;
	}

	public <T> Dynamic<T> serialize(DynamicOps<T> ops) {
		return new Dynamic(ops, ops.createMap(ImmutableMap.of(ops.createString("size"), ops.createInt(this.size), ops.createString("target"), ops.createString(this.target.toString()), ops.createString("state"), BlockState.serialize(ops, this.state).getValue())));
	}

	public static GubbinsOreFeatureConfig deserialize(Dynamic<?> dynamic) {
		int i = dynamic.get("size").asInt(0);
		BlockState blockState = dynamic.get("state").map(BlockState::deserialize).orElse(Blocks.AIR.getDefaultState());
		return new GubbinsOreFeatureConfig(b->b.getBlock() == Blocks.STONE, blockState, i);
	}
}