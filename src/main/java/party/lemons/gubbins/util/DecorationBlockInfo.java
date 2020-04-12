package party.lemons.gubbins.util;

import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.WallBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import party.lemons.gubbins.Gubbins;
import party.lemons.gubbins.block.GubbinsStairBlock;
import party.lemons.gubbins.init.GubbinsItems;

import java.util.Map;

public class DecorationBlockInfo
{
	private Map<Type, Block> blocks = Maps.newHashMap();
	private final String name;
	private final Block.Settings settings;
	private final Block base;

	public DecorationBlockInfo(String name, Block baseBlock, Block.Settings settings)
	{
		this.name = name;
		this.settings = settings;
		this.base = baseBlock;
	}

	public DecorationBlockInfo slab()
	{
		set(Type.SLAB, new SlabBlock(settings));
		return this;
	}

	public DecorationBlockInfo stair()
	{
		set(Type.STAIR, new GubbinsStairBlock(base.getDefaultState(), settings));
		return this;
	}

	public DecorationBlockInfo wall()
	{
		set(Type.WALL, new WallBlock(settings));
		return this;
	}

	public DecorationBlockInfo all()
	{
		return slab().stair().wall();
	}

	private void set(Type type, Block block)
	{
		this.blocks.put(type, block);
	}

	public Block get(Type type)
	{
		return blocks.get(type);
	}

	public DecorationBlockInfo register()
	{
		for(Type key : blocks.keySet())
		{
			Block bl = Registry.register(Registry.BLOCK, key.make(name), blocks.get(key));
			Registry.register(Registry.ITEM, key.make(name), new BlockItem(bl, GubbinsItems.settings()));
		}

		return this;
	}

	enum Type
	{
		SLAB("slab"),
		STAIR("stairs"),
		WALL("wall");

		private String postfix;

		Type(String postfix)
		{
			this.postfix = postfix;
		}

		public Identifier make(String name)
		{
			return new Identifier(Gubbins.MODID, name + "_" + postfix);
		}
	}
}
