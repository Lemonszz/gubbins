package party.lemons.gubbins.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FireworkRocketEntity.class)
public class FireworkRocketEntityMixin
{
	@Shadow private LivingEntity shooter;

	@Inject(at=@At("TAIL"), method = "tick()V")
	public void tick(CallbackInfo cbi)
	{
		/*if (this.shooter != null)
		{
			if(this.shooter.hasVehicle())
			{
				Vec3d vec3d = this.shooter.getRotationVector();
				double d = 1.5D;
				double e = 0.1D;
				Vec3d velocity = this.shooter.getVehicle().getVelocity();
				this.shooter.getVehicle().setVelocity(velocity.add(vec3d.x * 0.1D + (vec3d.x * 1.5D - velocity.x) * 0.5D, vec3d.y * 0.1D + (vec3d.y * 1.5D - velocity.y) * 0.5D, vec3d.z * 0.1D + (vec3d.z * 1.5D - velocity.z) * 0.5D));
			}
		}*/
	}
}
