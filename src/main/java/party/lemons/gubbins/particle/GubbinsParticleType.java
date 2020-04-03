package party.lemons.gubbins.particle;

import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;

public class GubbinsParticleType<T extends ParticleEffect> extends ParticleType<T>
{
	public GubbinsParticleType(boolean shouldAlwaysShow, ParticleEffect.Factory<T> parametersFactory)
	{
		super(shouldAlwaysShow, parametersFactory);
	}
}
