package party.lemons.gubbins.mixin;

import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.StructureFeature;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import party.lemons.gubbins.init.GubbinsFeatures;

import java.util.ArrayList;
import java.util.List;

@Mixin(Feature.class)
public class FeatureMixin
{
	@Shadow @Final @Mutable public static List<StructureFeature<?>> JIGSAW_STRUCTURES;

	@Inject(at = @At("RETURN"), method = "<clinit>")
	private static void onClassInit(CallbackInfo cbi)
	{
		List<StructureFeature<?>> structures = new ArrayList<>(Feature.JIGSAW_STRUCTURES);
		structures.add(GubbinsFeatures.ENHANCED_DUNGEON);
		structures.add(GubbinsFeatures.CAMPSITE);

		JIGSAW_STRUCTURES = structures;
	}
}
