package party.lemons.gubbins.item.quiver;

import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import party.lemons.gubbins.Gubbins;
import party.lemons.gubbins.init.GubbinsItems;

public class QuiverItem extends Item
{
	public QuiverItem()
	{
		super(GubbinsItems.settings().maxCount(1));
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand)
	{
		if(!world.isClient())
		{
			ItemStack stack = user.getStackInHand(hand);
			int slot = user.inventory.getSlotWithStack(stack);
			ContainerProviderRegistry.INSTANCE.openContainer(Gubbins.QUIVER, user, buf->buf.writeInt(slot));
		}

		return super.use(world, user, hand);
	}

	public static void doQuiverCheck(LivingEntity shooter)
	{
		if(shooter instanceof PlayerEntity)
		{
			PlayerEntity playerEntity = (PlayerEntity) shooter;
			for(int i = 0; i < playerEntity.inventory.size(); ++i)
			{
				ItemStack checkStack = playerEntity.inventory.getStack(i);
				if(!checkStack.isEmpty() && checkStack.getItem() == GubbinsItems.QUIVER)
				{
					CompoundTag tags = checkStack.getTag();
					if(tags == null) continue;

					ItemStack arrowStack = ItemStack.EMPTY;
					String t = "";
					if(tags.contains("1"))
					{
						ItemStack st = ItemStack.fromTag(tags.getCompound("1"));
						if(st.isItemEqual(st))
						{
							arrowStack = st;
							t = "1";
						}
					}else if(tags.contains("2"))
					{
						ItemStack st = ItemStack.fromTag(tags.getCompound("1"));
						if(st.isItemEqual(st))
						{
							arrowStack = ItemStack.fromTag(tags.getCompound("2"));
							t = "2";
						}
					}else if(tags.contains("3"))
					{
						ItemStack st = ItemStack.fromTag(tags.getCompound("1"));
						if(st.isItemEqual(st))
						{
							arrowStack = ItemStack.fromTag(tags.getCompound("3"));
							t = "3";
						}
					}

					if(!arrowStack.isEmpty())
					{
						ItemStack returnStack = arrowStack.copy();
						returnStack.decrement(1);
						tags.put(t, returnStack.toTag(new CompoundTag()));
						return;
					}

				}
			}
		}
	}

}
