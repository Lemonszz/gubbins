package party.lemons.gubbins.util;

import io.netty.buffer.Unpooled;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import party.lemons.gubbins.init.GubbinsItems;

public class EntityUtil
{
	public static PacketByteBuf WriteEntitySpawn(Entity entity){
		PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
		buf.writeVarInt(Registry.ENTITY_TYPE.getRawId(entity.getType()));
		buf.writeUuid(entity.getUuid());
		buf.writeVarInt(entity.getEntityId());
		buf.writeDouble(entity.getX());
		buf.writeDouble(entity.getY());
		buf.writeDouble(entity.getZ());
		buf.writeByte(MathHelper.floor(entity.pitch * 256.0F / 360.0F));
		buf.writeByte(MathHelper.floor(entity.yaw * 256.0F / 360.0F));
		buf.writeFloat(entity.pitch);
		buf.writeFloat(entity.yaw);

		return buf;
	}

	public static boolean isUsingTelescope(PlayerEntity playerEntity)
	{
		ItemStack activeStack = playerEntity.getActiveItem();
		return !activeStack.isEmpty() && activeStack.getItem() == GubbinsItems.TELESCOPE;
	}

	public static int getSlotWithItemStack(PlayerEntity playerEntity, ItemStack stack)
	{
		DefaultedList<ItemStack> inv = playerEntity.inventory.main;
		for(int i = 0; i < inv.size(); ++i) {
			if (!inv.get(i).isEmpty() && areItemsEqual(stack, inv.get(i))) {
				return i;
			}
		}
		return -1;
	}

	public static boolean areItemsEqual(ItemStack stack1, ItemStack stack2)
	{
		return stack1.getItem() == stack2.getItem() && ItemStack.areTagsEqual(stack1, stack2);
	}
}
