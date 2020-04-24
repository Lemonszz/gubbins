package party.lemons.gubbins.init;

import net.fabricmc.fabric.api.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityCategory;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import party.lemons.gubbins.entity.CamelEntity;
import party.lemons.gubbins.entity.NewBoatEntity;
import party.lemons.gubbins.entity.PrismarineArrowEntity;
import party.lemons.gubbins.entity.StickyItemFrameEntity;
import party.lemons.gubbins.util.registry.AutoReg;

@AutoReg(type = EntityType.class, registry = "entity_type")
public class GubbinsEntities
{
	public static final EntityType<NewBoatEntity> NEW_BOAT = FabricEntityTypeBuilder.create(EntityCategory.MISC, (EntityType.EntityFactory<NewBoatEntity>)NewBoatEntity::new).trackable(128, 3).size(EntityDimensions.fixed(1.375F, 0.5625F)).build();
	public static final EntityType<PrismarineArrowEntity> PRISMARINE_ARROW = FabricEntityTypeBuilder.create(EntityCategory.MISC, (EntityType.EntityFactory<PrismarineArrowEntity>)PrismarineArrowEntity::new).trackable(128, 3, true).size(EntityDimensions.fixed(0.5F, 0.5F)).build();
	public static final EntityType<StickyItemFrameEntity> STICKY_ITEM_FRAME = FabricEntityTypeBuilder.create(EntityCategory.MISC, (EntityType.EntityFactory<StickyItemFrameEntity>)StickyItemFrameEntity::new).trackable(128, 3).size(EntityDimensions.fixed(0.5F, 0.5F)).build();
	public static final EntityType<CamelEntity> CAMEL = FabricEntityTypeBuilder.create(EntityCategory.CREATURE, CamelEntity::new).trackable(128, 3).size(EntityDimensions.fixed(0.9F, 1.87F)).build();
}
