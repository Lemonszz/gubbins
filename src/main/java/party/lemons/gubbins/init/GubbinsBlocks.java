package party.lemons.gubbins.init;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tools.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;
import party.lemons.gubbins.block.GubbinsBlock;
import party.lemons.gubbins.block.GubbinsOreBlock;
import party.lemons.gubbins.util.registry.AutoReg;

@AutoReg(type = Block.class, registry = "block")
public class GubbinsBlocks
{
	public static final Block AMETHYST_ORE = new GubbinsOreBlock(settings(Material.STONE, 1.5F).breakByTool(FabricToolTags.PICKAXES, 2).build());
	public static final Block GARNET_ORE = new GubbinsOreBlock(settings(Material.STONE, 1.5F).breakByTool(FabricToolTags.PICKAXES, 2).sounds(BlockSoundGroup.NETHER_ORE).build());
	public static final Block ONYX_ORE = new GubbinsOreBlock(settings(Material.STONE, 1.5F).breakByTool(FabricToolTags.PICKAXES, 2).build());
	public static final Block AMETHYST_BLOCK = new GubbinsBlock(settings(Material.METAL, 2.5F).build());
	public static final Block GARNET_BLOCK = new GubbinsBlock(settings(Material.METAL, 2.5F).build());
	public static final Block ONYX_BLOCk = new GubbinsBlock(settings(Material.METAL, 2.5F).build());

	public static FabricBlockSettings settings(Material material, float hardness)
	{
		return FabricBlockSettings.of(material).hardness(hardness).resistance(hardness);
	}
}
