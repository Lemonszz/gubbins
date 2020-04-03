package party.lemons.gubbins.item.quiver;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.BasicInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.tag.ItemTags;

public class QuiverScreenHandler extends ScreenHandler
{
	private Inventory inventory;
	private PlayerInventory playerInventory;
	private ItemStack stack;

	public QuiverScreenHandler(int syncId, PlayerEntity playerEntity, ItemStack stack)
	{
		super(null, syncId);

		this.stack = stack;

		this.inventory = new BasicInventory(3);

		CompoundTag tags = stack.getOrCreateTag();
		if(tags.contains("1"))
			inventory.setStack(0, ItemStack.fromTag(tags.getCompound("1")));
		if(tags.contains("2"))
			inventory.setStack(1, ItemStack.fromTag(tags.getCompound("2")));
		if(tags.contains("3"))
			inventory.setStack(2, ItemStack.fromTag(tags.getCompound("3")));

		this.playerInventory = playerEntity.inventory;

		playerInventory.onOpen(playerEntity);

		for(int i = 0; i < 3; i++)
		{
			addSlot(new QuiverSlot(inventory, i, 62 + (i * 18), 20));
		}

		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 9; x++) {
				this.addSlot(new Slot(playerInventory, y * 9 + x + 9, 8 + x * 18, y * 18 + 51));
			}
		}
		for (int j = 0; j < 9; j++) {
			this.addSlot(new Slot(playerInventory, j, 8 + j * 18, 109));
		}

	}

	public void sendContentUpdates()
	{
		super.sendContentUpdates();

		CompoundTag tags = stack.getOrCreateTag();
		tags.remove("1");
		tags.remove("2");
		tags.remove("3");

		if(!inventory.getStack(0).isEmpty())
			tags.put("1", inventory.getStack(0).toTag(new CompoundTag()));
		if(!inventory.getStack(1).isEmpty())
			tags.put("2", inventory.getStack(1).toTag(new CompoundTag()));
		if(!inventory.getStack(2).isEmpty())
			tags.put("3", inventory.getStack(2).toTag(new CompoundTag()));

		stack.setTag(tags);
	}

	@Override
	public boolean canUse(PlayerEntity player)
	{
		return true;
	}

	public ItemStack transferSlot(PlayerEntity player, int invSlot) {
		ItemStack newStack = ItemStack.EMPTY;
		Slot slot = this.slots.get(invSlot);
		if (slot != null && slot.hasStack()) {
			ItemStack originalStack = slot.getStack();
			newStack = originalStack.copy();
			if (invSlot < this.inventory.size()) {
				if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
				return ItemStack.EMPTY;
			}

			if (originalStack.isEmpty()) {
				slot.setStack(ItemStack.EMPTY);
			} else {
				slot.markDirty();
			}
		}

		return newStack;
	}

	private static class QuiverSlot extends Slot
	{
		public QuiverSlot(Inventory inventory, int index, int x, int y)
		{
			super(inventory, index, x, y);
		}

		public boolean canInsert(ItemStack stack)
		{
			return !stack.isEmpty() && (stack.getItem() == Items.ARROW || stack.getItem() == Items.TIPPED_ARROW);
		}
	}
}
