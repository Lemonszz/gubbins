package party.lemons.gubbins.util.random;

import java.util.Random;

public class RandomUtil
{
	public static int getIntRange(int min, int max, Random random)
	{
		return random.nextInt(max - min) + min;
	}
}
