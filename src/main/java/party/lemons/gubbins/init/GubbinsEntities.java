package party.lemons.gubbins.init;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import party.lemons.gubbins.entity.ChorusPearlEntity;
import party.lemons.gubbins.entity.NewBoatEntity;
import party.lemons.gubbins.entity.PrismarineArrowEntity;
import party.lemons.gubbins.entity.StickyItemFrameEntity;
import party.lemons.gubbins.util.registry.AutoReg;

@AutoReg(type = EntityType.class, registry = "entity_type")
public class GubbinsEntities
{
	public static final EntityType<NewBoatEntity> NEW_BOAT = FabricEntityTypeBuilder.create(SpawnGroup.MISC, (EntityType.EntityFactory<NewBoatEntity>)NewBoatEntity::new).trackable(128, 3).dimensions(EntityDimensions.fixed(1.375F, 0.5625F)).build();
	public static final EntityType<PrismarineArrowEntity> PRISMARINE_ARROW = FabricEntityTypeBuilder.create(SpawnGroup.MISC, (EntityType.EntityFactory<PrismarineArrowEntity>)PrismarineArrowEntity::new).trackable(128, 3, true).dimensions(EntityDimensions.fixed(0.5F, 0.5F)).build();
	public static final EntityType<StickyItemFrameEntity> STICKY_ITEM_FRAME = FabricEntityTypeBuilder.create(SpawnGroup.MISC, (EntityType.EntityFactory<StickyItemFrameEntity>)StickyItemFrameEntity::new).trackable(128, 3).dimensions(EntityDimensions.fixed(0.5F, 0.5F)).build();
	public static final EntityType<ChorusPearlEntity> CHORUS_PEARL = FabricEntityTypeBuilder.create(SpawnGroup.MISC, (EntityType.EntityFactory<ChorusPearlEntity>)ChorusPearlEntity::new).trackable(128, 10).dimensions(EntityDimensions.fixed(0.25F, 0.25F)).build();
//	public static final EntityType<CamelEntity> CAMEL = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, CamelEntity::new).trackable(128, 3).dimensions(EntityDimensions.fixed(0.9F, 1.87F)).build();

	public static void initSpawns()
	{
	/*	List<Biome> camelBiomes = BiomeUtil.gatherBiomeFromCategory(Biome.Category.DESERT, new ArrayList<>());
		camelBiomes.forEach(b->{
			b.getEntitySpawnList(SpawnGroup.CREATURE).add(new Biome.SpawnEntry(CAMEL, 1, 1, 3));
		});*/
	}
}
