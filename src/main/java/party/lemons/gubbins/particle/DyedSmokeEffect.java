package party.lemons.gubbins.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.util.registry.Registry;
import party.lemons.gubbins.init.GubbinsParticles;

import java.util.Locale;

public class DyedSmokeEffect implements ParticleEffect
{
	private final float red;
	private final float green;
	private final float blue;
	private final boolean isSignal;

	public DyedSmokeEffect(float red, float green, float blue, boolean isSignal) {
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.isSignal = isSignal;
	}

	public void write(PacketByteBuf buf) {
		buf.writeFloat(this.red);
		buf.writeFloat(this.green);
		buf.writeFloat(this.blue);
		buf.writeBoolean(this.isSignal);
	}

	public String asString() {
		return String.format(Locale.ROOT, "%s %.2f %.2f %.2f", Registry.PARTICLE_TYPE.getId(this.getType()), this.red, this.green, this.blue);
	}

	public ParticleType<DyedSmokeEffect> getType() {
		return GubbinsParticles.DYED_SIGNAL_SMOKE;
	}

	@Environment(EnvType.CLIENT)
	public float getRed() {
		return this.red;
	}

	@Environment(EnvType.CLIENT)
	public float getGreen() {
		return this.green;
	}

	@Environment(EnvType.CLIENT)
	public float getBlue() {
		return this.blue;
	}

	public boolean isSignal()
	{
		return isSignal;
	}
}
