package party.lemons.gubbins.config;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import party.lemons.gubbins.util.serialization.GsonUtil;

import java.io.*;

public class GubbinsConfig
{
	public CaveBiomes CAVE_BIOMES = new CaveBiomes();
	public Adornments ADORNMENTS = new Adornments();
	public IceCaves ICE_CAVES = new IceCaves();
	public PotionFlask POTION_FLASK = new PotionFlask();
	public Ore ORE = new Ore();
	public Misc MISC = new Misc();


	public static class CaveBiomes
	{
		public boolean enabled = true;
		public CaveBiomeConfig SANDSTONE = new CaveBiomeConfig(10);
		public CaveBiomeConfig MOSSY = new CaveBiomeConfig(10);
		public CaveBiomeConfig GRASS = new CaveBiomeConfig(10);
		public CaveBiomeConfig SPIDER = new CaveBiomeConfig(10);
		public CaveBiomeConfig HARD_DIRT = new CaveBiomeConfig(10);
		public CaveBiomeConfig PRISMARINE = new CaveBiomeConfig(10);
		public CaveBiomeConfig MUSHROOM = new CaveBiomeConfig(3);
	}

	public static class Ore
	{
		public boolean enableGarnet = true;
		public boolean enableOnyx = true;
		public boolean enableAmethyst = true;
	}

	public static class IceCaves
	{
		public boolean enabled = true;
	}

	public static class Adornments
	{
		public boolean enabled = true;
	}

	public static class Misc
	{
		public boolean enableWaterBottleConcrete = true;
		public boolean enableCampfireDye = true;
		public boolean enableMansionInSnowTundra = true;
		public boolean enableSilkTouch2 = true;
		public boolean enableWarpedPodGen = true;
	}

	public static class PotionFlask
	{
		public int maxSize = 16;
	}

	public static GubbinsConfig load()
	{
		Gson gson = GsonUtil.makeStandardGson();
		JsonReader reader = null;
		try
		{
			reader = new JsonReader(new FileReader(FILE_PATH));
		}
		catch(FileNotFoundException e)
		{
			return new GubbinsConfig();
		}

		return gson.fromJson(reader, GubbinsConfig.class);
	}

	public static void write(GubbinsConfig config)
	{
		Gson gson = GsonUtil.makeStandardGson();
		try
		{
			Writer writer = new FileWriter(FILE_PATH);
			gson.toJson(config, writer);
			writer.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	public static final String FILE_PATH = "config/gubbins.json";
}
