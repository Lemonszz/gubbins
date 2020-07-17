package party.lemons.gubbins.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;
import java.util.stream.Collectors;

public class WarpedFruitItem extends Item
{
	public WarpedFruitItem(Settings settings)
	{
		super(settings);
	}

	public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {

		if(!world.isClient())
		{
			List<StatusEffectInstance> effects = user.getStatusEffects().stream().filter(s->!s.isAmbient()).collect(Collectors.toList());
			if(effects.size() > 0)
			{
				int ind = user.getRandom().nextInt(effects.size());
				user.removeStatusEffect(effects.get(ind).getEffectType());
			}
		}
		return super.finishUsing(stack, world, user);
	}
}
