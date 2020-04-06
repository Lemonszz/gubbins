package party.lemons.gubbins.init;

import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.carver.Carver;
import party.lemons.gubbins.gen.carver.IceCarver;
import party.lemons.gubbins.gen.carver.IceRavineCarver;
import party.lemons.gubbins.util.registry.AutoReg;

@AutoReg(type = Carver.class, registry = "carver")
public class GubbinsCarvers
{
	public static final IceRavineCarver ICE_RAVINE = new IceRavineCarver(ProbabilityConfig::deserialize);
	public static final IceCarver ICE_CAVE = new IceCarver(ProbabilityConfig::deserialize, 256);
}
