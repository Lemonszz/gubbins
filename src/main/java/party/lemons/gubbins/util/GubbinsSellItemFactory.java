package party.lemons.gubbins.util;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOffers;

import java.util.Random;

public class GubbinsSellItemFactory implements TradeOffers.Factory
{
	private final ItemStack sell;
	private final int price;
	private final int count;
	private final int maxUses;
	private final int experience;
	private final float multiplier;

	public GubbinsSellItemFactory(Block block, int i, int j, int k, int l) {
		this(new ItemStack(block), i, j, k, l);
	}

	public GubbinsSellItemFactory(Item item, int i, int j, int k) {
		this((ItemStack)(new ItemStack(item)), i, j, 12, k);
	}

	public GubbinsSellItemFactory(Item item, int i, int j, int k, int l) {
		this(new ItemStack(item), i, j, k, l);
	}

	public GubbinsSellItemFactory(ItemStack itemStack, int i, int j, int k, int l) {
		this(itemStack, i, j, k, l, 0.05F);
	}

	public GubbinsSellItemFactory(ItemStack itemStack, int price, int count, int maxUses, int experience, float multiplier) {
		this.sell = itemStack;
		this.price = price;
		this.count = count;
		this.maxUses = maxUses;
		this.experience = experience;
		this.multiplier = multiplier;
	}

	public TradeOffer create(Entity entity, Random random) {
		return new TradeOffer(new ItemStack(Items.EMERALD, this.price), new ItemStack(this.sell.getItem(), this.count), this.maxUses, this.experience, this.multiplier);
	}
}
