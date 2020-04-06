package party.lemons.gubbins.init;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.fabricmc.fabric.impl.client.particle.ParticleFactoryRegistryImpl;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.*;
import party.lemons.gubbins.particle.DyedSmokeEffect;
import party.lemons.gubbins.particle.DyedSmokeParticle;
import party.lemons.gubbins.particle.GubbinsParticleType;
import party.lemons.gubbins.util.registry.AutoReg;

@AutoReg(type = ParticleType.class, registry = "particle_type")
public class GubbinsParticles
{

	public static void clientInit()
	{
		ParticleFactoryRegistryImpl.INSTANCE.register(DYED_SIGNAL_SMOKE, provider->(parameters, world, x, y, z, velocityX, velocityY, velocityZ)->
		{
			DyedSmokeParticle particle = new DyedSmokeParticle(world, x, y, z, velocityX, velocityY, velocityZ, parameters);
			particle.setSprite(provider);
			return particle;
		});
	}

	public static final ParticleEffect.Factory<DyedSmokeEffect> DYED_SMOKE_PARAMS = new ParticleEffect.Factory<DyedSmokeEffect>() {
		public DyedSmokeEffect read(ParticleType<DyedSmokeEffect> particleType, StringReader stringReader) throws CommandSyntaxException
		{
			stringReader.expect(' ');
			float r = (float)stringReader.readDouble();
			stringReader.expect(' ');
			float g = (float)stringReader.readDouble();
			stringReader.expect(' ');
			float b = (float)stringReader.readDouble();
			stringReader.expect(' ');
			boolean signal = stringReader.readBoolean();
			return new DyedSmokeEffect(r, g, b, signal);
		}

		public DyedSmokeEffect read(ParticleType<DyedSmokeEffect> particleType, PacketByteBuf packetByteBuf) {
			return new DyedSmokeEffect(packetByteBuf.readFloat(), packetByteBuf.readFloat(), packetByteBuf.readFloat(), packetByteBuf.readBoolean());
		}
	};

	public static final ParticleType<DyedSmokeEffect> DYED_SIGNAL_SMOKE = new GubbinsParticleType<>(true, DYED_SMOKE_PARAMS);
}
