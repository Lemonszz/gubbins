package party.lemons.gubbins.particle;

import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.particle.SpriteBillboardParticle;
import net.minecraft.world.World;
import party.lemons.gubbins.particle.DyedSmokeEffect;

public class DyedSmokeParticle extends SpriteBillboardParticle
{
	public DyedSmokeParticle(World world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, DyedSmokeEffect effect)
	{
		super(world, x, y, z);
		this.scale(3.0F);
		setColorAlpha(0.9F);
		this.setBoundingBoxSpacing(0.25F, 0.25F);
		if (effect.isSignal()) {
			this.maxAge = this.random.nextInt(50) + 280;
		} else {
			this.maxAge = this.random.nextInt(50) + 80;
		}

		this.gravityStrength = 3.0E-6F;
		this.velocityX = velocityX;
		this.velocityY = velocityY + (double)(this.random.nextFloat() / 500.0F);
		this.velocityZ = velocityZ;

		this.colorRed = ((float)(Math.random() * 0.20000000298023224D) + 0.8F) * effect.getRed() * 1;
		this.colorGreen = ((float)(Math.random() * 0.20000000298023224D) + 0.8F) * effect.getGreen() * 1;
		this.colorBlue = ((float)(Math.random() * 0.20000000298023224D) + 0.8F) * effect.getBlue() * 1;
	}
	public void tick() {
		this.prevPosX = this.x;
		this.prevPosY = this.y;
		this.prevPosZ = this.z;
		if (this.age++ < this.maxAge && this.colorAlpha > 0.0F) {
			this.velocityX += this.random.nextFloat() / 5000.0F * (float)(this.random.nextBoolean() ? 1 : -1);
			this.velocityZ += this.random.nextFloat() / 5000.0F * (float)(this.random.nextBoolean() ? 1 : -1);
			this.velocityY -= this.gravityStrength;
			this.move(this.velocityX, this.velocityY, this.velocityZ);
			if (this.age >= this.maxAge - 60 && this.colorAlpha > 0.01F) {
				this.colorAlpha -= 0.015F;
			}

		} else {
			this.markDead();
		}
	}


	@Override
	public ParticleTextureSheet getType()
	{
		return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
	}
}
