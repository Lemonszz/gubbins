package party.lemons.gubbins.block;

import party.lemons.gubbins.util.GubbinsUtil;

public class HardenedDirtBlock extends GubbinsBlock
{
	public HardenedDirtBlock(Settings settings)
	{
		super(settings);

		GubbinsUtil.addShovelableBlock(this);
	}
}
