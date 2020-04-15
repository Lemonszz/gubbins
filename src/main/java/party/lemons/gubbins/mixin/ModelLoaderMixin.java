package party.lemons.gubbins.mixin;

import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.state.StateManager;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import party.lemons.gubbins.init.SpecialModels;

import java.util.Arrays;
import java.util.Map;

@Mixin(ModelLoader.class)
public abstract class ModelLoaderMixin
{
	@Shadow @Final @Mutable private static Map<Identifier, StateManager<Block, BlockState>> STATIC_DEFINITIONS;

	@Inject(at = @At("RETURN"), method = "<clinit>")
	private static void onStaticConstruct(CallbackInfo cbi)
	{
		Map<Identifier, StateManager<Block, BlockState>> newMap = Maps.newHashMap();
		STATIC_DEFINITIONS.forEach(newMap::put);
		Arrays.stream(SpecialModels.MODELS).forEach(sp->{
			newMap.put(sp.ID, sp.stateStateManager);
		});

		STATIC_DEFINITIONS = newMap;
	}
}
