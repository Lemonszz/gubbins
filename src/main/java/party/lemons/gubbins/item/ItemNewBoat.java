package party.lemons.gubbins.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RayTraceContext;
import net.minecraft.world.World;
import party.lemons.gubbins.boat.BoatType;
import party.lemons.gubbins.entity.NewBoatEntity;

import java.util.List;

public class ItemNewBoat extends Item
{
	private final BoatType type;

	public ItemNewBoat(BoatType type, Item.Settings settings)
	{
		super(settings);

		this.type = type;
	}

	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack itemStack = user.getStackInHand(hand);
		HitResult hitResult = rayTrace(world, user, RayTraceContext.FluidHandling.ANY);
		if (hitResult.getType() == HitResult.Type.MISS) {
			return TypedActionResult.pass(itemStack);
		} else {
			Vec3d vec3d = user.getRotationVec(1.0F);
			List<Entity> list = world.getEntities((Entity)user, user.getBoundingBox().stretch(vec3d.multiply(5.0D)).expand(1.0D), EntityPredicates.EXCEPT_SPECTATOR.and(Entity::collides));
			if (!list.isEmpty()) {
				Vec3d cameraPosVec = user.getCameraPosVec(1.0F);

				for(Entity entity : list)
				{
					Box box = entity.getBoundingBox().expand((double) entity.getTargetingMargin());
					if(box.contains(cameraPosVec))
					{
						return TypedActionResult.pass(itemStack);
					}
				}
			}

			if (hitResult.getType() == HitResult.Type.BLOCK) {
				NewBoatEntity boatEntity = createBoat(world, hitResult.getPos().x, hitResult.getPos().y, hitResult.getPos().z);
				boatEntity.yaw = user.yaw;
				if (!world.doesNotCollide(boatEntity, boatEntity.getBoundingBox().expand(-0.1D))) {
					return TypedActionResult.fail(itemStack);
				} else {
					if (!world.isClient) {
						world.spawnEntity(boatEntity);
						if (!user.abilities.creativeMode) {
							itemStack.decrement(1);
						}
					}

					user.incrementStat(Stats.USED.getOrCreateStat(this));
					return TypedActionResult.success(itemStack);
				}
			} else {
				return TypedActionResult.pass(itemStack);
			}
		}
	}

	public NewBoatEntity createBoat(World world, double x, double y, double z)
	{
		NewBoatEntity boatEntity = new NewBoatEntity(world, x, y, z);
		boatEntity.setBoatType(type);
		world.spawnEntity(boatEntity);

		return boatEntity;
	}
}
