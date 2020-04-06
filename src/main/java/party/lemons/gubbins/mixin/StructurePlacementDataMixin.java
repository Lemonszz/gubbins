package party.lemons.gubbins.mixin;

import net.minecraft.structure.StructurePlacementData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import party.lemons.gubbins.util.accessor.StructurePlacementDataAccessor;

@Mixin(StructurePlacementData.class)
public class StructurePlacementDataMixin implements StructurePlacementDataAccessor
{
	@Shadow private boolean placeFluids;

	@Override
	public void setPlaceFluid(boolean placeFluid)
	{
		this.placeFluids = placeFluid;
	}
}
