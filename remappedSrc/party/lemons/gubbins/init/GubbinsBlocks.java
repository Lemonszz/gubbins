package party.lemons.gubbins.init;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;
import party.lemons.gubbins.block.GubbinsBlock;
import party.lemons.gubbins.block.TranslucentBlock;
import party.lemons.gubbins.block.WarpedPodBlock;
import party.lemons.gubbins.util.DecorationBlockInfo;
import party.lemons.gubbins.util.registry.AutoReg;

@AutoReg(type = Block.class, registry = "block", priority = 98)
public class GubbinsBlocks
{
	public static final Block AMETHYST_ORE = new TranslucentBlock(settings(Material.STONE, 1.5F).breakByTool(FabricToolTags.PICKAXES, 2).build());
	public static final Block GARNET_ORE = new TranslucentBlock(settings(Material.STONE, 1.5F).breakByTool(FabricToolTags.PICKAXES, 2).sounds(BlockSoundGroup.NETHER_ORE).build());
	public static final Block ONYX_ORE = new TranslucentBlock(settings(Material.STONE, 1.5F).breakByTool(FabricToolTags.PICKAXES, 2).build());
	public static final Block DIAMOND_ICE_ORE = new TranslucentBlock(settings(Material.DENSE_ICE, 1.5F).breakByTool(FabricToolTags.PICKAXES, 3).build());
	public static final Block GOLD_ICE_ORE = new TranslucentBlock(settings(Material.DENSE_ICE, 1.5F).sounds(BlockSoundGroup.GLASS).breakByTool(FabricToolTags.PICKAXES, 2).build());
	public static final Block REDSTONE_ICE_ORE = new TranslucentBlock(settings(Material.DENSE_ICE, 1.5F).sounds(BlockSoundGroup.GLASS).breakByTool(FabricToolTags.PICKAXES, 2).build());
	public static final Block COAL_ICE_ORE = new TranslucentBlock(settings(Material.DENSE_ICE, 1.5F).sounds(BlockSoundGroup.GLASS).breakByTool(FabricToolTags.PICKAXES, 0).build());
	public static final Block IRON_ICE_ORE = new TranslucentBlock(settings(Material.DENSE_ICE, 1.5F).sounds(BlockSoundGroup.GLASS).breakByTool(FabricToolTags.PICKAXES, 1).build());

	public static final Block AMETHYST_BLOCK = new GubbinsBlock(settings(Material.METAL, 2.5F).build());
	public static final Block GARNET_BLOCK = new GubbinsBlock(settings(Material.METAL, 2.5F).build());
	public static final Block ONYX_BLOCk = new GubbinsBlock(settings(Material.METAL, 2.5F).build());

	//public static final Block ENDERPEARL_BLOCK = new GubbinsBlock(settings(Material.ORGANIC_PRODUCT, 2.5F).build());

	public static final Block SNOW_BRICKS = new GubbinsBlock(settings(Material.SNOW_BLOCK, 0.25F).breakByTool(FabricToolTags.SHOVELS, 0).sounds(BlockSoundGroup.SNOW).build());
	public static final Block MOSSY_STONE = new GubbinsBlock(settings(Material.STONE, 1.5F).build());
	public static final Block HARDENED_DIRT = new GubbinsBlock(settings(Material.STONE, 1.0F).sounds(BlockSoundGroup.GRAVEL).build());
	public static final Block HARDENED_DIRT_BRICKS = new GubbinsBlock(settings(Material.STONE, 1.5F).sounds(BlockSoundGroup.GRAVEL).build());
	public static final Block BASALT_BRICKS = new GubbinsBlock(settings(Material.STONE, 1.25F).sounds(BlockSoundGroup.BASALT).build());
	public static final Block POLISHED_BASALT_BRICKS = new GubbinsBlock(settings(Material.STONE, 1.25F).sounds(BlockSoundGroup.BASALT).build());
	public static final Block FLINT_BLOCK = new GubbinsBlock(settings(Material.STONE, 1.25F).sounds(BlockSoundGroup.BASALT).build());
	public static final Block FLINT_SHINGLES = new GubbinsBlock(settings(Material.STONE, 1.5F).sounds(BlockSoundGroup.BASALT).build());

	public static final Block WARPED_POD = new WarpedPodBlock(settings(Material.ORGANIC_PRODUCT, 0F).noCollision().ticksRandomly().sounds(BlockSoundGroup.NETHER_WART).nonOpaque().build());

	public static final DecorationBlockInfo SNOW_BRICK_DECORATION = new DecorationBlockInfo("snow_brick", SNOW_BRICKS, settings(Material.SNOW_BLOCK, 0.3F).build()).all();
	public static final DecorationBlockInfo MOSSY_STONE_DECORATION = new DecorationBlockInfo("mossy_stone", MOSSY_STONE, settings(Material.STONE, 1.5F).build()).all();
	public static final DecorationBlockInfo HARDENED_DIRT_DECORATION = new DecorationBlockInfo("hardened_dirt", HARDENED_DIRT, settings(Material.STONE, 1.0F).sounds(BlockSoundGroup.GRAVEL).build()).all();
	public static final DecorationBlockInfo HARDENED_DIRT_BRICK_DECORATION = new DecorationBlockInfo("hardened_dirt_brick", HARDENED_DIRT_BRICKS, settings(Material.STONE, 1.5F).sounds(BlockSoundGroup.GRAVEL).build()).all();
	public static final DecorationBlockInfo BASALT_BRICK_DECORATION = new DecorationBlockInfo("basalt_brick", BASALT_BRICKS, settings(Material.STONE, 1.25F).sounds(BlockSoundGroup.BASALT).build()).all();
	public static final DecorationBlockInfo POLISHED_BASALT_BRICK_DECORATION = new DecorationBlockInfo("polished_basalt_brick", POLISHED_BASALT_BRICKS, settings(Material.STONE, 1.25F).sounds(BlockSoundGroup.BASALT).build()).all();
	public static final DecorationBlockInfo FLINT_SHINGLES_DECORATION = new DecorationBlockInfo("flint_shingle", FLINT_SHINGLES, settings(Material.STONE, 1.25F).sounds(BlockSoundGroup.BASALT).build()).all();

	public static FabricBlockSettings settings(Material material, float hardness)
	{
		return FabricBlockSettings.of(material).hardness(hardness).resistance(hardness);
	}

	public static void initDecorations()
	{
		SNOW_BRICK_DECORATION.register();
		MOSSY_STONE_DECORATION.register();
		HARDENED_DIRT_DECORATION.register();
		HARDENED_DIRT_BRICK_DECORATION.register();
		BASALT_BRICK_DECORATION.register();
		POLISHED_BASALT_BRICK_DECORATION.register();
		FLINT_SHINGLES_DECORATION.register();
	}
}
