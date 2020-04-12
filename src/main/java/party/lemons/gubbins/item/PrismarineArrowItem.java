package party.lemons.gubbins.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import party.lemons.gubbins.entity.PrismarineArrowEntity;
import party.lemons.gubbins.item.quiver.QuiverItem;

public class PrismarineArrowItem extends ArrowItem
{
	public PrismarineArrowItem(Settings settings)
	{
		super(settings);
	}


	public PersistentProjectileEntity createArrow(World world, ItemStack stack, LivingEntity shooter) {

		QuiverItem.doQuiverCheck(shooter);

		PrismarineArrowEntity arrowEntity = new PrismarineArrowEntity(world, shooter);
		arrowEntity.initFromStack(stack);

		return arrowEntity;
	}

}
